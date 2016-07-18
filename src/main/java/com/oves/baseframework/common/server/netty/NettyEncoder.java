package com.oves.baseframework.common.server.netty;

import com.oves.baseframework.common.server.common.RemotingUtil;
import com.oves.baseframework.common.server.protocol.RemotingCommand;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;


/**
 * @author jin.qian
 * @version $Id: NettyEncoder.java, v 0.1 2016年1月15日 下午5:12:39 jin.qian Exp $
 */
public class NettyEncoder extends MessageToByteEncoder<RemotingCommand> {
    private static final Logger log = LoggerFactory.getLogger(RemotingUtil.RemotingLogName);

    @Override
    public void encode(ChannelHandlerContext ctx, RemotingCommand cmd, ByteBuf out)
            throws Exception {
        try {
            int length = 0;
            if (cmd.getBody() != null) {
                length += cmd.getBody().length;
            }

            ByteBuffer result = ByteBuffer.allocate(8 + length);
            String preLen = String.format("%08d", length);
            // length
            result.put(preLen.getBytes(NettyServerConfig.DEFAULT_CHARSET));
            // body data;
            if (cmd.getBody() != null) {
                result.put(cmd.getBody());
            }

            result.flip();

            out.writeBytes(result);
        } catch (Exception e) {
            log.error("encode exception, " + RemotingUtil.parseChannelRemoteAddr(ctx.channel()), e);
            if (cmd != null) {
                log.error(cmd.toString());
            }
            RemotingUtil.closeChannel(ctx.channel());
        }
    }
}
