package com.oves.baseframework.common.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.net.SocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TCP 原生socket调用
 */
public class DefaultSocketClient {

    private static Logger logger = LoggerFactory.getLogger(DefaultSocketClient.class);
    final static String defaultCharset = "GB18030";
    private static int timeout = 10;

    public static String sendReceive(String ip, int port, String xml) {
        Socket client = null;
        try {
            client = createConnection(ip, port);
            byte[] tmpDat = xml.getBytes(defaultCharset);
            writeWithPreLen(client.getOutputStream(), tmpDat);
        } catch (Exception e) {
            logger.error("DefaultSocketClient sendReceive Exception, 发送失败", e);
            closeConnection(client);
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            if (read(client.getInputStream(), bos, 8) <= 0) {
                logger.error("DefaultSocketClient recv error, 接收异常");
                return null;
            }
            return new String(bos.toByteArray(), defaultCharset);
        } catch (IOException e) {
            logger.error("DefaultSocketClient sendReceive Exception, 状态不明", e);
            return null;
        } finally {
            closeConnection(client);
        }
    }

    private static Socket createConnection(String host, int port) throws IOException {
        Socket socket = SocketFactory.getDefault().createSocket();
        socket.connect(new InetSocketAddress(host, port), timeout * 1000);
        socket.setSoTimeout(timeout * 1000);
        socket.setTcpNoDelay(true);
        return socket;
    }

    private static void closeConnection(Socket socket) {
        if (socket == null) {
            return;
        }

        try {
            socket.close();
        } catch (IOException e) {
            logger.error("DefaultSocketClient closeConnection IOException", e);
        }
    }

    private static void writeWithPreLen(OutputStream out, byte[] data) throws IOException {
        byte[] dataToSend = new byte[8 + data.length];
        String preLen = String.format("%08d", data.length);
        ByteBuffer bb = ByteBuffer.wrap(dataToSend);
        bb.put(preLen.getBytes());
        bb.put(data);
        bb.compact();
        out.write(bb.array());
        out.flush();
    }

    private static int read(InputStream in, OutputStream out, int preLen) throws IOException {
        int len = readLen(in, preLen);
        if (len <= 0) {
            return 0;
        }

        int count = 0;
        byte[] buf = new byte[1024];
        while (count < len) {
            int readlen = in.read(buf, 0, Math.min(len - count, 1024));
            //如果正在读取时对方关闭socket，则退出循环
            if (readlen == -1)
                break;
            out.write(buf, 0, readlen);
            count += readlen;
        }

        if (count != len) {
            throw new IOException("data is not receive completed:(" + len + "," + count + ")");
        }
        return len;
    }

    private static int readLen(InputStream in, int prelen) throws IOException {
        if (prelen <= 0) {
            return 0;
        }

        byte[] lendata = new byte[prelen];
        int count = 0, c, off = 0;

        while ((c = in.read()) != -1) {
            lendata[off++] = (byte) c;
            count++;
            if (count == prelen) {
                break;
            }
        }

        if (count != prelen) {
            return 0;
        }

        int len = Integer.parseInt(new String(lendata));
        return len;
    }
}
