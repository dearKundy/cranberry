package com.kundy.cranberry.controller;

import com.kundy.cranberry.systemdesign.distributedlock.RedisDistributedLock;
import com.kundy.cranberry.systemdesign.distributedlock.DbDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author kundy
 * @date 2019/8/20 9:28 AM
 */
@Slf4j
@RestController
public class MainController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private DbDistributedLock dbDistributedLock;

    @Autowired
    private RedisDistributedLock redisDistributedLock;
    
    @GetMapping("/testDbDistributedLock")
    public String testDbDistributedLock() {
        if (this.dbDistributedLock.lockWithBlock()) {
            log.info("DB 分布式锁, i come in.... 端口号：" + port);
            this.dbDistributedLock.unLock();
        }
        return "success";
    }

    @GetMapping("/testRedisDistributedLock")
    public String testRedisDistributedLock() {
        String requestId = UUID.randomUUID().toString() + Thread.currentThread().getId();
        if (this.redisDistributedLock.tryLock("test001", requestId)) {
            log.info("redis 分布式锁, i come in.... 端口号：" + port + " 当前线程：" + Thread.currentThread().getName());
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean flag = this.redisDistributedLock.unLock("test001", requestId);
            if (flag) {
                log.info("释放锁成功" + " 当前线程：" + Thread.currentThread().getName());
            }
        } else {
            log.info("获取 redis 分布式锁失败。。。。");
        }
        return "success";
    }

}
