package pers.kksg.demo.algorithm;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/10/07/22:52
 * @Description:
 *
 * 查找组成一个偶数最接近的两个素数
 *
 * 任意一个偶数（大于2）都可以由2个素数组成，组成偶数的2个素数有很多种情况，本题目要求输出组成指定偶数的两个素数差值最小的素数对。
 *
 * 数据范围：输入的数据满足 4 \le n \le 1000 \4≤n≤1000
 * 输入描述：
 * 输入一个大于2的偶数
 *
 * 输出描述：
 * 从小到大输出两个素数
 *
 * 输入：
 * 20
 * 输出：
 * 7
 * 13
 *
 *
 */
public class FindNearPrime {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        for (int i = num/2; i >0 ; i--) {
            if (isPrime(i) && isPrime(num - i)) {
                System.out.println(i);
                System.out.println(num - i);
                return;
            }

        }
    }
    private static boolean isPrime(int num) {
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

}
