package com.example.lld.producerConsumer.PC05ConsumerAcknowledgement;

public class Message {
    private final String content;
    private boolean acknowledged = false;

    public Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void acknowledge() {
        this.acknowledged = true;
    }
}
