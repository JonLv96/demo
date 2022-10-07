package pers.kksg.demo.algorithm;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/10/07/22:28
 * @Description:
 *
 *
 * 求最小公倍数
 *
 * 正整数A和正整数B 的最小公倍数是指 能被A和B整除的最小的正整数值，设计一个算法，求输入A和B的最小公倍数。
 *
 *
 * 输入：
 * 5 7
 * 输出：
 * 35
 */
public class MinCommMultiple {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();
        int temp = num1;
        System.out.println(gcb(temp, num1, num2));
    }
    private static int gcb(int temp, int num1, int num2) {
        if (temp % num2 == 0) {
            return temp;
        }

        return gcb(temp + num1,num1,num2);
    }
}
