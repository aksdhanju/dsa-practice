package com.example.lld.producerConsumer.OneThreadPerCallProducerConsumer;

public class Consumer {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void consume() {
        new Thread(() -> buffer.consume()).start(); // Spawn a new thread per consume call
    }
}
