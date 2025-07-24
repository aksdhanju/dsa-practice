package com.example.lld.producerConsumer.PC03LongRunningThread;

import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer2 implements Runnable {
    private final Buffer buffer;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final String name;

    public Consumer2(Buffer buffer, String name) {
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
                buffer.consume(name);
                Thread.sleep(1500); // Simulate time to consume
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Consumer interrupted");
        }
    }
}