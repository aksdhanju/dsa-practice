package com.example.dsa.arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TwoSumTest {

    @Test
    void testTwoSum() {
        TwoSum twoSum = TwoSum.builder()
                .nums(new int[]{2, 7, 11, 15})
                .target(9)
                .build();

        int[] result = twoSum.solve();
        assertArrayEquals(new int[]{0, 1}, result);
    }
}
