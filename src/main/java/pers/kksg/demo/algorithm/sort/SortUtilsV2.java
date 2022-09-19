package pers.kksg.demo.algorithm.sort;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/14/10:59
 * @Description:排序算法
 */
public class SortUtilsV2 {
    /**
     * 冒泡排序
     *
     * @param array
     * @return
     */
    public static int[] bubbleSort(int[] array) {
        if (Objects.isNull(array)) {
            return array;
        }
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    /**
     * 冒泡排序优化
     * 1.引入flag标志， 如果该次排序未发生 冒泡位置变动，则表明目前数组已经是有序的了，无需再进行冒泡
     * 2.lastExchangeIndex，记住数组最后进行交换的位置，【0，lastExchangeIndex】 为无序，【lastExchangeIndex，arr.length-1】为有序，下次遍历到该位置即可
     *
     * @param array
     * @return
     */
    public static int[] bubbleSortV2(int[] array) {
        if (Objects.isNull(array)) {
            return array;
        }
        int count = 0;
        int sortedFlag = 1;
        int lastExchangeIndex = array.length - 1;
        int k;
        for (int i = 0; i < array.length - 1; i++) {
            k = lastExchangeIndex;
            for (int j = 0; j < k; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    sortedFlag = 0;
                    lastExchangeIndex = j;
                }
                count++;
            }
            //若有序标志为1 表示目前数组已经是有序的
            if (Objects.equals(sortedFlag, 1)) {
                System.out.println("循环遍历次数：" + count);
                return array;
            }
        }
        System.out.println("循环遍历次数：" + count);
        return array;
    }

    /**
     * 选择排序
     *
     * @param array
     * @return
     */
    public static int[] selectionSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        int count = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    array[i] = array[i] ^ array[j];
                    array[j] = array[i] ^ array[j];
                    array[i] = array[i] ^ array[j];
                }
                count++;
            }
        }
        System.out.println("循环遍历次数：" + count);
        return array;
    }

    /**
     * 插入排序
     *
     * @param array
     * @return
     */
    public static int[] insertionSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        int curr = 0;
        for (int i = 0; i < array.length - 1; i++) {
            int moveIndex = i;
            curr = array[i + 1];
            while (moveIndex >= 0 && array[moveIndex] > curr) {
                array[moveIndex + 1] = array[moveIndex];
                moveIndex--;
            }
            array[moveIndex + 1] = curr;
        }
        return array;
    }

    /**
     * 希尔排序
     *
     * @param array
     * @return
     */
    public static int[] shellSort(int[] array) {
        for (int i = array.length / 2; i >= 1; i /= 2) {
            for (int j = 0; j + i < array.length; j += i) {
                int curr = array[j + i];
                int preIndex = j;
                while (preIndex >= 0 && array[preIndex] > curr) {
                    array[preIndex + i] = array[preIndex];
                    preIndex -= i;
                }
                array[preIndex + i] = curr;
            }
        }
        return array;
    }

    /**
     * 归并排序
     *
     * @param array
     * @return
     */
    public static int[] MergeSort(int[] array) {
        if (array.length < 2) {
            return array;
        }
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        return merge(MergeSort(left), MergeSort(right));
    }

    /**
     * 归并排序——将两段排序好的数组结合成一个排序数组
     *
     * @param left
     * @param right
     * @return
     */
    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= left.length) {
                result[index] = right[j++];
            } else if (j >= right.length) {
                result[index] = left[i++];
            } else if (left[i] > right[j]) {
                result[index] = right[j++];
            } else {
                result[index] = left[i++];
            }
        }
        return result;
    }

    /**
     * 快速排序方法
     *
     * @param array
     * @return
     */
    public static int[] quickSort(int[] array) {
        if (Objects.isNull(array)) {
            return null;
        }
        quickRecursion(array, 0, array.length - 1);
        return array;
    }

    public static void quickRecursion(int[] arr, int startIndex, int endIndex) {
        if (startIndex + 1 == endIndex) {
            return;
        }
        //假定默认取第一个值为分区样本
        int partitionIndex = startIndex;
        for (int i = startIndex + 1; i <= endIndex; i++) {
            if (arr[i] <= arr[startIndex]) {
                partitionIndex++;
                if (i > partitionIndex) {
                    swap(arr, i, partitionIndex);
                }
            }
        }
        swap(arr, startIndex, partitionIndex);
        //对样本值左右区间进行递归排序
        if (partitionIndex > startIndex + 1) {
            quickRecursion(arr, startIndex, partitionIndex - 1);
        }
        if (partitionIndex < endIndex - 1) {
            quickRecursion(arr, partitionIndex + 1, endIndex);
        }
    }

    /**
     * 快速排序算法——partition
     *
     * @param array
     * @param start
     * @param end
     * @return
     */
    public static int partition(int[] array, int start, int end) {
        int pivot = (int) (start + Math.random() * (end - start + 1));
        int smallIndex = start - 1;
        swap(array, pivot, end);
        for (int i = start; i <= end; i++) {
            if (array[i] <= array[end]) {
                smallIndex++;
                if (i > smallIndex) {
                    swap(array, i, smallIndex);
                }
            }
        }
        return smallIndex;
    }

    /**
     * 交换数组内两个元素
     *
     * @param array
     * @param i
     * @param j
     */
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    //声明全局变量，用于记录数组array的长度；
    static int len;

    /**
     * 堆排序算法
     *
     * @param array
     * @return
     */
    public static int[] HeapSort(int[] array) {
        len = array.length;
        if (len < 1) {
            return array;
        }
        //1.构建一个最大堆
        buildMaxHeap(array);
        //2.循环将堆首位（最大值）与末位交换，然后在重新调整最大堆
        while (len > 0) {
            swap(array, 0, len - 1);
            len--;
            adjustHeap(array, 0);
        }
        return array;
    }

    /**
     * 建立最大堆
     *
     * @param array
     */
    public static void buildMaxHeap(int[] array) {
        //从最后一个非叶子节点开始向上构造最大堆
        for (int i = (len / 2 - 1); i >= 0; i--) {
            adjustHeap(array, i);
        }
    }

    /**
     * 调整使之成为最大堆
     *
     * @param array
     * @param i
     */
    public static void adjustHeap(int[] array, int i) {
        int maxIndex = i;
        //如果有左子树，且左子树大于父节点，则将最大指针指向左子树
        if (i * 2 < len && array[i * 2] > array[maxIndex]) {
            maxIndex = i * 2;
        }
        //如果有右子树，且右子树大于父节点，则将最大指针指向右子树
        if (i * 2 + 1 < len && array[i * 2 + 1] > array[maxIndex]) {
            maxIndex = i * 2 + 1;
        }
        //如果父节点不是最大值，则将父节点与最大值交换，并且递归调整与父节点交换的位置。
        if (maxIndex != i) {
            swap(array, maxIndex, i);
            adjustHeap(array, maxIndex);
        }
    }
}
