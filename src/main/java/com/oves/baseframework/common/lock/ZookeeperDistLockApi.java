package com.oves.baseframework.common.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oves.baseframework.common.zk.CuratorClient;

public class ZookeeperDistLockApi {

    private static Logger                  logger    = LoggerFactory
        .getLogger(ZookeeperDistLockApi.class);

    // zk地址:端口
    private String                         zkAddress = "100.126.8.41:2181";

    // 命名空间
    private String                         namespace = "server";
    // 服务器注册父节点
    private String                         servers   = "servers";

    // zk客户端
    private CuratorClient                  curatorClient;

    // 步骤控制，客户端连接建立后才能进行下一步
    private CountDownLatch                 latch;

    private Map<String, TableLockListener> tableMap;

    public ZookeeperDistLockApi() {
        tableMap = new ConcurrentHashMap<>();
    }

    public void init() throws Exception {
        latch = new CountDownLatch(1);

        try {
            // client 初始化
            curatorClient = new CuratorClient();
            curatorClient.init(namespace, zkAddress, 10000, new StateEventListener());
            // 等待连接建立
            latch.await();

            // 服务器注册父节点初始化
            try {
                Stat serverStat = curatorClient.getStat(servers);
                if (serverStat == null) {
                    curatorClient.createPath(servers, CreateMode.PERSISTENT);
                }
            } catch (Exception e) {
                // 忽略
            }
        } catch (Exception e) {
            logger.error("ZookeeperDistLockApi init Exception", e);
            throw e;
        }
    }

    public TableLockListener register(String tableName, int tableCount) throws Exception {
        if (tableMap.containsKey(tableName)) {
            logger.warn("table " + tableName + "duplicate register!");
            return null;
        }

        try {
            // server父节点监听，监听对应tableName的父节点
            TableLockListener tableLockListener = new TableLockListener(curatorClient, servers,
                tableName, tableCount);
            boolean init = tableLockListener.init();
            if (!init) {
                logger.warn("ZookeeperDistLockApi register init TableLockListener failed");
            }
            tableMap.put(tableName, tableLockListener);
            return tableLockListener;
        } catch (Exception e) {
            logger.error("ZookeeperDistLockApi register table Exception", e);
            return null;
        }
    }

    private void reinit() {
        try {
            unregister();
            init();
            for (TableLockListener lockListener : tableMap.values()) {
                lockListener.init();
            }
        } catch (Exception e) {
            logger.error("重新初始化异常！" + e);
        }
    }

    private void unregister() throws Exception {
        try {
            for (TableLockListener lockListener : tableMap.values()) {
                lockListener.reset();
            }
            latch = null;
        } catch (Exception e) {
            logger.warn("unregister failed");
            throw e;
        }
    }

    public String getServersPath() throws Exception {
        return namespace + "/" + servers;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    // 服务器注册  状态监听
    final class StateEventListener implements ConnectionStateListener {

        @Override
        public void stateChanged(CuratorFramework curator, ConnectionState state) {
            if (state == ConnectionState.CONNECTED) {
                logger.info("connection established");
                latch.countDown();
            } else if (state == ConnectionState.LOST) {
                logger.info("connection lost, waiting for reconnect");
                // 失去连接后，重新初始化
                logger.info("re-initing");
                try {
                    reinit();
                    logger.info("re-inited");
                } catch (Exception e) {
                }
            }
        }
    }
}
