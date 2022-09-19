package pers.kksg.demo.algorithm.sort;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/17/23:38
 * @Description:
 */
public class Test {
    public static int[] arr = {33, 5, 7, 19, 3, 99, 13, 64, 35};

    public static void main(String[] args) {
        printArr(arr);
        quickSort();
        printArr(arr);
    }


    public static void bubbleSort() {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(j, j + 1);
                }
            }
        }
    }

    public static void selectionSort() {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    swap(i, j);
                }

            }
        }
    }

    public static void insertSort() {
        for (int i = 1; i < arr.length; i++) {
            int tempIndex = i - 1;
            int tempValue = arr[i];
            while (tempIndex >= 0) {
                if (tempValue > arr[tempIndex]) {
                    break;
                }
                arr[tempIndex + 1] = arr[tempIndex--];
            }
            arr[tempIndex + 1] = tempValue;
        }
    }

    public static void quickSort() {
        quickRecursion(0, arr.length - 1);
    }

    public static void quickRecursion(int startIndex, int endIndex) {
        if (endIndex <= startIndex + 1) {
            return;
        }
        //假设区间值为第一个值
        int partitionValue = arr[startIndex];
        int tempIndex = startIndex;
        for (int i = startIndex + 1; i <= endIndex; i++) {
            if (arr[i] < partitionValue) {
                tempIndex++;
                if (i > tempIndex) {
                    swap(i, tempIndex);
                }
            }
        }
        swap(tempIndex, startIndex);
        //进行区间两边 数组递归排序
        if (tempIndex > startIndex + 1) {
            quickRecursion(startIndex, tempIndex - 1);
        }
        if (endIndex - 1 > tempIndex) {
            quickRecursion(tempIndex + 1, endIndex);
        }

    }

    public static void shellSort() {
        for (int i = arr.length / 2; i <= 1; i /= 2) {
            for (int j = 0; j < i; j++) {

                

            }

        }
    }


    public static void swap(int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    public static void printArr(int[] arr) {
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
