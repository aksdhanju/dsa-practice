//package com.example.dsa.arrays;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TwoSumTest {
//
//    @Test
//    void testTwoSum() {
//        TwoSum twoSum = TwoSum.builder()
//                .nums(new int[]{2, 7, 11, 15})
//                .target(9)
//                .build();
//
//        int[] result = twoSum.solve();
//        assertArrayEquals(new int[]{0, 1}, result);
//    }
//    public class Solution {
//        /**
//         * @param rooms: m x n 2D grid
//         * @return: nothing
//         */
//        int m;
//        int n;
//        public void wallsAndGates(int[][] rooms) {
//            // write your code here
//            m = rooms.length;
//            n = rooms[0].length;
//
//            for (int i=0;i<m;i++){
//                for (int j=0;j<n;j++){
//                    if (rooms[i][j] == 0) {
//                        dfs(rooms, i, j, 0);
//                    }
//                }
//            }
//        }
//
//        void dfs(int[][]rooms, int i, int j, int dist) {
//            if (i<0 || j<0 || i>=m || j>= n) {
//                return;
//            }
//
//            if (rooms[i][j] == -1 || rooms[i][j] == 0 || dist>=rooms[i][j]) {
//                return;
//            }
//
//            rooms[i][j] = dist;
//
//            // if (dist < rooms[i][j] ){
//            //     rooms[i][j] = dist;
//            // }
//
//            dfs(rooms, i-1, j, dist+1);
//            dfs(rooms, i, j-1, dist+1);
//            dfs(rooms, i+1, j, dist+1);
//            dfs(rooms, i, j+1, dist+1);
//        }
//    }
//}