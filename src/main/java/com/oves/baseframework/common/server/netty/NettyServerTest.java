package com.oves.baseframework.common.server.netty;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.BasicConfigurator;
import org.xml.sax.SAXException;

import com.oves.baseframework.common.server.protocol.RemotingCommand;

import io.netty.channel.ChannelHandlerContext;

public class NettyServerTest {

    public static void main(String[] args) throws ParserConfigurationException, SAXException,
                                           IOException {
        BasicConfigurator.configure();
        NettyRequestProcessor processor = new NettyRequestProcessor() {

            @Override
            public RemotingCommand processRequest(ChannelHandlerContext ctx,
                                                  RemotingCommand request) throws Exception {
                System.out.println("receive netty RemotingCommand " + request.toString()
                                   + request.getBody().length);
                RemotingCommand cmd = RemotingCommand
                    .createResponseCommand("process netty RemotingCommand ok".getBytes());
                return cmd;
            }
        };
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(1000);
        ExecutorService executor = new ThreadPoolExecutor(1, 5, 1000, TimeUnit.MILLISECONDS,
            workQueue);
        NettyRemotingServer nserver = new NettyRemotingServer("8888", processor, executor);
        nserver.start();
        System.out.println("start netty server ok");
        System.out.println("end netty server ok");
    }
}
