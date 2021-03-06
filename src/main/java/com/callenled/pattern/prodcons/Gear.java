package com.callenled.pattern.prodcons;

/**
 * @Author: Callenld
 * @Date: 19-5-29
 */
public abstract class Gear<T, R> implements Runnable {

    /**
     * 生产者线
     */
    private Storage<T> producer;

    /**
     * 消费者线
     */
    private Storage<R> consumer;

    public void setProducer(Storage<T> producer) {
        this.producer = producer;
    }

    public void setConsumer(Storage<R> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (!this.producer.isStop()) {
            T t = this.producer.poll();
            if (t != null) {
                R r = null;
                try {
                    r = gear(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (r != null) {
                    this.push(r);
                }
            }
        }
        this.consumer.setStop();
    }

    /**
     * 齿轮
     *
     * @param t
     * @return
     */
    public abstract R gear(T t) throws Exception;

    /**
     * 插入队列
     *
     * @param r
     */
    public void push(R r) {
        if (r != null) {
            this.consumer.push(r, true);
        }
    }

    /**
     * 不重复插入队列
     *
     * @param r
     */
    public void push(R r, boolean checkRepeated) {
        if (r != null) {
            this.consumer.push(r, checkRepeated);
        }
    }
}
