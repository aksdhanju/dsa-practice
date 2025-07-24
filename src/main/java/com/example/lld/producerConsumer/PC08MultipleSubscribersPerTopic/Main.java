package com.example.lld.producerConsumer.PC08MultipleSubscribersPerTopic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean useInMemory = false; // Toggle this flag to switch implementation

        MessagePersistence persistence;
        if (useInMemory) {
            persistence = new InMemoryMessagePersistenceService();
        } else {
            persistence = new JdbcMessagePersistenceService(); // JDBC-based
        }
        MessageBroker broker = new MessageBroker(persistence);

        broker.createTopic("orders");

        // Message handler logic
        Consumer<Message> handler = msg -> {
            System.out.println("Handler: Saving message to DB - " + msg.getContent());
            // You could add custom logic here like saving to DB, etc.
        };

        Thread producer = new Thread(new Producer(broker, "orders", 1));
        Thread subscriber1 = new Thread(new Subscriber(broker, "orders", 1, handler));
        Thread subscriber2 = new Thread(new Subscriber(broker, "orders", 2, handler));

        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.submit(producer);
        executor.submit(subscriber1);
        executor.submit(subscriber2);

        Thread.sleep(15000); // run for 15s

//        producer.interrupt();
        subscriber1.interrupt();
        subscriber2.interrupt();

        Thread.sleep(1000);

        System.out.println("\n--- DLQ Messages ---");
        broker.getDlq("orders").forEach(msg -> System.out.println(msg.getContent()));

        System.out.println("\n--- All persisted messages ---");
        persistence.printAllMessages();

        persistence.close();
    }
}
