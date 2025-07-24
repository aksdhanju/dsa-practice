package com.example.lld.producerConsumer.PC06Retry_Dlq_Handling;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MessageBroker broker = new MessageBroker();
        broker.createTopic("orders");
        broker.createTopic("notifications");

        ExecutorService executor = Executors.newCachedThreadPool();

        // Producers
        Producer orderProducer = new Producer(broker, "orders", 1);
        Producer notifProducer = new Producer(broker, "notifications", 2);

        // Consumers
        Consumer orderConsumer = new Consumer(broker, "orders", 1);
        Consumer notifConsumer = new Consumer(broker, "notifications", 2);

        //as per current implementation, single producer can produce onto a single topic
        //and single consumer can read from a single topic.

        executor.submit(orderProducer);
        executor.submit(notifProducer);
        executor.submit(orderConsumer);
        executor.submit(notifConsumer);

        Thread.sleep(10000); // Run system for 10 sec

        orderProducer.stop();
        notifProducer.stop();
        orderConsumer.stop();
        notifConsumer.stop();

        executor.shutdownNow();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("System shutdown.");

        BlockingQueue<Message> orderDlq = broker.getDlq("orders");
        System.out.println("DLQ Messages for 'orders':");
        orderDlq.forEach(msg -> System.out.println(msg.getContent()));

    }
}
