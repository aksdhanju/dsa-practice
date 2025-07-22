package com.example.lld.producerConsumer.TopBasedProducerConsumer;

import java.util.concurrent.atomic.AtomicBoolean;

public class Producer implements Runnable {
    private final Buffer buffer;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private int value = 0;
    private final String name;

    public Producer(Buffer buffer, String name) {
        this.buffer = buffer;
        this.name = name;
    }

    public void stop() {
        running.set(false);
    }

    @Override
    public void run() {
        try {
            while (running.get()) {
                buffer.produce(value++, name);
                Thread.sleep(1000); // Simulate time to produce
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Producer interrupted");
        }
    }
}