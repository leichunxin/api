package com.oves.baseframework.common.drm;

import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oves.baseframework.common.util.ReflectionUtils;
import com.oves.baseframework.util.IpUtil;

/**
 * drm 值监控事件处理机制
 *
 * @author jin.qian
 * @version $Id: DrmIPWatcher.java, v 0.1 2015年12月28日 下午9:36:54 jin.qian Exp $
 */
public class DrmIPWatcher implements CuratorWatcher {

    private static Logger     logger = LoggerFactory.getLogger("zookeeper-appender");
    private DrmZookeeprClient client;
    private Object            drmobj;
    private String            param;
    private boolean           isroot;

    public DrmIPWatcher(DrmZookeeprClient client, Object drmobj, String param, boolean isroot) {
        this.client = client;
        this.drmobj = drmobj;
        this.param = param;
        this.isroot = isroot;
    }

    @Override
    public void process(WatchedEvent event) throws Exception {
        logger.info(event.toString());
        // session过期或失去连接不继续watch
        if (event.getState() == Watcher.Event.KeeperState.Disconnected
            || event.getState() == Watcher.Event.KeeperState.Expired) {
            return;
        }
        if (client == null) {
            return;
        }

        if (event.getType() == Watcher.Event.EventType.NodeDataChanged) {
            try {
                String path = event.getPath();
                String value = client.getCuratorClient().readPath(path);
                if (isroot) {
                    String ip = IpUtil.getRealIp();
                    String ippath = path + "/" + ip;
                    client.getCuratorClient().writePath(ippath, value);
                } else {
                    ReflectionUtils.writeField(param, drmobj, value);
                }
                logger.info(client.getCuratorClient().readPath(path));
            } finally {
                client.getCuratorClient().watcherPath(event.getPath(), this);
            }
        }
    }
}
