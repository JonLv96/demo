package pers.kksg.demo.algorithm;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/10/09/17:27
 * @Description: 204.计数质数
 * <p>
 * <p>
 * 输入：n = 10
 * 输出：4
 * 解释：小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
 */
public class CountPrime {

    /**
     * 埃氏筛
     *
     * @param n
     * @return
     */
    public static int countPrimes2(int n) {
        boolean[] isPrime = new boolean[n];
        Arrays.fill(isPrime, true);

        for (int i = 2; i < Math.sqrt(n); i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }

        }

        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) {
                count++;
            }
        }
        return count;
    }


    public static void main(String[] args) {
        System.out.println(countPrimes2(10));
    }

    public static int countPrimes(int n) {
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime(i)) {
                System.out.println(i);
                count++;
            }
        }
        return count;
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


