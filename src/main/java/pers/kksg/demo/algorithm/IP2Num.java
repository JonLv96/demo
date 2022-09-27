package pers.kksg.demo.algorithm;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/27/10:53
 * @Description: 原理：ip地址的每段可以看成是一个0-255的整数，把每段拆分成一个二进制形式组合起来，然后把这个二进制数转变成
 * 一个长整数。
 * 举例：一个ip地址为10.0.3.193
 * 每段数字             相对应的二进制数
 * 10                   00001010
 * 0                    00000000
 * 3                    00000011
 * 193                  11000001
 * <p>
 * 组合起来即为：00001010 00000000 00000011 11000001,转换为10进制数就是：167773121，即该IP地址转换后的数字就是它了。
 * <p>
 * 数据范围：保证输入的是合法的 IP 序列
 * <p>
 * 输入描述：
 * 输入
 * 1 输入IP地址
 * 2 输入10进制型的IP地址
 * <p>
 * 输出描述：
 * 输出
 * 1 输出转换成10进制的IP地址
 * 2 输出转换后的IP地址
 * <p>
 * <p>
 * <p>
 * 输入：
 * 10.0.3.193
 * 167969729
 * 输出：
 * 167773121
 * 10.3.3.193
 */
public class IP2Num {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            //IP --> Integer
            if (str.contains(".")) {
                String[] strs = str.split("\\.");
                Long result = 0L;
                for (String s : strs) {
                    result = Integer.parseInt(s) + result * 256;
                }
                System.out.println(result);
            } else {
                // Integer --> IP
                String result = "";
                long num = Long.parseLong(str);
                for (int i = 0; i < 4; i++) {
                    result = num % 256 + "." + result;
                    num /= 256;
                }
                System.out.println(result.substring(0, result.length() - 1));
            }
        }
    }

}
