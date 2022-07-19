package pers.kksg.demo.algorithm.longestSubStr;

import java.util.HashMap;
import java.util.Map;

/**
 * LengthOfLongestSubstring
 *
 * @Author Jonlv
 * @Description TODO
 * @Date 2022/7/8 11:46
 *
 *
 *
 *
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 *
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 */
public class LengthOfLongestSubString {

    public int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        if (s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        int maxLen = 1;
        Map<Character, Integer> map = new HashMap<>();
        map.put(chars[0], 0);
        for (int start = 0, end = start + 1; end < chars.length; end++) {
            Integer num = map.get(chars[end]);
            //当存在相同字符、计算长度并退出
            if (num != null) {
                //将i 更新至第一个重复的位置
                start = start > num ? start : num + 1;
            }
            maxLen = maxLen > (end - start + 1) ? maxLen : (end - start + 1);
            map.put(chars[end], end);
        }
        return maxLen;
    }


    public int lengthOfLongestSubstring2(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
            char alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);
            }
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1 );
        }
        return ans;
    }
}
