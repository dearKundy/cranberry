package com.kundy.cranberry.javabasis.multithread.threadsignal.producerconsumermodel;

/**
 * 使用 wait、notifyAll 实现生产者、消费者模型
 * <p>
 * N个线程往分别向同一个固定容量的容器中 塞【生产者】和 取 【消费者】元素，保证线程安全。
 *
 * 当容器满了，生产者线程应该停止生产并进入等待状态；当容器为空的时候，消费者线程应该停止消费并进入等待状态。
 *
 * @author kundy
 * @date 2020/7/22 2:27 PM
 */
public class PAndCMain {

    public static void main(String[] args) {

    }
}
