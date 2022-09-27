package pers.kksg.demo.algorithm;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/27/23:26
 * @Description:
 *
 * 括号的最大嵌套深度
 *
 *
 * 如果字符串满足以下条件之一，则可以称之为 有效括号字符串（valid parentheses string，可以简写为 VPS）：
 *
 * 字符串是一个空字符串 ""，或者是一个不为 "(" 或 ")" 的单字符。
 * 字符串可以写为 AB（A 与 B 字符串连接），其中 A 和 B 都是 有效括号字符串 。
 * 字符串可以写为 (A)，其中 A 是一个 有效括号字符串 。
 * 类似地，可以定义任何有效括号字符串 S 的 嵌套深度 depth(S)：
 *
 * depth("") = 0
 * depth(C) = 0，其中 C 是单个字符的字符串，且该字符不是 "(" 或者 ")"
 * depth(A + B) = max(depth(A), depth(B))，其中 A 和 B 都是 有效括号字符串
 * depth("(" + A + ")") = 1 + depth(A)，其中 A 是一个 有效括号字符串
 * 例如：""、"()()"、"()(()())" 都是 有效括号字符串（嵌套深度分别为 0、1、2），而 ")(" 、"(()" 都不是 有效括号字符串 。
 *
 * 给你一个 有效括号字符串 s，返回该字符串的 s 嵌套深度 。
 *
 * 输入：s = "(1+(2*3)+((8)/4))+1"
 * 输出：3
 * 解释：数字 8 在嵌套的 3 层括号中。
 *
 *
 * 输入：s = "(1)+((2))+(((3)))"
 * 输出：3
 *
 *
 * 提示：
 *
 * 1 <= s.length <= 100
 * s 由数字 0-9 和字符 '+'、'-'、'*'、'/'、'('、')' 组成
 * 题目数据保证括号表达式 s 是 有效的括号表达式
 *
 */
public class BracketsMaxDepth {

    public static void main(String[] args) {
        System.out.println(maxDepth("8*((1*(5+6))*(8/6))"));;
    }

    public static int maxDepth(String s) {
        Stack<Character> stack = new Stack<Character>();
        char[] chars = s.toCharArray();
        int maxDepth = 0;
        for (char c : chars) {
            if (c == '(') {
                stack.push('(');
                maxDepth = Math.max(stack.size(),maxDepth);
            }
            if (c == ')') {
                stack.pop();
            }
        }
        return maxDepth;
    }

}
