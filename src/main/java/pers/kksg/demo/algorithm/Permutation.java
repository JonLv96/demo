package pers.kksg.demo.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/30/15:01
 * @Description: 输入：S = "qqe"
 * 输出：["eqq","qeq","qqe"]
 * <p>
 * <p>
 * 输入：S = "ab"
 * 输出：["ab", "ba"]
 */
public class Permutation {
    List<String> list = new ArrayList<>();

    public String[] permutation(String S) {

        char[] chars = S.toCharArray();
        int[] used = new int[chars.length];
        StringBuilder sb = new StringBuilder();
        dfs(used, chars, sb);
        // 格式处理
        String[] res = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    public void dfs(int[] used, char[] chars, StringBuilder sb) {
        if (sb.length() == chars.length) {
            list.add(sb.toString());
            return;
        }
        /**
         *                                              java
         *                j                                     a                      v                     a
         *        a       v        a                        j   v   a              j   a   a             j   a   v
         *      v   a   a   a    a   v
         *      a   v   a   a    v   a
         *  java\jaav\jvaa\jvaa\jaav\java
         *
         *  通过一个选择树可以发现问题。当有重复字母时,出现了重复的树
         *  这里通过一个技巧，这个技巧就是先排序后再处理，不过如果不看题解，我很难想到要先排序。
         *
         *  如果不先排序，那么在同一层遇到相同的字母时应该跳过，这个代码应该怎么写呢？
         *
         *
         *
         */
        /// 每次面临的选择
        for (int i = 0; i < chars.length; i++) {
            if (used[i] == 1) {
                continue;
            }
            /**
             *  used[j] == 0 且 chars[j] == cur 说明在同一层 此时遇到相等的元素需要剪枝，因为该分支和之前的分支重复了
             *  used[j] == 1 且 chars[j] == cur 不是同一层 此时即便遇到相等的元素也是不能剪枝的 比如第二层的a 和 第三层的a
             */
            char cur = chars[i];
            boolean valid = true;
            for (int j = 0; j < i; j++) {
                if (used[j] == 0 && chars[j] == cur) {
                    /// 这个cur不能要
                    valid = false;
                }
            }
            if (!valid) {
                continue;
            }
            sb.append(chars[i]);
            used[i] = 1;
            dfs(used, chars, sb);
            sb.deleteCharAt(sb.length() - 1);
            used[i] = 0;
        }
    }

}
