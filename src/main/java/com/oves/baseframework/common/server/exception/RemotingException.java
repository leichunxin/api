package com.oves.baseframework.common.server.exception;

/**
 * 通信层异常父类
 * 
 * @author shijia.wxr<vintage.wang@gmail.com>
 * @since 2013-7-13
 */
public class RemotingException extends Exception {
    private static final long serialVersionUID = -5690687334570505110L;

    public RemotingException(String message) {
        super(message);
    }

    public RemotingException(String message, Throwable cause) {
        super(message, cause);
    }
}
