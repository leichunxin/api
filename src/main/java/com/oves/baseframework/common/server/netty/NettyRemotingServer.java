package com.oves.baseframework.common.server.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.oves.baseframework.common.server.common.Pair;
import com.oves.baseframework.common.server.common.RemotingUtil;
import com.oves.baseframework.common.server.protocol.RemotingCommand;
import com.oves.baseframework.common.server.protocol.RemotingSysResponseCode;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

/**
 * Remoting服务端实现
 *
 * @author jin.qian
 * @version $Id: NettyRemotingServer.java, v 0.1 2016年1月14日 下午4:38:32 jin.qian Exp $
 */
public class NettyRemotingServer implements RemotingServer {
    private static final Logger log = LoggerFactory.getLogger(RemotingUtil.RemotingLogName);
    private ServerBootstrap serverBootstrap;
    private EventLoopGroup eventLoopGroupWorker;
    private EventLoopGroup eventLoopGroupBoss;
    private NettyServerConfig nettyServerConfig;

    private DefaultEventExecutorGroup defaultEventExecutorGroup;

    // 本地server绑定的端口
    private int port = 0;
    // 默认请求代码处理器
    protected Pair<NettyRequestProcessor, ExecutorService> requestProcessor;


    public NettyRemotingServer(final NettyServerConfig nettyServerConfig) {
        init(nettyServerConfig);
    }

    public NettyRemotingServer(String listenPort, NettyRequestProcessor processor, ExecutorService executor) {
        NettyServerConfig nsConfig = new NettyServerConfig();
        if (StringUtils.isNumeric(listenPort)) {
            nsConfig.setListenPort(Integer.parseInt(listenPort));
        }
        init(nsConfig);
        this.registerProcessor(processor, executor);
    }

