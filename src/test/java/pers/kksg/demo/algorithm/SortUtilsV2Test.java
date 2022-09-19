package pers.kksg.demo.algorithm;

import org.junit.jupiter.api.Test;
import pers.kksg.demo.algorithm.sort.SortUtilsV2;

import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/14/11:24
 * @Description:
 */
class SortUtilsV2Test {

    int[] arr = {33, 5, 7, 19, 3, 99, 13, 64, 35};
    int[] halfSortedArr = {3, 5, 7, 13, 19, 33, 99, 64, 35};
    int[] sortedArr = {3, 5, 7, 13, 19, 33, 35, 64, 99};


    @Test
    void bubbleSort() {
        printArr(arr);
        SortUtilsV2.bubbleSort(arr);
        printArr(arr);
    }

    @Test
    void bubbleSortV2() {
        printArr(arr);
        SortUtilsV2.bubbleSortV2(arr);
        printArr(arr);

        printArr(halfSortedArr);
        SortUtilsV2.bubbleSortV2(halfSortedArr);
        printArr(halfSortedArr);
    }

    @Test
    void selectionSort() {
        printArr(arr);
        SortUtilsV2.selectionSort(arr);
        printArr(arr);
    }

    @Test
    void insertionSort() {
        printArr(arr);
        SortUtilsV2.insertionSort(arr);
        printArr(arr);
    }

    @Test
    void shellSort() {
        printArr(arr);
        SortUtilsV2.shellSort(arr);
        printArr(arr);
    }

    @Test
    void mergeSort() {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put(null, "11");
        System.out.println(hashtable);
    }

    @Test
    void merge() {
    }

    @Test
    void quickSort() {
        printArr(arr);
        SortUtilsV2.quickSort(arr);
        printArr(arr);
    }

    @Test
    void partition() {
    }

    @Test
    void swap() {
    }

    @Test
    void heapSort() {
    }

    @Test
    void buildMaxHeap() {
    }

    @Test
    void adjustHeap() {
    }

    void printArr(int[] arr) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i == arr.length - 1) {
                break;
            }
            builder.append(",");
        }
        builder.append("]");
        System.out.println(builder);
    }
}