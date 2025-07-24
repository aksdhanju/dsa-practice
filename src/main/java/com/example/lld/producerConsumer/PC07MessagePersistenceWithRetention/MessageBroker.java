package com.example.lld.producerConsumer.PC07MessagePersistenceWithRetention;

import java.util.Map;
import java.util.concurrent.*;

public class MessageBroker {
    private static final int MAX_RETRIES = 3;
    private static final long RETENTION_MILLIS = 60_000; // 1 minute
    private final Map<String, BlockingQueue<Message>> topicQueues = new ConcurrentHashMap<>();
    private final Map<String, BlockingQueue<Message>> dlqQueues = new ConcurrentHashMap<>();
    private final MessagePersistenceService persistence;
    private final ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();

    public MessageBroker(MessagePersistenceService persistence) {
        this.persistence = persistence;
        startCleanupTask();
    }

    private void startCleanupTask() {
        cleaner.scheduleAtFixedRate(() -> {
            long expiryTime = System.currentTimeMillis() - RETENTION_MILLIS;
            persistence.deleteExpiredMessages(expiryTime);
        }, 10, 10, TimeUnit.SECONDS);
    }

    public void createTopic(String topicName) {
        topicQueues.putIfAbsent(topicName, new LinkedBlockingQueue<>());
        dlqQueues.putIfAbsent(topicName, new LinkedBlockingQueue<>());
    }

    public void publish(String topic, String content) throws InterruptedException {
        BlockingQueue<Message> queue = topicQueues.get(topic);
        if (queue != null) {
            queue.put(new Message(content));
            persistence.saveMessage(topic, content, 0, "PENDING");
        } else {
            throw new IllegalArgumentException("Topic doesn't exist: " + topic);
        }
    }

    public BlockingQueue<Message> getQueue(String topic) {
        return topicQueues.get(topic);
    }

    public void handleFailedMessage(String topic, Message message) throws InterruptedException {
        message.incrementRetry();
        if (message.getRetryCount() >= MAX_RETRIES) {
            dlqQueues.get(topic).put(message);
            persistence.saveMessage(topic, message.getContent(), message.getRetryCount(), "DLQ");
            System.out.println("Moving to DLQ: " + message.getContent());
        } else {
            topicQueues.get(topic).put(message); // Re-enqueue for retry
            persistence.saveMessage(topic, message.getContent(), message.getRetryCount(), "RETRYING");
            System.out.println("Retrying message: " + message.getContent());
        }
    }

    public BlockingQueue<Message> getDlq(String topic) {
        return dlqQueues.get(topic);
    }

    public void acknowledge(String topic, Message message) {
        message.acknowledge();
        persistence.saveMessage(topic, message.getContent(), message.getRetryCount(), "PROCESSED");
    }
}
