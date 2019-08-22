package com.kundy.cranberry.distributedlock;

import com.kundy.cranberry.service.CbMethodLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分布式锁 - 基于数据库实现
 * <p>
 * 思路：
 * 加锁：insert 唯一索引
 * 释放锁： delete 刚才新建的记录
 * <p>
 * 优点：简单容易理解
 * 缺点：
 * 1. 会有各种各样的问题，在解决问题的过程中会使整个方案变得越来越复杂。
 * 2. 操作数据库需要一定的开销，性能问题需要考虑。
 * 3. 使用数据库的行级锁并不一定靠谱，尤其是当我们的锁表并不大的时候。【这个是对另外一个基于数据库的实现方案而言 - 基于数据库自身的排它锁】
 *
 * @author kundy
 * @date 2019/8/19 9:54 PM
 */
@Slf4j
@Service
public class DbDistributedLock {

    /*
     * TODO 1. 该锁没有失效时间，需要做一个定时任务，定时清理超时数据（根据插入时间判断）
     * TODO 2. 该锁是非可重入的，需要在数据库表中加个字段，记录当前获得锁的机器的信息和线程信息，获取锁的时候先查数据库确定是否是同一个线程获取的锁。
     */


    @Autowired
    private CbMethodLockService lockService;

    /**
     * 非阻塞版本
     */
    public boolean tryLock() {
        boolean result = false;
        try {
            result = this.lockService.addMethod(this.getCallerMethodName());
        } catch (Exception ex) {
            log.warn("获取锁失败...");
        }
        return result;
    }

    /**
     * 阻塞版本，获取不到锁会自旋，如果超过指定次数都没有成功，则放弃锁
     */
    public boolean lockWithBlock() {
        Integer spinCount = 0;
        boolean result = false;
        while (!result) {
            if (spinCount < 10) {
                try {
                    result = this.lockService.addMethod(this.getCallerMethodName());
                } catch (Exception ex) {
                    log.warn("获取锁失败，自旋次数：" + spinCount++);
                    result = false;
                }
            } else {
                log.warn("自旋次数超过限制，放弃获取锁。。。");
                return false;
            }
        }
        return true;
    }

    public boolean unLock() {
        boolean result = false;
        try {
            result = this.lockService.deleteMethod(this.getCallerMethodName());
        } catch (Exception e) {
            log.warn("释放锁失败...");
        }
        return result;
    }

    /**
     * 获取当前调用 tryLock 方法的方法名
     */
    private String getCallerMethodName() {
        String callerMethodName = Thread.currentThread().getStackTrace()[3].toString();
        callerMethodName = callerMethodName.substring(0, callerMethodName.indexOf("("));
        return callerMethodName;
    }

}
