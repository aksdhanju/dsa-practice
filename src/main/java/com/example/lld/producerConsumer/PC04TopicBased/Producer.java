package com.example.lld.producerConsumer.PC04TopicBased;

public class Producer implements Runnable {
    private final MessageBroker broker;
    private final String topic;
    private final int producerId; //private final String name;
    private volatile boolean running = true;
    //private int value = 0; this value is now count in run method. why?

    public Producer(MessageBroker broker, String topic, int producerId) {
        this.broker = broker;
        this.topic = topic;
        this.producerId = producerId;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        int count = 0;
        while (running) {
            String message = "P" + producerId + " Message " + count;
            try {
                broker.publish(topic, message);
                System.out.println("Produced to " + topic + ": " + message);
                Thread.sleep(500); // simulate work
                count++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}