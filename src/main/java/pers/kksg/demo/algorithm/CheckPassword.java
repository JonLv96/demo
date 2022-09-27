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
public class CheckPassword {
    private static Pattern upperCase = Pattern.compile("[A-Z]");
    private static Pattern lowerCase = Pattern.compile("[a-z]");
    private static Pattern numCase = Pattern.compile("[0-9]");
    private static Pattern otherCase = Pattern.compile("[^0-9a-zA-Z]");


    public static void main(String[] arg) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String password = scanner.next();
            //长度超过八位
            if (password.length() <= 8) {
                System.out.println("NG");
                continue;
            }
            //包括大小写字母.数字.其它符号,以上四种至少三种
            if (!isSafe(password)) {
                System.out.println("NG");
                continue;
            }
            //不包含长度大于二的公共子串
            if (containLongerThan2Str(password)) {
                System.out.println("NG");
                continue;
            }
            System.out.println("OK");
        }
    }

    private static boolean containLongerThan2Str(String password) {
        for (int i = 0; i < password.toCharArray().length -2; i++) {
            if (password.substring(i+1).contains(password.substring(i, i + 3))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSafe(String password) {
        int count = 0;
        if (upperCase.matcher(password).find()) {
            count++;
        }
        if (lowerCase.matcher(password).find()) {
            count++;
        }
        if (numCase.matcher(password).find()) {
            count++;
        }
        if (otherCase.matcher(password).find()) {
            count++;
        }
        if (count >= 3) {
            return true;
        }
        return false;
    }

}
