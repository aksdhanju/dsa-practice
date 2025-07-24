package com.example.lld.producerConsumer.PC03LongRunningThread;

// Main.java
public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(3);

        Thread producerThread = new Thread(new Producer(buffer));
        Thread consumerThread = new Thread(new Consumer(buffer));

        producerThread.start();
        consumerThread.start();
    }
}
