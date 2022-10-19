package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    static final int[] VALUES = {3, 2, 5, 5, 8};
    static final List<Integer> INTEGERS = Arrays.asList(1, 3, 14, 5, 12, 1);

    public static void main(String[] args) {
        System.out.println(minValue(VALUES));
        oddOrEven(INTEGERS).forEach(System.out::println);
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (p1, p2) -> p1 * 10 + p2);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        boolean isEven = integers.stream().filter(x -> x % 2 != 0).count() % 2 == 0;
        return integers.stream().collect(Collectors.partitioningBy(x -> x % 2 != 0)).get(isEven);
    }
}