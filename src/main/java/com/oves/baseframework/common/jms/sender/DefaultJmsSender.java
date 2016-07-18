package com.oves.baseframework.common.jms.sender;

import org.springframework.jms.core.JmsTemplate;

/**
 * Created by darbean on 3/8/16.
 */
public class DefaultJmsSender {

    private JmsTemplate jmsTemplate;

    public void send(Object obj) {
        jmsTemplate.convertAndSend(obj);
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
