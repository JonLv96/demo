package pers.kksg.demo.algorithm;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/27/10:27
 * @Description: 实现删除字符串中出现次数最少的字符，若出现次数最少的字符有多个，则把出现次数最少的字符都删除。输出删除这些单词后的字符串，字符串中其它字符保持原来的顺序。
 * <p>
 * 数据范围：输入的字符串长度满足 1 \le n \le 20 \1≤n≤20  ，保证输入的字符串中仅出现小写字母
 * 输入描述：
 * 字符串只包含小写英文字母, 不考虑非法输入，输入的字符串长度小于等于20个字节。
 * <p>
 * 输出描述：
 * 删除字符串中出现次数最少的字符后的字符串。
 * <p>
 * <p>
 * 输入：
 * aabcddd
 * 输出：
 * aaddd
 */
public class RemoveMinChar {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.next();
            //统计 每个字符个数
            char[] chars = str.toCharArray();
            HashMap<Character, Integer> charNumMap = new HashMap<>();
            for (char aChar : chars) {
                Integer num = charNumMap.get(aChar);
                Integer result = Optional.ofNullable(num).map(n -> ++n).orElse(1);
                charNumMap.put(aChar, result);
            }
            Integer min = Collections.min(charNumMap.values());

            for (Map.Entry<Character, Integer> entry : charNumMap.entrySet()) {
                if (entry.getValue().equals(min)) {
                    str = str.replaceAll(String.valueOf(entry.getKey()), "");
                }
            }
            System.out.println(str);
        }
    }

}
