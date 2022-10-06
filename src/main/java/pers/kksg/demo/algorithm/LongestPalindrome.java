package pers.kksg.demo.algorithm;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/30/16:16
 * @Description: 最长回文子串
 * <p>
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 */
public class LongestPalindrome {
    /******************************方法1：暴力破解*************************************************/
//    public String longestPalindrome(String s) {
//        //定义当前最大回文字符串长度，该字符串起始索引及其结束索引
//        int maxPalindromeStr = 1, beginIndex = 0, endIndex = 0;
//        //双层for循环遍历该字符串所有组合
//        for (int begin = 0; begin < s.length() - 1; begin++) {
//            for (int end = begin + 1; end < s.length(); end++) {
//                int subStrLen = end + 1 - begin;
//                //若该截取字符串为回文字符串，则更新最长回文字符及其索引
//                if (subStrLen > maxPalindromeStr && isPalindromeString(s.substring(begin, end + 1))) {
//                    maxPalindromeStr = end - begin;
//                    beginIndex = begin;
//                    endIndex = end;
//                }
//            }
//        }
//        return s.substring(beginIndex, endIndex + 1);
//    }
//
//    /**
//     * 判断该字符串是否为回文字符串
//     *
//     * @param str
//     * @return
//     */
//    public static boolean isPalindromeString(String str) {
//        for (int i = 0; i < str.length() / 2; i++) {
//            if (str.charAt(i) != str.charAt(str.length() - 1 - i)) {
//                return false;
//            }
//        }
//        return true;
//    }

    /******************************方法2：中心扩散*************************************************/
//    public static String longestPalindrome(String s) {
//        //特殊情况处理
//        if (s == null || s.equals("")) {
//            return "";
//        }
//        if (s.length() <= 1) {
//            return s;
//        }
//        int maxLen = 1, maxLeftIndex = 0, maxRightIndex = 0;
//        for (int center = 1; center < s.length(); center++) {
//            //针对中心点是整数如 abcba 类型处理
//            int left = center - 1, right = center + 1;
//            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
//                left--;
//                right++;
//            }
//            //更新最大长度 及其索引
//            if (left + 1 != center && (right - left - 1) > maxLen) {
//                maxLen = right - left - 1;
//                maxLeftIndex = left + 1;
//                maxRightIndex = right - 1;
//            }
//            //针对中心点非整数  如aaaa类型处理
//            left = center - 1;
//            right = center;
//            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
//                left--;
//                right++;
//            }
//            //更新最大长度 及其索引
//            if (left + 1 != center && (right - left - 1) > maxLen) {
//                maxLen = right - left - 1;
//                maxLeftIndex = left + 1;
//                maxRightIndex = right - 1;
//            }
//        }
//        return s.substring(maxLeftIndex, maxRightIndex + 1);
//    }

    /******************************方法3：动态规划*************************************************/
    //例如要想知道   abcba 是否为回文字串， 先判断 两边的字符是否相等，前面运算肯定已经得出了bcb子串是否回文，此时结合该结果即可
    //知道我们所求子串是否回文
//    public static String longestPalindrome(String s) {
//        //特殊情况处理
//        if (s == null || s.equals("")) {
//            return "";
//        }
//        if (s.length() <= 1) {
//            return s;
//        }
//        //定义一个备忘录记录上一次查询该子串是否为回文
//        boolean[][] dp = new boolean[s.length()][s.length()];
//        //定义最长回文串长度、及其最长回文串开始索引位置
//        int maxLen = 1, begin = 0;
//        for (int i = 0; i < s.length(); i++) {
//            dp[i][i] = true;
//        }
//        for (int column = 1; column < s.length(); column++) {
//            for (int row = 0; row < column; row++) {
//                //若当前位置字符相等，判断其字串是否为回文 ， 即dp[row+1][column-1]
//                if (s.charAt(column) == s.charAt(row)) {
//                    //若column-row<=3 即该串为回文
//                    if (column - row < 3) {
//                        dp[row][column] = true;
//                    } else {
//                        dp[row][column] = dp[row + 1][column - 1];
//                    }
//                }
//                //若该串为回文串，更新最大回文长度，及其起始索引
//                if (dp[row][column] && (column - row + 1) > maxLen) {
//                    maxLen = column - row + 1;
//                    begin = row;
//                }
//            }
//        }
//        return s.substring(begin, begin + maxLen);
//    }
    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        int maxLeftIndex = 0, maxRightIndex = 0,maxLen = 1;
        for (int i = 1; i < s.length() - 1; i++) {
            int leftIndex = i;
            int rightIndex = i;
            while (leftIndex >= 0 && rightIndex <= s.length()) {
                if (s.charAt(leftIndex -1) == s.charAt(rightIndex+1)) {
                    leftIndex--;
                    rightIndex++;
                    continue;
                }
                break;
            }
            if (maxLen < (rightIndex - leftIndex +1)) {
                maxLen = maxRightIndex - maxLeftIndex - 1;
                maxLeftIndex = leftIndex;
                maxRightIndex = rightIndex;
            }
        }
        return s.substring(maxLeftIndex,maxRightIndex+1);
    }

    public static void main(String[] args) {
        longestPalindrome("babad");
    }
}
