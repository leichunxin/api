package com.oves.baseframework.common.result.constants;

/**
 * @author darbean
 * @version StateEnum.java, v0.1 6/21/16 10:22 darbean Exp $$
 */
public enum StateEnum {

                       SUCCESS("1", "成功"), //成功
                       FAIL("2", "失败"), //失败
                       PROCESSING("3", "进行中"), //进行中
                       UNKNOWN("4", "未知"),//未知
    ;

    public String state;
    public String desc;

    StateEnum(String state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public String getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return state;
    }
}
