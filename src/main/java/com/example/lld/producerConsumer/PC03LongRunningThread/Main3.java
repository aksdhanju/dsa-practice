package com.example.lld.producerConsumer.PC03LongRunningThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main3 {
    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer(3);
        Producer2 producer = new Producer2(buffer, "Producer-1");
        Consumer2 consumer1 = new Consumer2(buffer, "Consumer-1");;
        Consumer2 consumer2 = new Consumer2(buffer, "Consumer-2");;
        Consumer2 consumer3 = new Consumer2(buffer, "Consumer-3");;

        // Create a thread pool with 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit producer and consumer to executor
        executor.submit(producer);
        executor.submit(consumer1);
        executor.submit(consumer2);
        executor.submit(consumer3);

        // Let it run for 10 seconds
        Thread.sleep(10000);

        // Graceful shutdown
        producer.stop();
        consumer1.stop();
        consumer2.stop();
        consumer3.stop();

        // Stop accepting new tasks and try to shutdown cleanly
        executor.shutdown();

        // Wait for tasks to complete or timeout after 5 seconds
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            executor.shutdownNow(); // Force shutdown if not terminated
        }

        System.out.println("Main thread exiting");
    }
}
