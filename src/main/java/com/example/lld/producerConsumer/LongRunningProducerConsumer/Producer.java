package com.example.lld.producerConsumer.LongRunningProducerConsumer;

// Producer.java
public class Producer implements Runnable {
    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int value = 0;
        while (true) {
            buffer.produce(value++, null);
            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
