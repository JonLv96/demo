package pers.kksg.demo.algorithm;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/27/16:52
 * @Description:
 *
 * 将一个字符串str的内容颠倒过来，并输出。
 *
 * 数据范围：1 \le len(str) \le 10000\1≤len(str)≤10000
 * 输入描述：
 * 输入一个字符串，可以有空格
 *
 * 输出描述：
 * 输出逆序的字符串
 *
 *
 * 输入：
 * I am a student
 * 输出：
 * tneduts a ma I
 */
public class InversionStr {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        char[] chars = str.toCharArray();
        char[] inversionChar = new char[chars.length];
        int index = chars.length - 1;
        for (char c : chars) {
            inversionChar[index--] = c;
        }
        System.out.println(new String(inversionChar));
    }
}