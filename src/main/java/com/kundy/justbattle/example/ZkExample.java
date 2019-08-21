package com.kundy.justbattle.example;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * ZkClient 简单使用
 *
 * @author kundy
 * @date 2019/8/21 10:36 AM
 */
@Slf4j
public class ZkExample {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        log.info("ZK 连接成功");

        // 创建一个永久结点
//        zkClient.createPersistent("/zkClient001","test data");

        // 创建一个序列化的永久结点
//        zkClient.createPersistentSequential("/zkClient001","test sequential");

        // 创建一个临时结点
//        zkClient.createEphemeral("/tmpNode", "test tmp node");

        // 创建一个序列化临时结点
//        zkClient.createEphemeralSequential("/tmpNode", "test tmp node sequential");

        // 删除结点 - 要指定全称，不能模糊匹配
//        zkClient.delete("/myBoy0000000009");

        // 递归删除
//        zkClient.deleteRecursive("/myBoy");

        // 获取结点列表
//        List<String> children = zkClient.getChildren("/");

        // 读取结点内容
//        String node = zkClient.readData("/zkClient0010000000013");

        // 更新数据
//        zkClient.writeData("/zkClient0010000000013", "update test");

        // 检测结点是否存在
//        boolean exists = zkClient.exists("/zkClient0010000000013");

        // 注册监听 【监听结点变化】
        zkClient.subscribeDataChanges("/zkClient0010000000013", new IZkDataListener() {

            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                log.info("结点：" + s + "数据发生变化，最新的值为：" + (String) o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                log.info("结点：" + s + "被删除");
            }

        });

        for (int i = 0; i < 10; i++) {
            zkClient.writeData("/zkClient0010000000013", "test listener" + i);
        }

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 关闭连接
        zkClient.close();
    }

}
