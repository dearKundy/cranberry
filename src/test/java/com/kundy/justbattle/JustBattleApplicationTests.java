package com.kundy.justbattle;

import com.kundy.justbattle.dblock.OptimismLock;
import com.kundy.justbattle.dblock.PessimisticLock;
import com.kundy.justbattle.distributedlock.DbDistributedLock;
import com.kundy.justbattle.model.po.JbUserPo;
import com.kundy.justbattle.ratelimiter.RedisRateLimiter;
import com.kundy.justbattle.transaction.AnnotationTx;
import com.kundy.justbattle.transaction.ProgrammingTx;
import com.kundy.justbattle.transaction.TemplateTx;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JustBattleApplicationTests {

    @Autowired
    private ProgrammingTx programmingTx;

    @Autowired
    private TemplateTx templateTx;

    @Autowired
    private AnnotationTx annotationTx;

    @Autowired
    private PessimisticLock pessimisticLock;

    @Autowired
    private OptimismLock optimismLock;

    @Autowired
    private RedisRateLimiter limiter;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DbDistributedLock dbDistributedLock;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testProgramingTx() {
        JbUserPo jbUserPo = new JbUserPo().setName("heyJude").setPassword("0000");
        boolean flag = this.programmingTx.go(jbUserPo);
        System.out.println(flag);
    }

    @Test
    public void testTemplateTx() {
        JbUserPo jbUserPo = new JbUserPo().setName("yahu").setPassword("0000");
        boolean flag = this.templateTx.go(jbUserPo);
        System.out.println(flag);
    }

    @Test
    public void testAnnotationTx() {
        JbUserPo jbUserPo = new JbUserPo().setName("yiming").setPassword("0000");
        boolean flag = this.annotationTx.go(jbUserPo);
        System.out.println(flag);
    }

    @Test
    public void testDbLock() {
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                this.optimismLock.saleWithSmallGranularityLock(1);
                latch.countDown();
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLimiter() {

        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (limiter.tryAcquire("rate.limit:key1", 50)) {
                        System.out.println("pass...");
                    } else {
                        System.out.println("no pass...");
                    }
                }
            }).start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDbDistributedLock() {

        Integer[] ports = {8083};

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    Integer port = getPortRandom(ports);
                    String url = "http://localhost:" + port + "/testRedisDistributedLock";
                    this.restTemplate.getForObject(url, String.class);
                }
            }).start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Integer getPortRandom(Integer[] ports) {
        return ports[new Random().nextInt(ports.length)];
    }



}
