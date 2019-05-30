package com.callenled.pattern;

import com.callenled.pattern.prodcons.*;

/**
 * @Author: Callenld
 * @Date: 19-5-29
 */
public class Demo {

    public static void main(String[] args) {

        ProdConsModel.<String, String>builders(30)
                .setStart(new Start() {
                    @Override
                    public void run() {
                        System.out.println(System.currentTimeMillis());
                    }
                })
                .setProducer(new Producer<String>() {
                    @Override
                    public void producer() {
                        for (int i = 0; i < 10; i++) {
                            push("i:" + i);
                        }
                    }
                }, 3)
                .setGear(new Gear<String, String>() {
                    @Override
                    public String gear(String s) {
                        return "sssssss" + s;
                    }
                }, 3)
                .setConsumer(new Consumer<String>() {
                    @Override
                    public void consumer(String s) {

                    }
                }, 3)
                .setEnd(new End() {
                    @Override
                    public void run() {
                        System.out.println(System.currentTimeMillis());
                    }
                })
                .run();
    }
}
