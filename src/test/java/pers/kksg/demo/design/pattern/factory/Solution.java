package pers.kksg.demo.design.pattern.factory;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/27/16:54
 * @Description:
 */
public class Solution {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param L int整型
     * @param R int整型
     * @param x int整型
     * @return int整型
     */
    public int countNum(int L, int R, int x) {
        // write code here
        String xStr = String.valueOf(x);
        int totleCount = 0;
        for (int i = L; i <= R; i++) {
            char[] numberChar = String.valueOf(i).toCharArray();
            for (char ch : numberChar) {
                String chStr = String.valueOf(ch);
                if (chStr.equals(xStr)) {
                    totleCount++;
                }
            }
        }
        return totleCount;
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param N int整型 字符串的长度
     * @return int整型
     */
    public int validNum(int N) {
        int result = 0;


        return result;
    }

    public boolean checkValid(String str) {
        if (str.length() <= 1) {
            return true;
        }
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == 'c') {
                //若数组下标为0时
                if (i == 0) {
                    if (chars[i + 1] == 'c') {
                        return false;
                    }
                }
                if (chars[i + 1] == 'c' || chars[i-1] == 'c') {
                    return false;
                }
            }
        }
        return true;
    }

    public void generateStr(int N, int cCount) {
        String c = "c";
        String m = "m";
        List<String[]> list = new ArrayList<>();
        String[] strings;



    }


    @Test
    public void test() {
        System.out.println(countNum(2, 222222, 2));
    }
}
