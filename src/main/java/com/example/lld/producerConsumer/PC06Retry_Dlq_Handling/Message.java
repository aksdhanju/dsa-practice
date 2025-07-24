package com.example.lld.producerConsumer.PC06Retry_Dlq_Handling;

import lombok.Getter;

@Getter
public class Message {
    private final String content;
    private boolean acknowledged = false;
    private int retryCount = 0;

    public Message(String content) {
        this.content = content;
    }

    public void acknowledge() {
        this.acknowledged = true;
    }

    public void incrementRetry() {
        retryCount++;
    }
}
