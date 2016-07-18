package com.oves.baseframework.common.mq.producer;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.oves.baseframework.common.util.LogConstants;
import com.oves.baseframework.common.util.LogUtils;
import com.oves.baseframework.util.SerializeUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * metaq消息发布者
 *
 * @author jin.qian
 * @version $Id: MetaqMessagePublisherImpl.java, v 0.1 2015年4月14日 下午1:56:09 jin.qian Exp $
 */
public class MetaqMessagePublisherImpl implements MetaqMessagePublisher {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(LogConstants.METAQ_EVENT);

    /**
     * 生产者
     */
    private DefaultMQProducer producer;

    /**
     * MetaQ 相关参数配置
     */
    private String groupId;

    private String namesrvaddr;

    /**
     * 初始化方法
     */
    public void init() {
        // 设置 nameserver 地址
        if (producer == null) {
            if (!namesrvaddr.contains(":")) {
                System.setProperty("rocketmq.namesrv.domain", namesrvaddr);
                producer = new DefaultMQProducer(groupId);
            } else {
                producer = new DefaultMQProducer(groupId);
                producer.setNamesrvAddr(namesrvaddr);
            }
        }
        try {
            producer.start();
        } catch (MQClientException e) {
            logger.error("metaQ producer start failed!", e);
        }
    }

    @Override
    public MsgSendResult sendMessage(Message message) {
        try {
            SendResult res = producer.send(message);
            if (res == null) {
                LogUtils.error(logger, "result of send MQ message is null.");
                return MsgSendResult.genFailedResult(MsgSendResultCode.SEND_FAILED.name());
            }
            LogUtils.info(logger, getDigestString(res, message));

            if (SendStatus.SEND_OK == res.getSendStatus()) {
                return MsgSendResult.genSuccessResult(res.getMsgId());
            }
        } catch (Exception e) {
            LogUtils.error(logger, e, "meta send message failed. message=[%s]", message);
        }
        return MsgSendResult.genFailedResult(MsgSendResultCode.SEND_FAILED.name());
    }

    /**
     * 结果日志
     *
     * @param res
     * @param message
     * @return
     */
    private String getDigestString(SendResult res, Message message) {
        StringBuffer sb = new StringBuffer();
        sb.append("MQ SendResult:");
        sb.append("(");
        sb.append(res.getSendStatus()).append(",");
        sb.append(res.getMsgId()).append(",");
        sb.append(message.getTopic()).append(",");
        sb.append(message.getTags()).append(",");
        if (res.getMessageQueue() != null) {
            sb.append(res.getMessageQueue().getBrokerName()).append(",");
            sb.append(res.getMessageQueue().getQueueId()).append(",");
        }
        sb.append(res.getQueueOffset());
        sb.append(")");
        return sb.toString();
    }

    @Override
    public <T extends Serializable> MsgSendResult sendMessage(String topic, String tag, T payload, Map<String, String> properties) throws IOException {
        Message msg = null;

        // 序列化主对象
        byte[] byteArray = SerializeUtil.serialize(payload);
        if (byteArray == null) {
            LogUtils.error(logger, "meta hessian2Serialize failed when send message,topic=%s,tag=%s,properties=%s", topic, tag, properties);
            return MsgSendResult.genFailedResult(MsgSendResultCode.SERIALIZABLE_FAILED.name());
        }

        // 构造message
        msg = new Message(topic, byteArray);
        // 加入扩展信息
        if (!CollectionUtils.isEmpty(properties)) {
            Map<String, String> currentProperties = msg.getProperties();
            currentProperties.putAll(properties);
        }

        msg.setTags(tag);
        MsgSendResult res = this.sendMessage(msg);
        return res;
    }

    /**
     * destroy
     */
    public void destroy() {
        if (producer != null) {
            producer.shutdown();
        }
    }

    /**
     * setProducer
     *
     * @param producer
     */
    public void setProducer(DefaultMQProducer producer) {
        this.producer = producer;
    }

    /**
     * setGroupId
     *
     * @param groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getNamesrvaddr() {
        return namesrvaddr;
    }

    public void setNamesrvaddr(String namesrvaddr) {
        this.namesrvaddr = namesrvaddr;
    }

    public String getGroupId() {
        return groupId;
    }
}
