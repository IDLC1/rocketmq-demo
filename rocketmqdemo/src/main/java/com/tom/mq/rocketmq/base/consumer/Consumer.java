package com.tom.mq.rocketmq.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @File: Consumer
 * @Description:
 * @Author: tom
 * @Create: 2020-07-01 09:17
 **/
public class Consumer {

    public static void main(String[] args) throws Exception {
        // 创建消费者 consumer，指定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 指定 Nameserver 地址
        consumer.setNamesrvAddr("gtom.top:9876;gtom2.top:9876");
        // 订阅主题 Topic 和 Tag
        consumer.subscribe("base2", "Tag1");
        // 设置为负载均衡模式消费
        consumer.setMessageModel(MessageModel.BROADCASTING);
        // 设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            // 接收消息内容
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt msg : msgs) {
                    System.out.println(new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者consumer
        consumer.start();
    }
}
