package com.callenled.pattern.prodcons;

/**
 * @Author: Callenld
 * @Date: 19-5-29
 */
public abstract class Producer<T> implements Runnable {

    private Storage<T> storage;

    public void setStorage(Storage<T> storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        producer();
        storage.setStop();
    }

    /**
     * 生产
     */
    public abstract void producer();

    /**
     * 入队
     *
     * @param t
     */
    public void push(T t) {
        storage.push(t);
    }
}
