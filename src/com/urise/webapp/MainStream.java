package com.urise.webapp;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStream {
    static final int[] VALUES = {3, 2, 5, 5, 8};
    static final List<Integer> integers = Arrays.asList(1, 2, 14, 5, 12, 1);

    public static void main(String[] args) {
        System.out.println(minValue(VALUES));
        oddOrEven(integers).forEach(System.out::println);
    }

    private static int minValue(int[] values) {
        List<Integer> list = Arrays.stream(values).boxed().distinct().sorted((o2, o1) -> o1 - o2).collect(Collectors.toList());
        return IntStream.range(0, list.size())
                .map(i -> (int) (list.get(i) * Math.pow(10, i)))
                .reduce(Integer::sum).getAsInt();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(Integer::sum).get();
        if (sum % 2 != 0) {
            return integers.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        } else {
            return integers.stream().filter(x -> x % 2 != 0).collect(Collectors.toList());
        }
    }
}