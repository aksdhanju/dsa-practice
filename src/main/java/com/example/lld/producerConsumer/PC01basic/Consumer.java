package com.example.lld.producerConsumer.PC01basic;

public class Consumer {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void consume() {
        //        while(buffer.isEmpty()){
        //        }
        //        if (!buffer.isEmpty()) {
        //            buffer.remove();
        //        }
        buffer.remove();
    }
}
