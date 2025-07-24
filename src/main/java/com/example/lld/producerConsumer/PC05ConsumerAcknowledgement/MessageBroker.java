package com.example.lld.producerConsumer.PC05ConsumerAcknowledgement;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageBroker {
    private final Map<String, BlockingQueue<Message>> topicQueues = new ConcurrentHashMap<>();

    public void createTopic(String topicName) {
        topicQueues.putIfAbsent(topicName, new LinkedBlockingQueue<>());
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
}
