package com.tom.mq.rocketmq.order;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * @File: Producer
 * @Description:
 * @Author: tom
 * @Create: 2020-07-01 10:37
 **/
public class Producer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("gtom.top:9876;gtom2.top:9876");
        producer.start();

        List<OrderStep> orderSteps = OrderStep.buildOrders();
        int i = 0;
        for (OrderStep orderStep : orderSteps) {
            String body = orderStep.toString();
            Message msg = new Message("OrderTopic", "Order", "i = " + i, body.getBytes());
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                /**
                 *
                 * @param mqs 队列集合
                 * @param message 消息对象
                 * @param arg 业务标识的参数
                 * @return
                 */
                public MessageQueue select(List<MessageQueue> mqs, Message message, Object arg) {
                    long orderId = Long.valueOf(String.valueOf(arg));
                    long index = orderId % mqs.size();
                    return mqs.get((int) index);
                }
            }, orderStep.getOrderId());
            System.out.println("发送结果：" + sendResult);
            i++;
        }
        producer.shutdown();
    }
}
