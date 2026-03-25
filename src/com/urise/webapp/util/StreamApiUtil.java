package com.urise.webapp.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class StreamApiUtil {
    static void main() {
        int[] numArray = {7, 1, 8, 0, -1, 3, 1, 91, -2, 5, 3, 45, 8, 2, 3};
        System.out.println("Result of minValue:  " + minValue(numArray));
        List<Integer> nums = Arrays.stream(numArray).boxed().toList();
        System.out.println("Result of oddOrEven: " + oddOrEven(nums));
    }

    static int minValue(int[] nums) {
        return IntStream.of(nums)
                .filter(x -> x > 0)
                .filter(x -> x < 10)
                .distinct()
                .sorted()
                .reduce(0, (x, y) -> x * 10 + y);
    }

    static List<Integer> oddOrEven(List<Integer> nums) {
        Map<Boolean, List<Integer>> oddOrEvenMap = nums.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        if (oddOrEvenMap.get(false).size() % 2 == 0) return oddOrEvenMap.get(true);
        return oddOrEvenMap.get(false);
    }
}