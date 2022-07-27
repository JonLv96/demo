package pers.kksg.demo.algorithm.midnum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MidNumTest {

    @Test
    void findMedianSortedArrays() {
        MidNum midNum = new MidNum();
        int[] arr1 = {2,2,4,4};
        int[] arr2 = {2,2,4,4};
        midNum.findMedianSortedArrays(arr1,arr2);

    }
}