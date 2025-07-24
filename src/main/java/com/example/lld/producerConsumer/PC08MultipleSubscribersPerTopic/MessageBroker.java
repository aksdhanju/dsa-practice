package com.example.lld.producerConsumer.PC08MultipleSubscribersPerTopic;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Getter
public class MessageBroker {
    public static final int MAX_RETRIES = 3;
    public static final long RETENTION_MILLIS = 60_000; // 1 minute
    private final Map<String, List<BlockingQueue<Message>>> topicSubscribers = new ConcurrentHashMap<>();
    private final Map<String, BlockingQueue<Message>> dlqQueues = new ConcurrentHashMap<>();
    private final MessagePersistence persistence;
    private final ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();

    public MessageBroker(MessagePersistence persistence) {
        this.persistence = persistence;
        startCleanupTask();
    }

    private void startCleanupTask() {
        cleaner.scheduleAtFixedRate(() -> {
            long expiryTime = System.currentTimeMillis() - RETENTION_MILLIS;
            persistence.deleteExpiredMessages(expiryTime);
        }, 10, 10, TimeUnit.SECONDS);
    }

    public void createTopic(String topicName) {
        topicSubscribers.putIfAbsent(topicName, new CopyOnWriteArrayList<>());
        dlqQueues.putIfAbsent(topicName, new LinkedBlockingQueue<>());
    }

    public void publish(String topic, String content) throws InterruptedException {
        List<BlockingQueue<Message>> queues = topicSubscribers.get(topic);
        if (queues != null && !queues.isEmpty()) {
            Message msg = new Message(content);
            for (BlockingQueue<Message> q : queues) {
                q.put(msg);
            }
            persistence.saveMessage(topic, content, 0, "PENDING");
        } else {
            throw new IllegalArgumentException("No subscribers for topic: " + topic);
        }
    }

    public BlockingQueue<Message> subscribe(String topic) {
        //i think its a one time thing for a subscriber
        BlockingQueue<Message> subscriberQueue = new LinkedBlockingQueue<>();
        topicSubscribers.get(topic).add(subscriberQueue);
        return subscriberQueue;
    }

    public BlockingQueue<Message> getDlq(String topic) {
        return dlqQueues.get(topic);
    }

    public void acknowledge(String topic, Message message) {
        message.acknowledge();
        persistence.saveMessage(topic, message.getContent(), message.getRetryCount(), "PROCESSED");
    }
}
