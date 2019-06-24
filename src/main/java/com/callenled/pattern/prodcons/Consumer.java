package com.callenled.pattern.prodcons;

/**
 * @Author: Callenld
 * @Date: 19-5-29
 */
public abstract class Consumer<T> implements Runnable {

    private Storage<T> storage;

    private End end;

    public void setStorage(Storage<T> storage) {
        this.storage = storage;
    }

    public void setEnd(End end) {
        this.end = end;
    }

    @Override
    public void run() {
        while (!this.storage.isStop()) {
            T t = this.storage.poll();
            if (t != null) {
                try {
                    this.consumer(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.end != null) {
            this.end.setStop();
        }
    }

    /**
     * 消费
     *
     * @param t
     */
    public abstract void consumer(T t) throws Exception;
}
