package pers.kksg.demo.algorithm;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/27/23:05
 * @Description:
 *  NC52 有效括号序列
 *
 * 给出一个仅包含字符'(',')','{','}','['和']',的字符串，判断给出的字符串是否是合法的括号序列
 * 括号必须以正确的顺序关闭，"()"和"()[]{}"都是合法的括号序列，但"(]"和"([)]"不合法。
 *
 * 数据范围：字符串长度 0\le n \le 100000≤n≤10000
 * 要求：空间复杂度 O(n)O(n)，时间复杂度 O(n)O(n)
 *
 *
 * 输入：
 * "["
 * 返回值：
 * false
 *
 *
 * 输入：
 * "[]"
 * 返回值：
 * true
 */
public class MatchBrackets {

    public static void main(String[] args) {
        System.out.println(isValid("]"));
    }


    /**
     *
     * @param s string字符串
     * @return bool布尔型
     */
    public  static boolean isValid (String s) {
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (char c : chars) {
            //左括号进行入栈操作
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
                continue;
            }
            if (c == ')') {
                if (stack.size() == 0) {
                    return false;
                }
                Character pop = stack.pop();
                if (pop == '(') {
                    continue;
                }
                return false;
            }
            if (c == ']') {
                if (stack.size() == 0) {
                    return false;
                }
                Character pop = stack.pop();
                if (pop == '[') {
                    continue;
                }
                return false;
            }
            if (c == '}') {
                if (stack.size() == 0) {
                    return false;
                }
                Character pop = stack.pop();
                if (pop == '{') {
                    continue;
                }
                return false;
            }
        }
        if (stack.size() != 0) {
            return false;
        }
        return true;
    }

}
