package com.tom.mq.rocketmq.base.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        // 创建消息生产者producer，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        // 指定 Nameserver 地址
        producer.setSendMsgTimeout(6000);
        producer.setVipChannelEnabled(false);
        producer.setNamesrvAddr("gtom.top:9876;gtom2.top:9876");
        // 启动 producer
        producer.start();
        for (int i = 0; i < 3000; i++) {
            // 创建消息对象，指定主体 Topic、Tag 和消息体
            Message message = new Message("base2", "Tag1", ("Hello World" + i).getBytes());
            // 发送消息
            SendResult result = producer.send(message);
            // 发送状态、ID以及接受消息的队列的ID
            SendStatus status = result.getSendStatus();
            String msgId = result.getMsgId();
            int queueId = result.getMessageQueue().getQueueId();
            System.out.println("发送状态： " + status + " 消息ID： " + msgId + " 队列： " + queueId);

//            TimeUnit.SECONDS.sleep(1);
        }
        // 关闭 producer
        producer.shutdown();
    }
}
