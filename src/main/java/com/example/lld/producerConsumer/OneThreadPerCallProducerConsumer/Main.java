package com.example.lld.producerConsumer.OneThreadPerCallProducerConsumer;

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(3); // queue capacity
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        // Test case
        producer.produce(10);
        producer.produce(20);
        consumer.consume();
        producer.produce(30);
        producer.produce(40); // may wait if buffer full
        consumer.consume();
        consumer.consume();
        consumer.consume(); // may wait if buffer empty
    }
}
