package com.callenled.pattern.prodcons;

/**
 * @Author: Callenld
 * @Date: 19-5-30
 */
public abstract class End {

    private int consumers;

    /**
     * 消费者线程
     *
     * @param consumers
     */
    public void setConsumers(int consumers) {
        this.consumers = consumers;
    }

    /**
     * 监听
     */
    public void setStop() {
        if (--this.consumers == 0) {
            this.run();
        }
    }

    /**
     * 执行
     */
    public abstract void run();
}
