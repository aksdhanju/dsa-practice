package com.example.lld.producerConsumer.PC08MultipleSubscribersPerTopic;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class Subscriber implements Runnable {
    private final MessageBroker broker;
    private final String topic;
    private final int consumerId;
    private volatile boolean running = true;
    private final Consumer<Message> handler;  // Injected processing logic

    public Subscriber(MessageBroker broker, String topic, int consumerId, Consumer<Message> handler) {
        this.broker = broker;
        this.topic = topic;
        this.consumerId = consumerId;
        this.handler = handler;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        BlockingQueue<Message> queue = broker.subscribe(topic);
        if (queue == null) return;

        while (running) {
            try {
                Message message = queue.take(); // blocking
                long now = System.currentTimeMillis();

                if (now - message.getTimestamp() > MessageBroker.RETENTION_MILLIS) {
                    System.out.println("Message expired (skipped): " + message.getContent());
                    continue;
                }

                System.out.println("Consumer " + consumerId + " on " + topic + " got: " + message.getContent());

                try {
                    handler.accept(message); // your processing logic

                    // Simulate random failure
                    boolean success = Math.random() > 0.3;

                    if (success) {
                        // Acknowledge after successful processing
                        broker.acknowledge(topic, message);
                        System.out.println("Consumer " + consumerId + " processed & ACKed: " + message.getContent());
                    } else {
                        // Simulate failure (e.g. crash) => message not acknowledged
                        System.out.println("Consumer " + consumerId + " failed: " + message.getContent());
                    }
                } catch (Exception e) {
                    System.out.println("Exception in handler: " + e.getMessage());
                    // fall through to retry logic
                }

                // If not acknowledged, retry or DLQ
                if (!message.isAcknowledged()) {
                    handleFailedMessage(queue, topic, message);
                }

                // simulate processing
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    //failed message handling logic should be in subscriber and not in broker
    private void handleFailedMessage(BlockingQueue<Message> queue, String topic, Message message) throws InterruptedException {
        message.incrementRetry();
        if (message.getRetryCount() >= MessageBroker.MAX_RETRIES) {
            broker.getDlq(topic).put(message);
            broker.getPersistence().saveMessage(topic, message.getContent(), message.getRetryCount(), "DLQ");
            System.out.println("DLQ for consumer " + consumerId + ": " + message.getContent());
        } else {
            queue.put(message); // Re-enqueue to current subscriber's queue
            broker.getPersistence().saveMessage(topic, message.getContent(), message.getRetryCount(), "RETRYING");
            System.out.println("Retrying for consumer " + consumerId + ": " + message.getContent());
        }
    }
}
