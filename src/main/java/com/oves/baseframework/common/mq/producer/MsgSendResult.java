package com.oves.baseframework.common.mq.producer;

/**
 * 消息处理结果类
 * <pre>
 * 包含序列化、发送两个环节
 * </pre>
 *
 * @author jin.qian
 * @version $Id: MsgSendResult.java, v 0.1 2015年10月23日 下午10:58:26 jin.qian Exp $
 */
public class MsgSendResult {
    /**
     * 是否发送成功
     */
    private boolean sendSuccess;

    /**
     * 结果码，包含发送成功，序列化失败，发送失败等值
     */
    private String resultCode;

    /**
     * 若发送成功返回messageId
     */
    private String messageId;

    /**
     * genSuccessResult
     *
     * @param messageId
     * @return
     */
    public static MsgSendResult genSuccessResult(String messageId) {
        MsgSendResult successResult = new MsgSendResult(true,
                MsgSendResultCode.SEND_SUCCESS.name(), messageId);
        return successResult;
    }

    /**
     * genFailedResult
     *
     * @param errorCode
     * @return
     */
    public static MsgSendResult genFailedResult(String errorCode) {
        MsgSendResult failedResult = new MsgSendResult(false, errorCode, null);
        return failedResult;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "MsgSendResult [sendSuccess=" + sendSuccess + ", resultCode=" + resultCode
                + ", messageId=" + messageId + "]";
    }

    /**
     * @param sendSuccess
     * @param resultCode
     * @param messageId
     */
    private MsgSendResult(boolean sendSuccess, String resultCode, String messageId) {
        super();
        this.sendSuccess = sendSuccess;
        this.resultCode = resultCode;
        this.messageId = messageId;
    }

    /**
     * isSendSuccess
     *
     * @return
     */
    public boolean isSendSuccess() {
        return sendSuccess;
    }

    /**
     * getResultCode
     *
     * @return
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * getMessageId
     *
     * @return
     */
    public String getMessageId() {
        return messageId;
    }
}
