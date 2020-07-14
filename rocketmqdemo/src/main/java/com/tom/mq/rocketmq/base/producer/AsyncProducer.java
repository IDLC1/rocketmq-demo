package com.tom.mq.rocketmq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;

public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        // 指定 Nameserver 地址
        producer.setNamesrvAddr("gtom.top:9876;gtom2.top:9876");
        // 启动Producer实例
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);


        for (int i = 0; i < 300; i++) {
            final int index = i;
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("base2", "Tag3", ("Async Msg index = " + i).getBytes());
            producer.send(msg, new SendCallback() {
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送结果：" + sendResult);
                }

                public void onException(Throwable e) {
                    System.out.println("发送异常：" + e);
//                    e.printStackTrace();
                }
            });
            TimeUnit.SECONDS.sleep(1);
        }
//        Thread.sleep(100000);
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
