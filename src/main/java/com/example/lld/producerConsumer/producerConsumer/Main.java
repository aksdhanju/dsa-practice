package com.example.lld.producerConsumer.producerConsumer;

public class Main {

    public static void main(String[] args){
        Buffer buffer = new Buffer(5); // Small buffer for testing
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        // Simulate some produce/consume operations
        producer.produce(10);
        producer.produce(20);
        consumer.consume();
        producer.produce(30);
        consumer.consume();
        consumer.consume();
        consumer.consume(); // This will print: Buffer is empty
        producer.produce(40);
        producer.produce(50);
        producer.produce(60);
        producer.produce(70);
        producer.produce(80);
        producer.produce(90); // This will print: Buffer is full
    }
}
