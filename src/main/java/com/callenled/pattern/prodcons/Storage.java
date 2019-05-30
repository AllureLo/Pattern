package com.callenled.pattern.prodcons;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: Callenld
 * @Date: 19-5-17
 */
public class Storage<T> {

    private BlockingQueue<T> queues;

    /**
     * 生产线数量
     */
    private int threads;


    public Storage() {
        this.queues = new LinkedBlockingQueue<>(50);
    }

    public Storage(int capacity) {
        this.queues = new LinkedBlockingQueue<>(capacity);
    }

    /**
     * 生产线数量
     *
     * @param threads
     */
    public void setThreads(int threads) {
        this.threads = threads;
    }

    /**
     * 生产
     *
     * @param t 产品
     */
    public void push(T t) {
        try {
            this.queues.put(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("生产者---队列大小：" + this.queues.size());
    }

    /**
     * 消费
     *
     * @return t
     */
    public T poll() {
        if (this.isStop()) {
            return null;
        } else if (this.queues.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return poll();
        }
        T t = this.queues.poll();
        System.out.println("消费者---队列大小：" + this.queues.size());
        return t;
    }

    /**
     * 是否停止
     * @return
     */
    public boolean isStop() {
        return this.queues.isEmpty() && this.threads == 0;
    }

    /**
     * 设置某个线程停止
     */
    public synchronized void setStop() {
        this.threads--;
    }
}
