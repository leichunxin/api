package com.oves.baseframework.common.server.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oves.baseframework.common.server.netty.NettyRemotingServer;
import com.oves.baseframework.common.server.netty.NettyRequestProcessor;

import java.util.concurrent.*;

/**
 * Created by darbean on 3/2/16.
 */
public class DefaultNettyServer {

    private Logger logger = LoggerFactory.getLogger(DefaultNettyServer.class);

    private int accepts = 3000;
    private int corePoolSize = 1;
    private int maximumPoolSize = 5;
    private long keepAliveTime = 1000;

    private String port;

    private NettyRequestProcessor processor;

    public void init() {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(accepts);
        ExecutorService executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, workQueue);
        NettyRemotingServer nserver = new NettyRemotingServer(port, processor, executor);
        nserver.start();
    }

    public void setAccepts(int accepts) {
        this.accepts = accepts;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setProcessor(NettyRequestProcessor processor) {
        this.processor = processor;
    }
}
