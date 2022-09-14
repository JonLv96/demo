package pers.kksg.demo.algorithm.midnum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MidNumTest {

    @Test
    void findMedianSortedArrays() {
        MidNum midNum = new MidNum();
        int[] arr1 = {1,2};
        int[] arr2 = {3,4};
//        midNum.findMedianSortedArraysTest(arr1,arr2);
//        midNum.findMedianSortedArrays2(arr1,arr2);
        midNum.findMedianSortedArrays(arr1,arr2);

    }
}