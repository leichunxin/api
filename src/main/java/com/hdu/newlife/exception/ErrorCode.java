package com.hdu.newlife.exception;

import com.hdu.newlife.bean.ErrorBean;


/**
 * 类名:      ErrorCodeEnum
 * 描述:     错误代码
 * @author newlife
 *
 */
public enum ErrorCode {

    UNKNOWN(-1, "未知");

    private int    errorCode;

    private String errorMessage;

    ErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorBean getErrorBean(String bindKey, Object... args) {
        String message = this.getErrorMessage();
        for (int i = 0; i < args.length; i++) {
            message = message.replace("{" + i + "}", args[i].toString());
        }
        return new ErrorBean().setField(bindKey).setErrorCode(this.name()).setErrorMsg(message);
    }

}
