package com.oves.baseframework.common.mq.consumer;

import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * metaQ消费者客户端接口
 *
 * @author jin.qian
 * @version $Id: MetaQConsumerClient.java, v 0.1 Sep 29, 2013 12:09:38 PM jin.qian Exp $
 */
public interface MetaQConsumerClient {

    /**
     * 暂停metaQ消费器
     *
     * @throws Exception
     */
    public void suspendConsumer();

    /**
     * 继续metaQ消费器
     *
     * @throws Exception
     */
    public void resumeConsumer();

    /**
     * 修改metaQ消费线程数
     *
     * @throws Exception
     */
    public void updateConsumerCorePoolSize(int corePoolSize);

    /**
     * 根据msg id 获得metaq 消息
     *
     * @param msgId
     * @return MessageExt
     * @throws Exception
     */
    public MessageExt viewMessage(String msgId) throws Exception;
}