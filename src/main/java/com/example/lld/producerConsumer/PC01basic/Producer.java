package com.example.lld.producerConsumer.PC01basic;

public class Producer {
    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void produce(int value) {
        //        while(buffer.isFull()){
        //        }

        //        if (!buffer.isFull()) {
        //            buffer.add(value);
        //        }
        buffer.add(value);
    }
}