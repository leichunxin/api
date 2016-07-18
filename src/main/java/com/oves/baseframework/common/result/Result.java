package com.oves.baseframework.common.result;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.oves.baseframework.common.result.constants.StateEnum;

/**
 * @author darbean
 * @version Result.java, v0.1 6/21/16 10:16 darbean Exp $$
 */
public class Result<T extends Serializable> implements Serializable {

    /**
     * 返回状态, 成功、失败、处理中（进行中）、未知
     */
    private StateEnum rspState;

    /**
     * 请求ID, 请求的唯一单据号（可为空，涉及到资金必填。）
     */
    private String    reqNo;

    /**
     * 返回ID, 支付返回的单据号（可为空，涉及到资金必填。）
     */
    private String    rspNo;

    /**
     * 服务方返回代码。业务含义。（可为空）
     */
    private String    returnCode;

    /**
     * 返回给调用方的结果描述信息（可为空）
     */
    private String    returnMessage;

    /**
     * 返回的业务对象, 需要支持序列化
     */
    private T         data;

    public StateEnum getRspState() {
        return rspState;
    }

    public String getStateStr() {
        return rspState.getState();
    }

    public void setRspState(StateEnum rspState) {
        this.rspState = rspState;
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public String getRspNo() {
        return rspNo;
    }

    public void setRspNo(String rspNo) {
        this.rspNo = rspNo;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        if (StringUtils.isBlank(returnMessage) && rspState != null) {
            returnMessage = rspState.getDesc();
        }
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonStateObject = new JSONObject();
        jsonStateObject.put("rspState", rspState);
        jsonStateObject.put("reqNo", getReqNo());
        jsonStateObject.put("rspNo", getRspNo());
        jsonStateObject.put("returnCode", getReturnCode());
        jsonStateObject.put("returnMessage", getReturnMessage());
        jsonObject.put("state", jsonStateObject);
        jsonObject.put("data", getData());
        return jsonObject.toJSONString();
    }

    public static Result fromJson(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject state = jsonObject.getJSONObject("state");
        Serializable data = jsonObject.getObject("data", Serializable.class);
        Result result = new Result();
        result.setData(data);
        result.setRspState(Enum.valueOf(StateEnum.class, state.getString("rspState")));
        result.setReqNo(state.getString("reqNo"));
        result.setRspNo(state.getString("rspNo"));
        result.setReturnCode(state.getString("returnCode"));
        result.setReturnMessage(state.getString("returnMessage"));
        return result;
    }

    @Override
    public String toString() {
        return toJson();
    }
}
