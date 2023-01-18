package pers.kksg.demo.algorithm;

/**
 * @project demo
 * @description 数字反转
 * @author lvqiang
 * @date 2023/2/2 15:23:49
 * @version 1.0
 *
 *
 *
 * //给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 * //
 * // 如果反转后整数超过 32 位的有符号整数的范围 [−2³¹, 231 − 1] ，就返回 0。
 * //假设环境不允许存储 64 位整数（有符号或无符号）。
 * //
 * //
 * //
 * // 示例 1：
 * //
 * //
 * //输入：x = 123
 * //输出：321
 * //
 * //
 * // 示例 2：
 * //
 * //
 * //输入：x = -123
 * //输出：-321
 * //
 * //
 * // 示例 3：
 * //
 * //
 * //输入：x = 120
 * //输出：21
 * //
 * //
 * // 示例 4：
 * //
 * //
 * //输入：x = 0
 * //输出：0
 * //
 * //
 * //
 * //
 * // 提示：
 * //
 * //
 * // -2³¹ <= x <= 2³¹ - 1
 * //
 * //
 * // Related Topics 数学 👍 3748 👎 0
 */
public class NumOver {
    static class Solution {
        public int reverse(int x) {
            //2147483647
            int maxNum = Integer.MAX_VALUE / 10;
            int result = 0;
            while (x != 0) {
                int num = x % 10;
                x /= 10;

                if (result > maxNum || (result == maxNum && num > 7)) {
                    return 0;
                }
                if (result < maxNum * -1 || (result == maxNum * -1 && num < -8)) {
                    return 0;
                }

                result = result * 10 + num;
            }
            return result;
        }
    }
}
