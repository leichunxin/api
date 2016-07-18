package com.oves.baseframework.common.server.netty;

/**
 * Netty产生的事件类型
 * @author jin.qian
 * @version $Id: NettyEventType.java, v 0.1 2016年1月15日 下午5:09:48 jin.qian Exp $
 */
public enum NettyEventType {
    CONNECT,
    CLOSE,
    IDLE,
    EXCEPTION
}
