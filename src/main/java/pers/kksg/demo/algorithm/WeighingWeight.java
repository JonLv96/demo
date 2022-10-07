package pers.kksg.demo.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/10/06/21:46
 * @Description:
 *
 * 称砝码
 *
 * 现有n种砝码，重量互不相等，分别为 m1,m2,m3…mn ；
 * 每种砝码对应的数量为 x1,x2,x3...xn 。现在要用这些砝码去称物体的重量(放在同一侧)，问能称出多少种不同的重量。
 *
 *
 * 注：
 *
 * 称重重量包括 0
 *
 * 数据范围：每组输入数据满足 1 \le n \le 10 \1≤n≤10  ， 1 \le m_i \le 2000 \1≤m
 * i
 * ​
 *  ≤2000  ， 1 \le x_i \le 10 \1≤x
 * i
 * ​
 *  ≤10
 * 输入描述：
 * 对于每组测试数据：
 * 第一行：n --- 砝码的种数(范围[1,10])
 * 第二行：m1 m2 m3 ... mn --- 每种砝码的重量(范围[1,2000])
 * 第三行：x1 x2 x3 .... xn --- 每种砝码对应的数量(范围[1,10])
 * 输出描述：
 * 利用给定的砝码可以称出的不同的重量数
 *
 *
 * 输入：
 * 2
 * 1 2
 * 2 1
 * 输出：
 * 5
 * 说明：
 * 可以表示出0，1，2，3，4五种重量。
 */
public class WeighingWeight {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int kinds = scanner.nextInt();
            int[] weights = new int[kinds];
            for (int i = 0; i < kinds; i++) {
                weights[i] = scanner.nextInt();
            }
            int[] nums = new int[kinds];
            for (int i = 0; i < kinds; i++) {
                nums[i] = scanner.nextInt();
            }
            Set<Integer> values = new HashSet<>();
            values.add(0);

            for (int i = 0; i < kinds; i++) {
                List<Integer> list = new ArrayList<>(values);
                for (int j = 0; j < list.size(); j++) {
                    for (int k = 0; k < nums[i]; k++) {
                        values.add(list.get(j) + weights[i] * (k + 1));
                    }

                }
            }
            System.out.println(values.size());
        }
    }
}