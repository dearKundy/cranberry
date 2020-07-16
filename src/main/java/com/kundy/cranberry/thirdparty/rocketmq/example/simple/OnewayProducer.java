package com.kundy.cranberry.thirdparty.rocketmq.example.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author kundy
 * @date 2020/7/11 3:01 PM
 */
public class OnewayProducer {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("one_way_group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message msg = new Message("TopicTest", "TagA", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.sendOneway(msg);
        }
        Thread.sleep(5000);
        producer.shutdown();

    }

}
