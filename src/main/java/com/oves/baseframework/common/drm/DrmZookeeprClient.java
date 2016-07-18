package com.oves.baseframework.common.drm;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oves.baseframework.common.util.ReflectionUtils;
import com.oves.baseframework.common.zk.CuratorClient;
import com.oves.baseframework.util.IpUtil;

/**
 * drm配置主逻辑, 在需要使用drm的项目中配置该类的bean, 调用confRegist方法将配置项注册到zookeeper即可
 */
public class DrmZookeeprClient {

    private static Logger           logger    = LoggerFactory.getLogger("zookeeper-appender");

    /** zookeeper客户端, curator实现的进一步封装 */
    private CuratorClient           curatorClient;

    /** 命名空间, 固定 **/
    private static final String     namespace = "DRM";

    private String                  ip;

    /** zookeeper连接地址 */
    private String                  zkAddress;

    /** zookeeper客户端连接超时时间 */
    private int                     timeout   = 10000;

    /** 应用名 **/
    private String                  appName;

    /** zookeeper客户端状态监听器 */
    private ConnectionStateListener listener;

    /** 注册的配置项集合 */
    private Set<AppDrmNode>         confSet   = new CopyOnWriteArraySet<AppDrmNode>();

    // 步骤控制，客户端连接建立后才能进行下一步
    private CountDownLatch          latch     = null;

    public void init() throws Exception {
        ip = IpUtil.getRealIp();// 获取本应用所在机器的外网IP, 只需要获取一次
        latch = new CountDownLatch(1);
        this.listener = new StateEventListener();
        curatorClient = new CuratorClient();
        curatorClient.init(namespace, zkAddress, timeout, listener);
        // 等待连接建立
        latch.await();
        // app配置父节点初始化
        try {
            if (!curatorClient.isPathExist(appName)) {
                curatorClient.createPath(appName, CreateMode.PERSISTENT);
                curatorClient.writePath(appName, "");
            }
        } catch (Exception e) {
            // 忽略
        }
    }

    public boolean confRegist(AppDrmNode drmNode) {
        return confRegist(drmNode, true);
    }

    /**
     * 注册配置节点
     *
     * @param drmNode
     * @return
     */
    public boolean confRegist(AppDrmNode drmNode, boolean addset) {
        String path = appName + "/" + drmNode.getClassname() + "." + drmNode.getParmname();
        String ippath = path + "/" + ip;
        if (addset && confSet.contains(drmNode)) {
            logger.error("重复注册节点{}", drmNode);
            return false;
        }
        //初始化父节点
        try {
            if (!curatorClient.isPathExist(path)) {
                curatorClient.createPath(path, CreateMode.PERSISTENT);
                curatorClient.writePath(path, drmNode.getValue());
            } else {
                String value = curatorClient.readPath(path);
                //处理值
                if (!value.equals(drmNode.getValue())) {
                    ReflectionUtils.writeField(drmNode.getParmname(), drmNode.getObj(), value);
                    drmNode.setValue(value);
                }
            }
            //监控根目录
            curatorClient.watcherPath(path,
                new DrmIPWatcher(this, drmNode.getObj(), drmNode.getParmname(), true));
            //ippath
            if (curatorClient.isPathExist(ippath)) {
                curatorClient.deletePath(ippath);
            }
            //初始化临时节点 以及当前值
            curatorClient.createPath(ippath, CreateMode.EPHEMERAL);
            curatorClient.writePath(ippath, drmNode.getValue());
            //挂载临时节点监听
            curatorClient.watcherPath(ippath,
                new DrmIPWatcher(this, drmNode.getObj(), drmNode.getParmname(), false));
            if (addset) {
                confSet.add(drmNode);
            }
        } catch (Exception ex) {
            logger.error("注册drm异常", ex);
            return false;
        }
        return true;
    }

    // 服务器注册  状态监听
    final class StateEventListener implements ConnectionStateListener {

        @Override
        public void stateChanged(CuratorFramework curator, ConnectionState state) {
            if (state == ConnectionState.CONNECTED) {
                logger.info(" connection established");
                latch.countDown();
            } else if (state == ConnectionState.LOST) {
                logger.info(" connection lost, waiting for reconnect");
                try {
                    // 失去连接后，重新初始化
                    logger.info(" re-initing");
                    reinit();
                    logger.info(" re-inited");
                } catch (Exception e) {
                    logger.error("re-inited%s", e);
                }
            }
        }
    }

    /**
     * 重新初始化
     */
    private void reinit() {
        try {
            unregister();
            init();
            for (AppDrmNode conf : confSet) {
                confRegist(conf, false);
            }
        } catch (Exception e) {
            logger.error("重新初始化异常！", e);
        }
    }

    /**
     * 关闭原有连接
     *
     * @throws Exception
     */
    private void unregister() throws Exception {
        try {
            curatorClient.removeListener(listener);
            listener = null;
            curatorClient.close();
            curatorClient = null;
        } catch (Exception e) {
            logger.warn("unregister failed");
            throw e;
        }
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public CuratorClient getCuratorClient() {
        return curatorClient;
    }
}