    private void init(final NettyServerConfig nettyServerConfig) {
        this.serverBootstrap = new ServerBootstrap();
        this.nettyServerConfig = nettyServerConfig;

        this.eventLoopGroupBoss = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,
                        String.format("NettyBossSelector_%d", this.threadIndex.incrementAndGet()));
            }
        });

        this.eventLoopGroupWorker = new NioEventLoopGroup(
                nettyServerConfig.getServerSelectorThreads(), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);
            private int threadTotal = nettyServerConfig.getServerSelectorThreads();

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyServerSelector_%d_%d", threadTotal,
                        this.threadIndex.incrementAndGet()));
            }
        });
    }

    @Override
    public void start() {
        this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(//
                nettyServerConfig.getServerWorkerThreads(), //
                new ThreadFactory() {

                    private AtomicInteger threadIndex = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r,
                                "NettyServerWorkerThread_" + this.threadIndex.incrementAndGet());
                    }
                });

        ServerBootstrap childHandler = //
                this.serverBootstrap.group(this.eventLoopGroupBoss, this.eventLoopGroupWorker)
                        .channel(NioServerSocketChannel.class)
                        //
                        .option(ChannelOption.SO_BACKLOG, 1024)
                        //
                        .option(ChannelOption.SO_REUSEADDR, true)
                        //
                        .option(ChannelOption.SO_KEEPALIVE, false)
                        //
                        .childOption(ChannelOption.TCP_NODELAY, true)
                        //
                        .option(ChannelOption.SO_SNDBUF, nettyServerConfig.getServerSocketSndBufSize())
                        //
                        .option(ChannelOption.SO_RCVBUF, nettyServerConfig.getServerSocketRcvBufSize())
                        //
                        .localAddress(new InetSocketAddress(this.nettyServerConfig.getListenPort()))
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(
                                        //
                                        defaultEventExecutorGroup, //
                                        new NettyEncoder(), //
                                        new NettyDecoder(), //
                                        new IdleStateHandler(0, 0,
                                                nettyServerConfig.getServerChannelMaxIdleTimeSeconds()), //
                                        new NettyConnetManageHandler(), //
                                        new NettyServerHandler());
                            }
                        });

        if (nettyServerConfig.isServerPooledByteBufAllocatorEnable()) {
            // 这个选项有可能会占用大量堆外内存，暂时不使用。
            childHandler.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        }

        try {
            ChannelFuture sync = this.serverBootstrap.bind().sync();
            InetSocketAddress addr = (InetSocketAddress) sync.channel().localAddress();
            this.port = addr.getPort();
        } catch (InterruptedException e1) {
            throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException",
                    e1);
        }
    }

    @Override
    public void shutdown() {
        try {
            this.eventLoopGroupBoss.shutdownGracefully();

            this.eventLoopGroupWorker.shutdownGracefully();

            if (this.defaultEventExecutorGroup != null) {
                this.defaultEventExecutorGroup.shutdownGracefully();
            }
        } catch (Exception e) {
            log.error("NettyRemotingServer shutdown exception, ", e);
        }
    }

    @Override
    public void registerProcessor(NettyRequestProcessor processor, ExecutorService executor) {
        this.requestProcessor = new Pair<NettyRequestProcessor, ExecutorService>(processor,
                executor);
    }

    private void processRequestCommand(final ChannelHandlerContext ctx, final RemotingCommand cmd) {
        final Pair<NettyRequestProcessor, ExecutorService> pair = this.requestProcessor;

        if (pair != null) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {

                        final RemotingCommand response = pair.getObject1().processRequest(ctx, cmd);

                        if (response != null) {
                            response.setOpaque(cmd.getOpaque());
                            response.setType(RemotingCommand.RemotingCommandType.RESPONSE_COMMAND);
                            try {
                                ctx.writeAndFlush(response);
                            } catch (Throwable e) {
                                log.error("process request over, but response failed", e);
                                log.error(cmd.toString());
                                log.error(response.toString());
                            }
                        } else {
                            // 收到请求，但是没有返回应答，可能是processRequest中进行了应答，忽略这种情况
                        }
                    } catch (Throwable e) {
                        log.error("process request exception", e);
                        log.error(cmd.toString());

                        final RemotingCommand response = RemotingCommand.createResponseCommand(
                                RemotingSysResponseCode.SYSTEM_ERROR, //
                                RemotingUtil.exceptionSimpleDesc(e));
                        response.setOpaque(cmd.getOpaque());
                        ctx.writeAndFlush(response);
                    }
                }
            };

            try {
                // 这里需要做流控，要求线程池对应的队列必须是有大小限制的
                pair.getObject2().submit(run);
            } catch (RejectedExecutionException e) {
                // 每个线程10s打印一次
                if ((System.currentTimeMillis() % 10000) == 0) {
                    log.warn(RemotingUtil.parseChannelRemoteAddr(ctx.channel()) //
                            + ", too many requests and system thread pool busy, RejectedExecutionException " //
                            + pair.getObject2().toString());
                }

                final RemotingCommand response = RemotingCommand.createResponseCommand(
                        RemotingSysResponseCode.SYSTEM_BUSY,
                        "too many requests and system thread pool busy, please try another server");
                response.setOpaque(cmd.getOpaque());
                ctx.writeAndFlush(response);
            }
        } else {
            String error = " request type not supported";
            final RemotingCommand response = RemotingCommand
                    .createResponseCommand(RemotingSysResponseCode.REQUEST_CODE_NOT_SUPPORTED, error);
            response.setOpaque(cmd.getOpaque());
            ctx.writeAndFlush(response);
            log.error(RemotingUtil.parseChannelRemoteAddr(ctx.channel()) + error);
        }
    }

    class NettyServerHandler extends SimpleChannelInboundHandler<RemotingCommand> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx,
                                    RemotingCommand msg) throws Exception {
            if (msg != null) {
                processRequestCommand(ctx, msg);
            }
        }
    }

    class NettyConnetManageHandler extends ChannelDuplexHandler {
        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY SERVER PIPELINE: channelRegistered {}", remoteAddress);
            super.channelRegistered(ctx);
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY SERVER PIPELINE: channelUnregistered, the channel[{}]", remoteAddress);
            super.channelUnregistered(ctx);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY SERVER PIPELINE: channelActive, the channel[{}]", remoteAddress);
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.info("NETTY SERVER PIPELINE: channelInactive, the channel[{}]", remoteAddress);
            super.channelInactive(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent evnet = (IdleStateEvent) evt;
                if (evnet.state().equals(IdleState.ALL_IDLE)) {
                    final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
                    log.warn("NETTY SERVER PIPELINE: IDLE exception [{}]", remoteAddress);
                    RemotingUtil.closeChannel(ctx.channel());
                }
            }

            ctx.fireUserEventTriggered(evt);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            final String remoteAddress = RemotingUtil.parseChannelRemoteAddr(ctx.channel());
            log.warn("NETTY SERVER PIPELINE: exceptionCaught {}", remoteAddress);
            log.warn("NETTY SERVER PIPELINE: exceptionCaught exception.", cause);
            RemotingUtil.closeChannel(ctx.channel());
        }
    }

    @Override
    public int localListenPort() {
        return this.port;
    }
}
