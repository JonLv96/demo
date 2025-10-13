package pers.kksg.demo.algorithm.sort;

import org.apache.commons.lang.StringUtils;

import java.util.Scanner;

/**
 * @project: demo
 * @description: 给你一个字符串 s，找到 s 中最长的 回文 子串。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * 示例 2：
 * <p>
 * 输入：s = "cbbd"
 * 输出："bb"
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= s.length <= 1000
 * s 仅由数字和英文字母组成
 * @author: lvqiang
 * @create: 2025-10-09 14:33
 **/
public class Test2 {

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String inputStr = scanner.nextLine();
//        scanner.close();
        String inputStr = "cbbd";

        char[] charArray = inputStr.toCharArray();
        int maxLen = 0;
        int startIndex = 0, endIndex = 0;

        for (int i = 1; i < charArray.length; i++) {
            int tempStartIndex = 0, tempEndIndex = 0;
            // 以为 索引为 i 元素为中心
            for (int j = 0; j <= i; j++) {
                if (j + i >= charArray.length || i - j < 0) {
                    break;
                }
                if (charArray[j + i] == charArray[i - j]) {
                    tempStartIndex = i - j;
                    tempEndIndex = j + i;
                }
            }
            if (tempEndIndex - tempStartIndex > maxLen) {
                maxLen = tempEndIndex - tempStartIndex;
                startIndex = tempStartIndex;
                endIndex = tempEndIndex;
            }
        }
        if (maxLen == 0) {
            System.out.println();
            return;
        }
        System.out.println(StringUtils.substring(inputStr, startIndex, endIndex + 1));
    }

}
