package pers.kksg.demo.algorithm;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/20/1:13
 * @Description: 密码要求:
 * <p>
 * 1.长度超过8位
 * <p>
 * 2.包括大小写字母.数字.其它符号,以上四种至少三种
 * <p>
 * 3.不能有长度大于2的包含公共元素的子串重复 （注：其他符号不含空格或换行）
 * <p>
 * 数据范围：输入的字符串长度满足 1 \le n \le 100 \1≤n≤100
 * 输入描述：
 * 一组字符串。
 * <p>
 * 输出描述：
 * 如果符合要求输出：OK，否则输出NG
 * <p>
 * <p>
 * <p>
 * 输入：
 * 021Abc9000
 * 021Abc9Abc1
 * 021ABC9000
 * 021$bc9000
 * 复制
 * 输出：
 * OK
 * NG
 * NG
 * OK
 */
public class ChechPassword {
    public static void main(String[] arg) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.next();
            if (str.length() <= 8) {
                System.out.println("NG");
                continue;
            }
            if (getMatch(str)) {
                System.out.println("NG");
                continue;
            }
            if (getString(str, 0, 3)) {
                System.out.println("NG");
                continue;
            }
            System.out.println("OK");
        }
    }

    // 校验是否有重复子串
    private static boolean getString(String str, int l, int r) {
        if (r >= str.length()) {
            return false;
        }
        if (str.substring(r).contains(str.substring(l, r))) {
            return true;
        } else {
            return getString(str, l + 1, r + 1);
        }
    }

    // 检查是否满足正则
    private static boolean getMatch(String str) {
        int count = 0;
        Pattern p1 = Pattern.compile("[A-Z]");
        if (p1.matcher(str).find()) {
            count++;
        }
        Pattern p2 = Pattern.compile("[a-z]");
        if (p2.matcher(str).find()) {
            count++;
        }
        Pattern p3 = Pattern.compile("[0-9]");
        if (p3.matcher(str).find()) {
            count++;
        }
        Pattern p4 = Pattern.compile("[^a-zA-Z0-9]");
        if (p4.matcher(str).find()) {
            count++;
        }
        if (count >= 3) {
            return false;
        } else {
            return true;
        }
    }
}
