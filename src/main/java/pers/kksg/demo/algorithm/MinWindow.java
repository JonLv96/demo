package pers.kksg.demo.algorithm;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/30/17:47
 * @Description: NC28 最小覆盖子串
 * <p>
 * <p>
 * 给出两个字符串 s 和 t，要求在 s 中找出最短的包含 t 中所有字符的连续子串。
 * <p>
 * 数据范围：0 \le |S|,|T| \le100000≤∣S∣,∣T∣≤10000，保证s和t字符串中仅包含大小写英文字母
 * 要求：进阶：空间复杂度 O(n)O(n) ， 时间复杂度 O(n)O(n)
 * 例如：
 * S ="XDOYEZODEYXNZ"S="XDOYEZODEYXNZ"
 * T ="XYZ"T="XYZ"
 * 找出的最短子串为"YXNZ""YXNZ".
 * <p>
 * 注意：
 * 如果 s 中没有包含 t 中所有字符的子串，返回空字符串 “”；
 * 满足条件的子串可能有很多，但是题目保证满足条件的最短的子串唯一。
 * <p>
 * <p>
 * <p>
 * 示例1
 * 输入：
 * "XDOYEZODEYXNZ","XYZ"
 * 返回值：
 * "YXNZ"
 * <p>
 * <p>
 * 输入：
 * "abcAbA","AA"
 * 返回值：
 * "AbA"
 */
public class MinWindow {
    public static void main(String[] args) {
        System.out.println(minWindow("aa", "aa"));
    }

    public static String minWindow(String S, String T) {
        int[] chars = new int[128];
        //定义一个存储所有子串数组， index=字符ASCII值，value=是否存在该字符
        for (char c : T.toCharArray()) {
            chars[c] -= 1;
        }
        int left = 0, right = 0;
        int moveLeft = 0, moveRight = 0;
        int minLen = S.length();
        for (; moveRight < S.length(); moveRight++) {
            if (T.contains(String.valueOf(S.charAt(moveRight)))) {
                chars[S.charAt(moveRight)] += 1;
            }
            while (check(chars)) {
                //满足条件
                if (minLen >= (moveRight - moveLeft + 1)) {
                    left = moveLeft;
                    right = moveRight;
                    minLen = moveRight - moveLeft + 1;
                }
                //窗口左边界移动
                if (T.contains(String.valueOf(S.charAt(moveLeft)))) {
                    chars[S.charAt(moveLeft)] -= 1;
                }
                moveLeft += 1;
            }
        }

        //无匹配子串
        if (minLen == S.length() && !S.equals(T)) {
            return "";
        }
        return S.substring(left, right + 1);
    }

    private static boolean check(int[] chars) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] < 0) {
                return false;
            }
        }
        return true;
    }
}