package com.example.lld.producerConsumer.LongRunningProducerConsumer;

public class Main2 {

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer(3);
        Producer2 producer = new Producer2(buffer, "Producer-1");
        Consumer2 consumer = new Consumer2(buffer, "Consumer-1");

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        Thread.sleep(10000); // Let it run for 10 seconds

        producer.stop();
        consumer.stop();

        producerThread.interrupt();
        consumerThread.interrupt();

        producerThread.join();
        consumerThread.join();

        System.out.println("Main thread exiting");
    }
}
