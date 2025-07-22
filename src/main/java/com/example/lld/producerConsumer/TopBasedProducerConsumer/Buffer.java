package com.example.lld.producerConsumer.TopBasedProducerConsumer;

import lombok.Getter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
public class Buffer {
    private final BlockingQueue<Integer> queue;
    private final int capacity;

    public Buffer(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedBlockingQueue<>();
    }

    public synchronized void produce(int value, String name) {
        queue.offer(value);
        System.out.println(name + ": produced " + value);
        //        while (queue.size() == capacity) {
        //            try {
        //                System.out.println("Buffer full. Producer waiting...");
        //                wait();
        //            } catch (InterruptedException e) {
        //                Thread.currentThread().interrupt();
        //            }
        //        }
        //
        //        queue.offer(value);
        //        System.out.println(name + ": produced " + value);
        //        notifyAll(); // Notify consumer
    }

    public synchronized int consume(String name) {
        int value = queue.poll();
        System.out.println(name + ": consumed " + value);
        return value;
        //        while (queue.isEmpty()) {
        //            try {
        //                System.out.println("Buffer empty. Consumer waiting...");
        //                wait();
        //            } catch (InterruptedException e) {
        //                Thread.currentThread().interrupt();
        //            }
        //        }
        //
        //        int value = queue.poll();
        //        System.out.println(name + ": consumed " + value);
        //        notifyAll(); // Notify producer
        //        return value;
    }
}

