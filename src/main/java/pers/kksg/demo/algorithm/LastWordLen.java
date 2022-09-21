package pers.kksg.demo.algorithm;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/18/16:55
 * @Description:
 *
 *
 * 计算字符串最后一个单词的长度，单词以空格隔开，字符串长度小于5000。（注：字符串末尾不以空格为结尾）
 * 输入描述：
 * 输入一行，代表要计算的字符串，非空，长度小于5000。
 *
 * 输出描述：
 * 输出一个整数，表示输入字符串最后一个单词的长度。
 *
 * 输入：
 * hello nowcoder
 * 输出：
 * 8
 * 说明：
 * 最后一个单词为nowcoder，长度为8
 */
public class LastWordLen {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] strings = str.split(" ");
        System.out.println(strings[strings.length - 1].length());;
    }
}