package com.oves.baseframework.common.mq.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.oves.baseframework.common.util.LogConstants;
import com.oves.baseframework.common.util.LogUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MetaQ数据接收客户端，主要功能： -- 订阅metaQ数据
 *
 * @author jin.qian
 * @version $Id: MetaQEventClient.java, v 0.1 2015年10月23日 下午8:46:58 jin.qian Exp $
 */
public class MetaQEventClient implements MetaQConsumerClient {

    private static final Logger logger = LoggerFactory.getLogger(LogConstants.METAQ_EVENT);

    private String instanceName;
    private String consumerGroup;
    private String namesrvAddr;
    private String consumeThreadMax;
    private String consumeThreadMin;

    /**
     * MetaQ 相关参数配置
     */
    private String topic;
    private String tags;

    /**
     * metaq 并发线数
     */
    protected int consumerCorePoolSize = 1;

    /**
     * metaq 消费挂起
     */
    protected boolean consumerSuspended = false;

    /**
     * metaQ 数据消费者
     */
    protected DefaultMQPushConsumer consumer;

    /**
     * metaQ 消息处理主逻辑实现
     */
    private MessageListenerConcurrently messageListener;

    /**
     * metaq consumer内部具有并发能力，并发数可配置，建议应用这边只起一个consumer，利于并发性能调整。
     *
     * @throws InterruptedException
     * @throws MQClientException
     */
    protected void init() throws MQClientException {
        LogUtils.info(logger, "Starting MetaQ Consumer...");
        if (namesrvAddr.contains(":")) {
            consumer = new DefaultMQPushConsumer();
            consumer.setNamesrvAddr(namesrvAddr);
        } else {
            System.setProperty("rocketmq.namesrv.domain", namesrvAddr);
            consumer = new DefaultMQPushConsumer();
        }
        consumer.setInstanceName(instanceName);
        consumer.setConsumerGroup(consumerGroup);
        consumer.setConsumeThreadMin(Integer.parseInt(consumeThreadMin));
        consumer.setConsumeThreadMax(Integer.parseInt(consumeThreadMax));
        LogUtils.info(logger, "consumer.consumerGroup:%s", consumer.getConsumerGroup());
        // 重上次消费进度消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 添加metaQ订阅关系
        consumer.subscribe(topic, tags);
        LogUtils.info(logger, "Consumer Subscription:%s", consumer.getSubscription());

        if (consumerSuspended) {
            consumer.suspend();
        } else {
            consumer.resume();
        }
        LogUtils.info(logger, "MetaQEventClient conf loaded sucess.[%s,%s]", "metaq_consumer_suspended", consumerSuspended);
        // 挂载数据消费实例
        consumer.registerMessageListener(messageListener);
        LogUtils.info(logger, "Ready to start consumer...[%s]", consumer);
        // 启动消费者
        consumer.start();
        LogUtils.info(logger, "MetaQ Consumer Started.");
    }

    @Override
    public void updateConsumerCorePoolSize(int corePoolSize) {
        consumer.updateCorePoolSize(corePoolSize);
    }

    @Override
    public MessageExt viewMessage(String msgId) throws Exception {
        if (StringUtils.isEmpty(msgId)) {
            return null;
        }
        return consumer.viewMessage(msgId);
    }

    public MessageListenerConcurrently getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(MessageListenerConcurrently messageListener) {
        this.messageListener = messageListener;
    }

    public void setConsumerCorePoolSize(int consumerCorePoolSize) {
        this.consumerCorePoolSize = consumerCorePoolSize;
    }

    public int getConsumerCorePoolSize() {
        return consumerCorePoolSize;
    }

    public void setConsumerSuspended(boolean consumerSuspended) {
        this.consumerSuspended = consumerSuspended;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getConsumeThreadMax() {
        return consumeThreadMax;
    }

    public void setConsumeThreadMax(String consumeThreadMax) {
        this.consumeThreadMax = consumeThreadMax;
    }

    public String getConsumeThreadMin() {
        return consumeThreadMin;
    }

    public void setConsumeThreadMin(String consumeThreadMin) {
        this.consumeThreadMin = consumeThreadMin;
    }

    public boolean isConsumerSuspended() {
        return consumerSuspended;
    }

    public void suspendConsumer() {
        consumer.suspend();
    }

    public void resumeConsumer() {
        consumer.resume();
    }
}
