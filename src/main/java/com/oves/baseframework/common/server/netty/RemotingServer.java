package com.oves.baseframework.common.server.netty;

import java.util.concurrent.ExecutorService;


/**
 * 远程通信，Server接口
 *
 * @author jin.qian
 * @version $Id: RemotingServer.java, v 0.1 2016年1月14日 下午8:49:37 jin.qian Exp $
 */
public interface RemotingServer {


    /**
     * 注册处理器
     *
     * @param processor
     * @param executor
     */
    public void registerProcessor(final NettyRequestProcessor processor, final ExecutorService executor);

    /**
     * 启动服务
     */
    public void start();

    /**
     * 停止服务
     */
    public void shutdown();

    /**
     * 服务器绑定的本地端口
     *
     * @return PORT
     */
    public int localListenPort();
}
