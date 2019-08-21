package com.kundy.justbattle.distributedlock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

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

    public void doWork() throws Exception {
        CuratorFramework client = this.initClient();

        String lockPath = "/curator/lock";
        this.doLockLogic(client, lockPath);

        // 关闭客户端
        client.close();
    }

    private CuratorFramework initClient() {
        // 重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // 创建 zk 客户端
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);

        client.start();

        return client;
    }

    /**
     * @param lockPath 锁空间的根节点路径
     */
    private void doLockLogic(CuratorFramework client, String lockPath) throws Exception {

        //创建分布式锁
        InterProcessMutex lock = new InterProcessMutex(client, lockPath);

        // 尝试获取锁失败
        if (!lock.acquire(5, TimeUnit.SECONDS)) {
            throw new IllegalStateException("try acquire the lock fail");
        }
        try {
            log.info("success to get the lock");
        } finally {
            log.info("success to releasing the lock");
            // 释放锁
            lock.release();
        }
    }

}
