package pers.kksg.demo.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/19/22:50
 * @Description:
 */
public class JumpStep {

    public static final Map<Integer,Integer> map = new HashMap<>();



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int step = scanner.nextInt();
        int result = jump(step);
        System.out.println(result);
    }

    private static int jump(int step) {
        if (step == 1) {
            return 1;
        }
        if (step == 2) {
            return 2;
        }
        int step1 = step - 1;
        int step2 = step - 2;

        int result1 = Optional.ofNullable(map.get(step1)).orElse(jump(step1));
        int result2 = Optional.ofNullable(map.get(step2)).orElse(jump(step2));
        return result1 + result2;
    }

}
