package com.example.lld.producerConsumer.PC07MessagePersistenceWithRetention;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException {
        MessagePersistenceService persistence = new MessagePersistenceService();
        MessageBroker broker = new MessageBroker(persistence);

        broker.createTopic("orders");

        Thread producer = new Thread(new Producer(broker, "orders", 1));
        Thread consumer = new Thread(new Consumer(broker, "orders", 1));

        producer.start();
        consumer.start();

        Thread.sleep(15000); // run for 15s

        producer.interrupt();
        consumer.interrupt();

        Thread.sleep(1000);

        System.out.println("\n--- DLQ Messages ---");
        broker.getDlq("orders").forEach(msg -> System.out.println(msg.getContent()));

        persistence.close();

    }
}
