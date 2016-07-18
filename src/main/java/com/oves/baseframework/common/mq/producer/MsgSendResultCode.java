package com.oves.baseframework.common.mq.producer;

/**
 * 
 * @author jin.qian
 * @version $Id: MsgSendResultCode.java, v 0.1 2015年10月23日 下午10:58:13 jin.qian Exp $
 */
public enum MsgSendResultCode {
    /** 消息发送成功 */
    SEND_SUCCESS,
    /** 消息发送失败 */
    SEND_FAILED,
    /** 序列化失败 */
    SERIALIZABLE_FAILED;
}
