package com.oves.baseframework.common.lock;

import java.io.Serializable;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oves.baseframework.common.zk.CuratorClient;

/**
 * @author darbean
 * @ClassName: DistributedTableLock
 * @Description: 使用{@code zookeeper}实现分布式锁服务。
 * @date Nov 10, 2015 9:59:46 PM
 */
public class DistributedTableLock implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(DistributedTableLock.class);

    /**
     * 封装了各种操作的客户端
     */
    private CuratorClient curatorClient;
    /**
     * 锁完整路径
     */
    private String        path;
    /**
     * 节点值
     */
    private String        value;

    public DistributedTableLock(CuratorClient curatorClient, String path, String value) {
        this.curatorClient = curatorClient;
        this.path = path;
        this.value = value;
    }

    public boolean lock() {
        try {
            curatorClient.createPath(path, CreateMode.EPHEMERAL, value);
            return true;
        } catch (Exception e) {
            logger.error("lock Exception", e);
            return false;
        }
    }

    public void unLock() {
        try {
            Stat stat = curatorClient.getStat(path);
            // 节点不存在，跳出
            if (stat == null) {
                return;
            }
            // 节点存在，读取内容
            String value = curatorClient.readPath(path);
            if (value != null) {
                // 如果不是本机加的锁，跳出
                if (!value.equals(this.value)) {
                    return;
                }
            }
            // 内容为空，或为本机加的锁，则释放锁
            while (true) {
                try {
                    stat = curatorClient.getStat(path);
                    // 节点不存在，跳出
                    if (stat == null) {
                        return;
                    }
                    // 释放锁
                    curatorClient.deletePath(path);
                } catch (Exception e) {
                    logger.error("unlock delete Exception, retry...", e);
                    continue;
                }
                break;
            }
        } catch (Exception e) {
            logger.error("unLock Exception", e);
        }
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }

    public boolean isAvailable() {
        boolean available = false;
        try {
            Stat stat = curatorClient.getStat(path);
            // 节点不存在，不可用
            if (stat == null) {
                return available;
            }
            String value = curatorClient.readPath(path);
            if (value != null && value.equals(this.value)) {
                available = true;
            }
        } catch (Exception e) {
            available = false;
        }
        return available;
    }
}
