package com.example.lld.producerConsumer.PC01basic;

import lombok.Getter;

import java.util.LinkedList;
import java.util.Queue;

@Getter
public class BufferQueue {
    private Queue<Integer> queue;
    private int capacity;

    public BufferQueue(int capacity){
        queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public void produce(int value) {
        if (queue.size() == capacity) {
            System.out.println("Buffer full. Cannot produce " + value);
            return;
        }
        queue.offer(value);
        System.out.println("Produced: " + value);
    }

    public void consume() {
        if (queue.isEmpty()) {
            System.out.println("Buffer empty. Nothing to consume.");
            return;
        }
        int value = queue.poll();
        System.out.println("Consumed: " + value);
    }
}
