package com.oves.baseframework.common.mq.producer;

import com.alibaba.rocketmq.common.message.Message;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * metaq发布消息接口
 *
 * @author jin.qian
 * @version $Id: MetaqMessagePublisher.java, v 0.1 2015年10月23日 下午10:56:36 jin.qian Exp $
 */
public interface MetaqMessagePublisher {

    /**
     * 发送metaq消息
     *
     * @param message
     * @return
     */
    public MsgSendResult sendMessage(Message message);


    /**
     * 发送metaq消息
     *
     * @param topic      要发送的topic
     * @param tag        要发送的tag
     * @param payload    实际要发送的数据，裸格式
     * @param properties 封装在message中的properties属性，需要业务自己封装好需要携带的参数
     * @throws IOException
     */
    public <T extends Serializable> MsgSendResult sendMessage(String topic, String tag, T payload, Map<String, String> properties) throws IOException;
}
