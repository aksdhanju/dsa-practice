package com.example.lld.producerConsumer.PC06Retry_Dlq_Handling;

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
        BlockingQueue<Message> queue = broker.getQueue(topic);
        if (queue == null) return;

        while (running) {
            try {
                Message message = queue.take(); // blocking
                System.out.println("Consumer " + consumerId + " on " + topic + " got: " + message.getContent());

                // Simulate random failure
                boolean success = Math.random() > 0.3;

                if (success) {
                    // Acknowledge after successful processing
                    message.acknowledge();
                    System.out.println("Consumer " + consumerId + " processed & ACKed: " + message.getContent());
                } else {
                    // Simulate failure (e.g. crash) => message not acknowledged
                    System.out.println("Consumer " + consumerId + " failed: " + message.getContent());
                }

                // If not acknowledged, retry or DLQ
                if (!message.isAcknowledged()) {
                    broker.handleFailedMessage(topic, message);
                }

                // simulate processing
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
