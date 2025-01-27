package org.example;// you can also use imports, for example:

class Solution {
    public int solution(int[] A) {
        int[] sequence = {1, 0, -1, 0};
        int result = 0;
        for (int i = 0; i < A.length; i++) {
            int k = i % 4;
            if (A[i] == 0 && sequence[k] != 0) {
                return -1;
            }
            if ((A[i] > 0 && sequence[k] != 1) || (A[i] < 0 && sequence[k] != -1)) {
                result++;
            }
        }
        return result;
    }
}
