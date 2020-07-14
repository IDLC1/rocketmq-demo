package com.tom.mq.rocketmq.delay;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

public class ScheduledMessageProducer {
    public static void main(String[] args) throws Exception {
        // 实例化一个生产者来产生延时消息
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("gtom.top:9876;gtom2.top:9876");
        // 启动生产者
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("DelayTopic", ("Hello scheduled message " + i).getBytes());
            // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
            message.setDelayTimeLevel(3);
            // 发送消息
            SendResult sendResult = producer.send(message);
            SendStatus status = sendResult.getSendStatus();
            System.out.println("发送结果： " + status);
        }
        // 关闭生产者
        producer.shutdown();
    }
}
