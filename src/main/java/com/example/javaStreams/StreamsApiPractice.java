package com.example.javaStreams;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsApiPractice {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        Stream stream = list.stream();
        List<Integer> evenNumbers = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        evenNumbers.forEach(x -> System.out.println(x));


        //Predicate inside intermediate and terminal operations
        //Here are the main intermediate stream operations that accept a Predicate:
        //1.filter
        Stream<Integer> oddNumsStream = list.stream().filter(x -> x % 2 == 1);
        long oddNumsCount = oddNumsStream.count();
        System.out.println("odd numbers are " + oddNumsCount);
        //Returns the count of elements in this stream. This is a special case of a reduction and is equivalent to:
        //return mapToLong(e -> 1L).sum();
        //List<Integer> oddNumsList = oddNumsStream.collect(Collectors.toList());
        //2.dropWhile
        //3.takeWhile

        //termninal operation
        //1. anyMatch
        boolean isEven = Stream.of(1, 2, 3, 4, 5, 6)
                .anyMatch(x -> x % 2 == 0);
        System.out.println(isEven);

        //2.allMatch
        boolean allPositive = list.stream().allMatch(x -> x > 0);
        System.out.println(allPositive);

        //3.noneMatch
        boolean noSevenMultiple = list.stream().noneMatch(x -> x % 7 == 0);
        System.out.println(noSevenMultiple);

        //4. findFirst + filter(Predicate)
        //Often used with a predicate (in filter) to find the first matching element.
        List<String> names = Arrays.asList("Avtar", "Kiran", "Akash", "Sehaj", "Shefie", "Chandan");
        Optional<String> nameOptional = names.stream().filter(x -> x.startsWith("S"))
                .findFirst();
        nameOptional.ifPresent(x -> System.out.println(x)); //Note forEach and ifPresent take
        // Consumer as argument


        //5. findAny() + filter(Predicate)
        Optional<String> aNameOptional = names.stream().filter(x -> x.startsWith("A"))
                .findAny();
        aNameOptional.ifPresent(x -> System.out.println(x));

        //6. removeIf() Not a stream method, but uses Predicate)
        List<Integer> nums = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7));
        nums.removeIf(n -> n % 2 == 0);  // Remove even numbers
        System.out.println(nums);       // Output: [1, 3, 5]

        //Test problem
        for (int num : nums) {
            if (num >= 3) {
                System.out.println(num);
                num = -1 * num;
                System.out.println(num);
            }
        }
        nums.stream().forEach(System.out::print);
        nums.stream()
                .filter(num -> num >= 3)
                .forEach(num -> {
                    System.out.println(num);
                    num = -1*num;
                    System.out.println(num);
                });


        //Consumer
        //Great question! In Java Streams, Consumer<T> is a functional interface used
        //in operations that accept a value
        // and perform some side effect (like printing) without returning a result.
        //Intermediate operation
        //1.peek()
        names.stream().peek(x -> System.out.println(x)).map(x-> x.toUpperCase())
                .forEach(x -> System.out.println(x));
        //Terminal operation
        //1. forEach(Consumer<? super T> action)
        //Performs the given action for each element in the stream (terminal operation)

        //2. 2. forEachOrdered(Consumer<? super T> action)
        //Like forEach, but preserves encounter order, useful for parallel streams.
        nums.stream().parallel().forEach(x -> System.out.println(x));


        //Comparator inside
        //Intermediate Operations using Comparator
        //1. sorted
        names.stream().sorted().forEach(x -> System.out.println(x));
        System.out.println();
        //lambda function
        names.stream().sorted((a,b) -> a.compareTo(b)).forEach(x -> System.out.print(x));
        System.out.println();
        //method reference
        names.stream().sorted(String::compareTo).forEach(x -> System.out.print(x));
        System.out.println();
        names.stream().sorted(Comparator.naturalOrder()).forEach(x -> System.out.print(x));
        System.out.println();
        names.stream().sorted(Comparator.reverseOrder()).forEach(x -> System.out.print(x));
        System.out.println();

        // Terminal Operations using Comparator
        //1. min(Comparator<T> comparator)
        List<Integer> primeNumbers = List.of(2,3,5,7,11,13,17,19);

        Optional<Integer> min = primeNumbers.stream()
                .min((a, b) -> a - b); // or Comparator.naturalOrder()

        min.ifPresent(System.out::println); // 1


        //2. max(Comparator<T> comparator)
        Optional<Integer> max =  primeNumbers.stream().max((a,b) -> a-b);

        max.ifPresent(System.out::println);  //19
        //Function inside


    }
}
