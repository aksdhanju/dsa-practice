package com.example.lld.producerConsumer.OneThreadPerCallProducerConsumer;

public class Producer {
    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void produce(int value) {
        new Thread(() -> buffer.produce(value)).start(); // Spawn a new thread per produce call
    }
}
