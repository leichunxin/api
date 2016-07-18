package com.oves.baseframework.common.server.netty;

import com.oves.baseframework.common.server.protocol.RemotingCommand;

import io.netty.channel.ChannelHandlerContext;


/**
 * Common remoting command processor
 * @author jin.qian
 * @since 2013-7-13
 */
public interface NettyRequestProcessor {
    RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request)
            throws Exception;
}
