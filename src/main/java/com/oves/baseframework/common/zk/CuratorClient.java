package com.oves.baseframework.common.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;
import java.util.List;

/**
 * zookeeper curator客户端封装, 封装了常用path操作方法
 */
public class CuratorClient {

    /**
     * zookeeper连接地址, 由调用方决定
     */
    private String zkAddress;
    /**
     * 命名空间, 由调用方决定
     **/
    private String namespace;
    /**
     * zookeeper客户端的curator实现
     **/
    private CuratorFramework curator;

    /**
     * 初始化客户端
     *
     * @param namespace     命名空间
     * @param address       链接地址+端口列表(ip:port,ip:port)
     * @param timeout       超时时间
     * @param stateListener 状态监听器
     * @throws Exception
     */
    public void init(String namespace, String address, int timeout, ConnectionStateListener stateListener) throws Exception {
        this.namespace = namespace;
        this.zkAddress = address;
        curator = CuratorFrameworkFactory
                .builder()
                .connectString(this.zkAddress)
                .namespace(this.namespace)
                .retryPolicy(new RetryNTimes(5, 1000))
                .connectionTimeoutMs(timeout)
                .build();
        curator.getConnectionStateListenable().addListener(stateListener);
        curator.start();
    }

    /**
     * 移除状态监听器
     *
     * @param stateListener
     */
    public void removeListener(ConnectionStateListener stateListener) {
        curator.getConnectionStateListenable().removeListener(stateListener);
    }

    /**
     * 关闭当前连接
     *
     * @throws Exception
     */
    public void close() throws Exception {
        try {
            curator.close();
            curator = null;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 获取节点状态
     *
     * @param path
     * @return
     * @throws Exception
     */
    public Stat getStat(String path) throws Exception {
        return curator.checkExists().forPath(path);
    }

    /**
     * 判断节点是否存在
     *
     * @param path
     * @return
     * @throws Exception
     */
    public boolean isPathExist(String path) throws Exception {
        Stat serverStat = getStat(path);
        if (serverStat == null) {
            return false;
        }
        return true;
    }

    /**
     * 创建节点
     *
     * @param path
     * @param mode
     * @throws Exception
     */
    public void createPath(String path, CreateMode mode) throws Exception {
        createPath(path, mode, "");
    }

    /**
     * 创建节点, 同时写入数据
     *
     * @param path
     * @param mode
     * @param value
     * @throws Exception
     */
    public void createPath(String path, CreateMode mode, String value) throws Exception {
        curator.create()// 创建一个路径
                .creatingParentsIfNeeded()// 如果指定的节点的父节点不存在，递归创建父节点
                .withMode(mode)// 存储类型（临时的还是持久的）
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)// 不设置访问权限
                .forPath(path, value.getBytes(Charset.forName("utf-8")));// 创建的路径, 默认空值
    }

    /**
     * 删除节点
     *
     * @param path
     * @throws Exception
     */
    public void deletePath(String path) throws Exception {
        curator.delete().forPath(path);
    }

    /**
     * 往节点中写入数据
     *
     * @param path
     * @param value
     * @throws Exception
     */
    public void writePath(String path, String value) throws Exception {
        curator.setData().forPath(path, value.getBytes(Charset.forName("utf-8")));
    }

    /**
     * 读取节点数据
     *
     * @param path
     * @return
     * @throws Exception
     */
    public String readPath(String path) throws Exception {
        byte[] buffer = curator.getData().forPath(path);
        return new String(buffer);
    }

    /**
     * 监控节点
     *
     * @param path
     * @param watcher
     * @return
     * @throws Exception
     */
    public String watcherPath(String path, CuratorWatcher watcher) throws Exception {
        byte[] buffer = curator.getData().usingWatcher(watcher).forPath(path);
        return new String(buffer);
    }

    /**
     * 获取子节点
     *
     * @param path
     * @return
     * @throws Exception
     */
    public List<String> getChildren(String path) throws Exception {
        return curator.getChildren().forPath(path);
    }

    /**
     * 获取原生zookeeper的curator客户端实现
     *
     * @return
     */
    public CuratorFramework getCurator() {
        return curator;
    }
}