package com.urise.webapp.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class StreamApiUtil {
    static int minValue(int[] nums) {
        return IntStream.of(nums)
                .filter(x -> x < 10)
                .filter(x -> x > 0)
                .distinct()
                .sorted()
                .reduce(0, (x, y) -> x * 10 + y);
    }

    static List<Integer> oddOrEven(List<Integer> nums) {
        Map<Boolean, List<Integer>> evenMap = nums.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        if (evenMap.get(false).size() % 2 == 0) return evenMap.get(true);
        return evenMap.get(false);
    }

    static void main() {
        int[] numArray = {7, 1, 8, 0, -1, 3, 1, 91, -2, 5, 3, 45, 8, 2};
        System.out.println("Result of minValue:  " + minValue(numArray));
        Integer[] boxedArray = new Integer[numArray.length];
        for (int i = 0; i < numArray.length; i++) {
            boxedArray[i] = numArray[i];
        }
        List<Integer> numList = Arrays.asList(boxedArray);
        System.out.println("Result of oddOrEven: " + oddOrEven(numList));
    }
}