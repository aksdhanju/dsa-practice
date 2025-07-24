package com.example.lld.producerConsumer.PC02OneThreadPerCall;

public class Consumer {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void consume() {
        new Thread(() -> buffer.consume()).start(); // Spawn a new thread per consume call
    }
}
