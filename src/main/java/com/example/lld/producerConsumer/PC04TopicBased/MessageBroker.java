package com.example.lld.producerConsumer.PC04TopicBased;

import java.util.concurrent.*;
import java.util.*;

public class MessageBroker {
    private final Map<String, BlockingQueue<String>> topicQueues;

    public MessageBroker(){
        topicQueues = new ConcurrentHashMap<>();
    }

    public void createTopic(String topicName) {
        topicQueues.putIfAbsent(topicName, new LinkedBlockingQueue<>());
    }

    public void publish(String topic, String message) throws InterruptedException {
        BlockingQueue<String> queue = topicQueues.get(topic);
        if (queue != null) {
            queue.put(message); // blocking if full
        } else {
            throw new IllegalArgumentException("Topic doesn't exist: " + topic);
        }
    }

    public BlockingQueue<String> getQueue(String topic) {
        return topicQueues.get(topic);
    }
}
