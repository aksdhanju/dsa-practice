package com.example.lld.producerConsumer.PC06Retry_Dlq_Handling;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageBroker {
    private static final int MAX_RETRIES = 3;
    private final Map<String, BlockingQueue<Message>> topicQueues = new ConcurrentHashMap<>();
    private final Map<String, BlockingQueue<Message>> dlqQueues = new ConcurrentHashMap<>();


    public void createTopic(String topicName) {
        topicQueues.putIfAbsent(topicName, new LinkedBlockingQueue<>());
        dlqQueues.putIfAbsent(topicName, new LinkedBlockingQueue<>());
    }

    public void publish(String topic, String content) throws InterruptedException {
        BlockingQueue<Message> queue = topicQueues.get(topic);
        if (queue != null) {
            queue.put(new Message(content));
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
            System.out.println("Moving to DLQ: " + message.getContent());
            dlqQueues.get(topic).put(message);
        } else {
            System.out.println("Retrying message: " + message.getContent());
            topicQueues.get(topic).put(message); // Re-enqueue for retry
        }
    }

    public BlockingQueue<Message> getDlq(String topic) {
        return dlqQueues.get(topic);
    }
}
