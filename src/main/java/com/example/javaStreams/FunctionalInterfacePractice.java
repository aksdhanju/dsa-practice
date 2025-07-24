package com.example.javaStreams;

import java.util.Comparator;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfacePractice {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //common functional interfaces:
        //1.Runnable
        Runnable runnable1 = () -> {
            System.out.println(Thread.currentThread().getName() + " is getting executed");
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is getting executed");
            }
        };
        Thread thread1 = new Thread(runnable1);
        thread1.start();

        Thread thread2 = new Thread(runnable2);
        thread2.start();

        //2.Callable
        Callable<String> task1 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Result from Callable 1";
            }
        };
        Callable<String> task2 = () -> "Result from Callable 2";
        //Use case: Executor Service
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutorService executor2 =  Executors.newFixedThreadPool(4);

        Future<String> future = executor.submit(task1);
        String result = future.get(); //blocking call
        executor.shutdown();

        //3: Comparator
        Comparator<Integer> comparator1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return Integer.compare(a, b);
            }
        };
        Comparator<Integer> comparator2 = (a, b) -> a - b;
        Comparator<Integer> comparator3 = (a, b) -> Integer.compare(a, b);
        Comparator<Integer> comparator4 = Integer::compare;

        //4.Function
        //Integer is input, String is output
        Function<Integer, String> conversionFunction1 = new Function<Integer, String>() {
            @Override
            public String apply(Integer s) {
                return s.toString();
            }
        };
        Function<Integer, String> conversionFunction2 = (s) -> s.toString();
        Function<Integer, String> conversionFunction3 = Object::toString;

        //5.Consumer
        //anonymous class
        Consumer<String> consumer1 = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        //lambda expression
        Consumer<String> consumer2 = (s) -> System.out.println(s);
        //method reference
        Consumer<String> consumer3 = System.out::println;

        //6.Predicate
        Predicate<Integer> isEvenPredicate1 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer num) {
                if (num % 2 == 0) {
                    return true;
                }
                return false;
            }
        };

        Predicate<Integer> isEvenPredicate2 = (num) -> {
            if (num % 2 == 0) {
                return true;
            }
            return false;
        };
        Predicate<Integer> isEvenPredicate3 = (num) -> num % 2 == 0;
        System.out.println(isEvenPredicate3.test(5));

        //7.Supplier
        Supplier<String> supplier1 = new Supplier<String>() {
            @Override
            public String get() {
                return "Sample output data";
            }
        };

        Supplier<String> supplier2 = () -> "Sample output data";

        Square s = (x) -> x+3;
        int res = s.calculate(5);
        System.out.println(res);

    }
}
@FunctionalInterface
interface Square{
    int calculate(int a);
}