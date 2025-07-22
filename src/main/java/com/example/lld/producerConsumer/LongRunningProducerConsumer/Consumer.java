package com.example.lld.producerConsumer.LongRunningProducerConsumer;

// Consumer.java
public class Consumer implements Runnable {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            buffer.consume(null);
            try {
                Thread.sleep(150); // simulate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}