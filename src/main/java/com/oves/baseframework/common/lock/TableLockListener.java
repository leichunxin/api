package com.oves.baseframework.common.lock;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oves.baseframework.common.zk.CuratorClient;
import com.oves.baseframework.util.IpUtil;

/**
 * Created by darbean on 1/27/16.
 */
public class TableLockListener implements PathChildrenCacheListener {

    private static Logger                      logger      = LoggerFactory
        .getLogger(TableLockListener.class);

    private CuratorClient                      curatorClient;
    // 本表锁的服务注册节点，实际产生竞争的服务器父节点
    private String                             serverPath;
    private String                             ip;
    // 锁注册节点
    private String                             locksPath   = "locks";

    // 子节点事件监听
    private PathChildrenCache                  childrenCache;
    // 分配到的锁<offset, lock>
    private Map<Integer, DistributedTableLock> locks;
    // 防止锁重分配时读到脏数据
    private ReentrantLock                      lock;

    private String                             tableName;
    private int                                offsetBegin = 0, offsetEnd = 0;
    private int                                tableCount;

    public TableLockListener(CuratorClient curatorClient, String serversPath, String tableName,
                             int tableCount) throws Exception {
        this.curatorClient = curatorClient;
        this.serverPath = serversPath + "/" + tableName;
        this.tableName = tableName;
        this.tableCount = tableCount;
        this.ip = IpUtil.getRealIp();
        this.lock = new ReentrantLock();
        this.locks = new ConcurrentHashMap<Integer, DistributedTableLock>();

        // 锁父节点初始化
        try {
            Stat locksStat = curatorClient.getStat(locksPath);
            if (locksStat == null) {
                curatorClient.createPath(locksPath, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            // 忽略
        }

        // 服务器注册父节点初始化
        try {
            Stat serverStat = curatorClient.getStat(serverPath);
            if (serverStat == null) {
                curatorClient.createPath(serverPath, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            // 忽略
        }
    }

    public boolean init() {
        try {
            childrenCache = new PathChildrenCache(curatorClient.getCurator(), serverPath, true);
            childrenCache.getListenable().addListener(this);
            childrenCache.start();
            // 在zk注册自己的IP
            Stat ipStat = curatorClient.getStat(serverPath + "/" + this.ip);
            if (ipStat != null) {
                // 节点存在, 是由于临时节点延时, 先删除旧节点
                curatorClient.deletePath(serverPath + "/" + this.ip);
            }
            curatorClient.createPath(serverPath + "/" + this.ip, CreateMode.EPHEMERAL, "available");
            return true;
        } catch (Exception e) {
            logger.error(
                "init tableListener failed, ip:" + ip + ", tableName: " + tableName + " Exception",
                e);
            return false;
        }
    }

    public void reset() {
        childrenCache.getListenable().removeListener(this);
    }

    public int getOffsetBegin() {
        lock.lock();
        try {
            return offsetBegin;
        } finally {
            lock.unlock();
        }
    }

    public int getOffsetEnd() {
        lock.lock();
        try {
            return offsetEnd;
        } finally {
            lock.unlock();
        }
    }

    public DistributedTableLock getLock(int offset) {
        return locks.get(offset);
    }

    @Override
    public void childEvent(CuratorFramework curatorFramework,
                           PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
        switch (pathChildrenCacheEvent.getType()) {
            case CHILD_ADDED:// 子节点新增
            case CHILD_REMOVED:// 子节点移除
                String path = pathChildrenCacheEvent.getData().getPath();
                logger
                    .info("childEvent:" + pathChildrenCacheEvent.getType() + ", childPath:" + path);
                // XXX 重新分配锁区间，加锁防止出现前一个事件没完成直接清理，会导致持有的锁区间重叠
                lock.lock();
                try {
                    // 不需要重新分配，先将获得的锁释放
                    //                    for (DistributedTableLock tLock : locks.values()) {
                    //                        tLock.unLock();
                    //                    }
                    locks.clear();
                    List<String> children = curatorClient.getChildren(serverPath);
                    Collections.sort(children);
                    int size = children.size();
                    if (size == 0) {
                        logger.warn("own ip: " + ip
                                    + " not in the servers list, need to re-register child event");
                        reset();
                        init();
                        return;
                    }
                    // 计算本机在服务器列表中处于第几位
                    int ipIndex = children.indexOf(ip);
                    if (ipIndex == -1) {
                        logger.warn("own ip: " + ip
                                    + " not in the servers list, need to re-register child event");
                        reset();
                        init();
                        return;
                    }
                    // 计算每台机器平均分配的表
                    int pageSize = tableCount / size;
                    // 最后没分完的表
                    int last = tableCount % size;
                    // 分配到的区间起始值
                    offsetBegin = pageSize * ipIndex;
                    // 分配到的区间结尾值
                    offsetEnd = offsetBegin + pageSize - 1;
                    // 将没分完的表分给最后一个机器
                    if (ipIndex == size - 1) {
                        offsetEnd += last;
                    }
                    // 防止越界
                    offsetEnd = offsetEnd > (tableCount - 1) ? (tableCount - 1) : offsetEnd;
                    // 持有获得的锁对象，加锁操作放到真正需要锁的时候
                    for (int i = offsetBegin; i <= offsetEnd; i++) {
                        String lockPath = "/" + locksPath + "/" + tableName
                                          + new DecimalFormat("_000").format(i);
                        DistributedTableLock lock = new DistributedTableLock(curatorClient,
                            lockPath, ip);
                        locks.put(i, lock);
                    }
                    logger.info("adjust lock range, offsetBegin:" + offsetBegin + ", offsetEnd:"
                                + offsetEnd + ", locksize:" + locks.size());
                } finally {
                    lock.unlock();
                }
                break;
            // 失去连接
            case CONNECTION_LOST:
                // 失去连接，交给状态监听器处理
                logger.info("childEvent:CONNECTION_LOST");
                break;
            default:
                break;
        }
    }

    public String getIp() {
        return ip;
    }

    public String getTableName() {
        return tableName;
    }

    public int getTableCount() {
        return tableCount;
    }
}
