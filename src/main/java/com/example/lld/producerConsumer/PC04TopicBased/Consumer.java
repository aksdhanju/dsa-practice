package com.example.lld.producerConsumer.PC04TopicBased;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final MessageBroker broker;
    private final String topic;
    private final int consumerId;
    private volatile boolean running = true;

    public Consumer(MessageBroker broker, String topic, int consumerId) {
        this.broker = broker;
        this.topic = topic;
        this.consumerId = consumerId;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        BlockingQueue<String> queue = broker.getQueue(topic);
        if (queue == null) return;

        while (running) {
            try {
                String message = queue.take(); // blocking
                System.out.println("Consumer " + consumerId + " on " + topic + " got: " + message);
                Thread.sleep(1000); // simulate processing
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}