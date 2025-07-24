package com.example.lld.producerConsumer.PC08MultipleSubscribersPerTopic;

import lombok.Getter;

@Getter
public class Message {
    private final String content;
    private final long timestamp;
    private boolean acknowledged = false;
    private int retryCount = 0;

    public Message(String content) {
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public void acknowledge() {
        this.acknowledged = true;
    }

    public void incrementRetry() {
        retryCount++;
    }
}
