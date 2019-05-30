package com.callenled.pattern.prodcons;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Callenld
 * @Date: 19-5-29
 */
public class ProdConsModel {

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static <T> Builder<T> builder(int capacity) {
        return new Builder<>(capacity);
    }

    public static <T, R> Builders<T, R> builders() {
        return new Builders<>();
    }

    public static <T, R> Builders<T, R> builders(int capacity) {
        return new Builders<>(capacity);
    }

    public static class Builder<T> {
        /**
         * 队列容量
         */
        private int capacity;

        /**
         * 生产者
         */
        private List<Producer<T>> producer;

        /**
         * 消费者
         */
        private List<Consumer<T>> consumer;

        /**
         * 开启
         */
        private Start start;

        /**
         * 结束
         */
        private End end;

        private Builder() {
            this.capacity = 50;
        }

        private Builder(int capacity) {
            this.capacity = capacity;
        }

        /**
         * 设置生产线
         *
         * @param producer
         * @return
         */
        public Builder<T> setProducer(Producer<T> producer) {
            return this.setProducer(producer, 1);
        }

        /**
         * 设置生产线
         *
         * @param producer
         * @param threads
         * @return
         */
        public Builder<T> setProducer(Producer<T> producer, int threads) {
            if (this.producer == null) {
                this.producer = new ArrayList<>();
            }
            for (int i = 0; i < threads; i++) {
                this.producer.add(producer);
            }
            return this;
        }

        /**
         * 设置消费线
         *
         * @param consumer
         * @return
         */
        public Builder<T> setConsumer(Consumer<T> consumer) {
            return this.setConsumer(consumer, 1);
        }

        /**
         * 设置消费线
         *
         * @param consumer
         * @param threads
         * @return
         */
        public Builder<T> setConsumer(Consumer<T> consumer, int threads) {
            if (this.consumer == null) {
                this.consumer = new ArrayList<>();
            }
            for (int i = 0; i < threads; i++) {
                this.consumer.add(consumer);
            }
            return this;
        }

        /**
         * 设置开启
         *
         * @param start
         * @return
         */
        public Builder<T> setStart(Start start) {
            this.start = start;
            return this;
        }


        /**
         * 设置结束
         *
         * @param end
         * @return
         */
        public Builder<T> setEnd(End end) {
            this.end = end;
            return this;
        }

        /**
         * 运行
         */
        public void run() {
            if (this.start != null) {
                this.start.run();
            }
            //线程池
            ExecutorService service = Executors.newCachedThreadPool();
            //队列
            Storage<T> storage = new Storage<>(capacity);
            //设置生产者线程数量
            storage.setThreads(this.producer.size());
            //生产者
            for (Producer<T> producer : this.producer) {
                producer.setStorage(storage);
                service.execute(producer);
            }
            //消费者
            if (this.end != null) {
                this.end.setConsumers(this.consumer.size());
            }
            for (Consumer<T> consumer : this.consumer) {
                consumer.setStorage(storage);
                consumer.setEnd(this.end);
                service.execute(consumer);
            }
            service.shutdown();
        }
    }

    public static class Builders<T, R> {

        /**
         * 队列容量
         */
        private int capacity;

        /**
         * 生产者
         */
        private List<Producer<T>> producer;

        /**
         * 齿轮(消费者-生产者)
         */
        private List<Gear<T, R>> gear;

        /**
         * 消费者
         */
        private List<Consumer<R>> consumer;

        /**
         * 开启
         */
        private Start start;

        /**
         * 结束
         */
        private End end;

        private Builders() {
            this.capacity = 50;
        }

        private Builders(int capacity) {
            this.capacity = capacity;
        }

        /**
         * 设置生产线
         *
         * @param producer
         * @return
         */
        public Builders<T, R> setProducer(Producer<T> producer) {
            return this.setProducer(producer, 1);
        }

        /**
         * 设置生产线
         *
         * @param producer
         * @param threads
         * @return
         */
        public Builders<T, R> setProducer(Producer<T> producer, int threads) {
            if (this.producer == null) {
                this.producer = new ArrayList<>();
            }
            for (int i = 0; i < threads; i++) {
                this.producer.add(producer);
            }
            return this;
        }

        /**
         * 设置消费线
         *
         * @param consumer
         * @return
         */
        public Builders<T, R> setConsumer(Consumer<R> consumer) {
            return this.setConsumer(consumer, 1);
        }

        /**
         * 设置消费线
         *
         * @param consumer
         * @param threads
         * @return
         */
        public Builders<T, R> setConsumer(Consumer<R> consumer, int threads) {
            if (this.consumer == null) {
                this.consumer = new ArrayList<>();
            }
            for (int i = 0; i < threads; i++) {
                this.consumer.add(consumer);
            }
            return this;
        }

        /**
         * 设置中间线
         *
         * @param gear
         * @return
         */
        public Builders<T, R> setGear(Gear<T, R> gear) {
            return this.setGear(gear, 1);
        }

        /**
         * 设置中间线
         *
         * @param gear
         * @param threads
         * @return
         */
        public Builders<T, R> setGear(Gear<T, R> gear, int threads) {
            if (this.gear == null) {
                this.gear = new ArrayList<>();
            }
            for (int i = 0; i < threads; i++) {
                this.gear.add(gear);
            }
            return this;
        }

        /**
         * 设置开启
         *
         * @param start
         * @return
         */
        public Builders<T, R> setStart(Start start) {
            this.start = start;
            return this;
        }


        /**
         * 设置结束
         *
         * @param end
         * @return
         */
        public Builders<T, R> setEnd(End end) {
            this.end = end;
            return this;
        }

        /**
         * 运行
         */
        public void run() {
            if (this.start != null) {
                this.start.run();
            }
            //线程池
            ExecutorService service = Executors.newCachedThreadPool();
            //队列
            Storage<T> pStorage = new Storage<>(capacity);
            //设置生产者线程数量
            pStorage.setThreads(this.producer.size());
            //生产者
            for (Producer<T> producer : this.producer) {
                producer.setStorage(pStorage);
                service.execute(producer);
            }
            Storage<R> cStorage = new Storage<>(capacity);
            //设置中间件线程数量
            cStorage.setThreads(this.gear.size());
            //消费者 - 生产者
            for (Gear<T, R> gear : this.gear) {
                gear.setProducer(pStorage);
                gear.setConsumer(cStorage);
                service.execute(gear);
            }
            //消费者
            if (this.end != null) {
                this.end.setConsumers(this.consumer.size());
            }
            for (Consumer<R> consumer : this.consumer) {
                consumer.setStorage(cStorage);
                consumer.setEnd(this.end);
                service.execute(consumer);
            }
            service.shutdown();
        }
    }
}
