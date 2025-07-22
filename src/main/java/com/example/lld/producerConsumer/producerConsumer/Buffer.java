package com.example.lld.producerConsumer.producerConsumer;

import lombok.Getter;

@Getter
public class Buffer {
    private int[] buffer;
    private int count;

    public Buffer(int size){
        buffer = new int[size];
        count = 0;
    }
    public boolean isFull() {
        return count == buffer.length;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void add(int value) {
        if (!isFull()) {
            buffer[count++] = value;
            System.out.println("Produced: " + value);
        } else {
            System.out.println("Buffer is full. Cannot produce.");
        }
    }

    public int remove(){
        if (!isEmpty()) {
            int value = buffer[--count];
            //can be done
            buffer[count+1] = 0;
            System.out.println("Consumed: " + value);
            return value;
        } else {
            System.out.println("Buffer is empty. Cannot consume.");
            return -1;
        }
    }
}
