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
        try {
            producer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        storage.setStop();
    }

    /**
     * 生产
     */
    public abstract void producer() throws Exception;

    /**
     * 插入队列
     *
     * @param t
     */
    public void push(T t) {
        if (t != null) {
            storage.push(t, true);
        }
    }

    /**
     * 插入队列
     *
     * @param t
     */
    public void push(T t, boolean checkRepeated) {
        if (t != null) {
            storage.push(t, checkRepeated);
        }
    }
}
