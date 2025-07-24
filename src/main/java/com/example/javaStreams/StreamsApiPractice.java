package com.example.javaStreams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamsApiPractice {
    public static void main(String[] args){
        List<Integer> list = Arrays.asList(1,2,3);
        Stream stream = list.stream();
    }
}
