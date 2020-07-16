package com.kundy.cranberry.thirdparty.rocketmq.example.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author kundy
 * @date 2020/7/11 2:21 PM
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("test_sync_group");
        producer.setNamesrvAddr("local:9876");
        producer.start();

        for (int i = 0; i < 100; i++) {
            Message msg = new Message("TopicTest", "TagA", ("Hello RocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }

        producer.shutdown();

    }

}
