package pers.kksg.demo.algorithm;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/18/23:11
 * @Description: 明明生成了NN个1到500之间的随机整数。请你删去其中重复的数字，即相同的数字只保留一个，把其余相同的数去掉，然后再把这些数从小到大排序，按照排好的顺序输出。
 * <p>
 * 数据范围： 1 \le n \le 1000 \1≤n≤1000  ，输入的数字大小满足 1 \le val \le 500 \1≤val≤500
 * 输入描述：
 * 第一行先输入随机整数的个数 N 。 接下来的 N 行每行输入一个整数，代表明明生成的随机数。 具体格式可以参考下面的"示例"。
 * 输出描述：
 * 输出多行，表示输入数据处理后的结果
 * <p>
 * <p>
 * 输入：
 * 3
 * 2
 * 2
 * 1
 * 输出：
 * 1
 * 2
 * 说明：
 * 输入解释：
 * 第一个数字是3，也即这个小样例的N=3，说明用计算机生成了3个1到500之间的随机整数，接下来每行一个随机数字，共3行，也即这3个随机数字为：
 * 2
 * 2
 * 1
 * 所以样例的输出为：
 * 1
 * 2
 */
public class RemoveTheSamRandNum {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int toleNum = scanner.nextInt();
        Set<Integer> set = new HashSet<>();
        while (toleNum-- > 0) {
            set.add(scanner.nextInt());
        }
        List<Integer> list = set.stream().collect(Collectors.toList());
        sort(list, 0, list.size() - 1);
        list.stream().forEach(System.out::println);
    }

    public static void sort(List<Integer> list, int startIndex, int endIndex) {
        if (endIndex < startIndex + 1) {
            return;
        }
        //假定第一个数为区间数
        int partitionNnum = list.get(startIndex);
        int tempIndex = startIndex;
        //将数组按照区间值进行划分
        for (int i = startIndex + 1; i <= endIndex; i++) {
            if (partitionNnum > list.get(i)) {
                tempIndex++;
                if (i > tempIndex) {
                    swap(list, i, tempIndex);
                }
            }
        }
        swap(list, tempIndex, startIndex);

        //递归将后续数组进行排序
        if (startIndex + 1 < tempIndex) {
            sort(list, startIndex, tempIndex - 1);
        }
        if (tempIndex + 1 < endIndex) {
            sort(list, tempIndex + 1, endIndex);
        }
    }


    public static void swap(List<Integer> list, int index1, int index2) {
        int temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }
}
