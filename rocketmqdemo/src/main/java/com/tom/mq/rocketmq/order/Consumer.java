package com.tom.mq.rocketmq.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @File: Consumer
 * @Description:
 * @Author: tom
 * @Create: 2020-07-01 10:54
 **/
public class Consumer {
    public static void main(String[] args) throws Exception {
        // 创建消费者 consumer，指定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 指定 Nameserver 地址
        consumer.setNamesrvAddr("gtom.top:9876;gtom2.top:9876");
        // 订阅主题 Topic 和 Tag
        consumer.subscribe("OrderTopic", "Order");
        // 设置为负载均衡模式消费
//        consumer.setMessageModel(MessageModel.BROADCASTING);
        // 处理消息
        consumer.registerMessageListener(new MessageListenerOrderly() {
            //  对于一个队列的消息，只采用一个线程去处理
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for (MessageExt msg : list) {
                    System.out.println("线程名称：【" + Thread.currentThread().getName() + "】：" + new String(msg.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        // 启动消费者consumer
        consumer.start();

        System.out.println("消费者启动");
    }
}
