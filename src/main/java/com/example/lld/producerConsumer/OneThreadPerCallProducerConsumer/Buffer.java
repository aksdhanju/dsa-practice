package com.example.lld.producerConsumer.OneThreadPerCallProducerConsumer;

import lombok.Getter;
import java.util.LinkedList;
import java.util.Queue;

@Getter
public class Buffer {
    private final Queue<Integer> queue;
    private final int capacity;

    public Buffer(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    public synchronized void produce(int value) {
        while (queue.size() == capacity) {
            try {
                System.out.println("Buffer full. Producer waiting...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        queue.offer(value);
        System.out.println("Produced: " + value);
        notifyAll(); // Notify consumer
    }

    public synchronized int consume() {
        while (queue.isEmpty()) {
            try {
                System.out.println("Buffer empty. Consumer waiting...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        int value = queue.poll();
        System.out.println("Consumed: " + value);
        notifyAll(); // Notify producer
        return value;
    }
}

