package com.kundy.cranberry.javabasis.multithread.threadsignal;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 启动两个线程, 一个输出 1,3,5,7…99, 另一个输出 2,4,6,8…100 最后 STDOUT 中按序输出 1,2,3,4,5…100
 *
 * @author kundy
 * @date 2020/7/29 9:39 PM
 */
public class AlternateOutput {

    private final Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Integer count = 1;

    public static void main(String[] args) {
        AlternateOutput alternateOutput = new AlternateOutput();

        alternateOutput.printA();
        alternateOutput.printB();

    }

    /**
     * 输出基数
     */
    public void printA() {
        new Thread(() -> {
            while (count < 100) {
                try {
                    lock.lock();

                    while (count % 2 == 0) {
                        conditionA.await();
                    }

                    System.out.println(Thread.currentThread().getName() + "-" + count);
                    count++;
                    conditionB.signalAll();

                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }

    /**
     * 输出偶数
     */
    public void printB() {
        new Thread(() -> {

            while (count < 100) {
                try {

                    lock.lock();

                    while (count % 2 != 0) {
                        conditionB.await();
                    }

                    System.out.println(Thread.currentThread().getName() + "-" + count);
                    count++;
                    conditionA.signalAll();

                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }
            }

        }).start();
    }

}
