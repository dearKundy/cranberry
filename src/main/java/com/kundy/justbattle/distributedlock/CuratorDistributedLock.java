package com.kundy.justbattle.distributedlock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * CuratorDistributedLock 是 Netflix 公司开源的一套 zookeeper 客户端框架
 * <p>
 * CuratorDistributedLock 分布式锁实现原理：http://www.dengshenyu.com/java/分布式系统/2017/10/23/zookeeper-distributed-lock.html
 *
 * @author kundy
 * @date 2019/8/21 1:27 PM
 */
@Slf4j
public class CuratorDistributedLock {

    public static void main(String[] args) throws Exception {

        // 重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // 创建 zk 客户端
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);

        client.start();

        //创建分布式锁, 锁空间的根节点路径为 /curator/lock
        InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");

        // 尝试获取锁
        mutex.acquire();

        log.info("Enter mutex");

        // 释放锁
        mutex.release();

        // 关闭客户端
        client.close();

    }

}
