package com.example.lld.producerConsumer.PC08MultipleSubscribersPerTopic;

public interface MessagePersistence {
    void saveMessage(String topic, String content, int retryCount, String status);
    void deleteExpiredMessages(long expirationThreshold);
    void close() throws Exception;

    // Optional utility for debugging
    default void printAllMessages() {}
}