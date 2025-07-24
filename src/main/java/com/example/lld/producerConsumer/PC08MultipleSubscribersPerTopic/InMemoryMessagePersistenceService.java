package com.example.lld.producerConsumer.PC08MultipleSubscribersPerTopic;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMessagePersistenceService implements MessagePersistence {

    private final Map<String, List<String>> store = new ConcurrentHashMap<>();

    @Override
    public void saveMessage(String topic, String content, int retryCount, String status) {
        store.computeIfAbsent(topic, k -> new ArrayList<>())
                .add("Content: " + content + ", Retry: " + retryCount + ", Status: " + status);
    }

    @Override
    public void deleteExpiredMessages(long expirationThreshold) {
        // No-op for in-memory store
    }

    @Override
    public void close() {
        store.clear();
    }

    @Override
    public void printAllMessages() {
        store.forEach((topic, messages) -> {
            System.out.println("Topic: " + topic);
            messages.forEach(System.out::println);
        });
    }
}
