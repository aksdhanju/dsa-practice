package com.example.lld.producerConsumer.PC08MultipleSubscribersPerTopic;

import lombok.Getter;

@Getter
public class MessageEntity {
    private final long id;
    private final String topic;
    private final String value;
    private final int retryCount;
    private final String status; // PENDING, PROCESSED, DLQ
    private final long timestamp;

    public MessageEntity(long id, String topic, String value, int retryCount, String status, long timestamp) {
        this.id = id;
        this.topic = topic;
        this.value = value;
        this.retryCount = retryCount;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getters only
}
