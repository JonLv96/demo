//package pers.kksg.demo.algorithm.sort;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @Author: lvqiang
// * @Date: 2022/09/17/23:38
// * @Description:
// */
//public class Test {
//    public static void main(String[] args) {
//        Map<String, Integer> ascToNumMap = new HashMap<>();
//        Map<Integer, String> numToAscMap = new HashMap<>();
//
//        // 数字 0-9
//        for (int i = 0; i <= 9; i++) {
//            ascToNumMap.put(String.valueOf(i), 48 + i);
//            numToAscMap.put(48 + i, String.valueOf(i));
//        }
//
//        // 大写字母 A-J
//        for (char c = 'A'; c <= 'Z'; c++) {
//            ascToNumMap.put(String.valueOf(c), (int) c);
//            numToAscMap.put((int) c, String.valueOf(c));
//        }
//
//        // 小写字母 a-j
//        for (char c = 'a'; c <= 'z'; c++) {
//            ascToNumMap.put(String.valueOf(c), (int) c);
//            numToAscMap.put((int) c, String.valueOf(c));
//        }
//
//        // 创建Scanner对象读取输入
//        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine(); // 读取一行输入字符串
////        scanner.close(); // 关闭Scanner（可选，但推荐）
//        char[] ascArr = input.toCharArray();
//        int[] numArr = transferAscToNum(ascArr, ascToNumMap);
//        int[] sortedNumArr = sortArr(numArr);
//        String sortedAscStr = transferNumToAsc(sortedNumArr, numToAscMap);
//
//        // 输出结果字符串
//        System.out.println(sortedAscStr);
//    }
//
//    /**
//     * 数组排序
//     */
//    public static int[] sortArr(int[] arr) {
//        for (int i = 0; i < arr.length - 1; i++) {
//            for (int j = i + 1; j < arr.length; j++) {
//                if (arr[i] > arr[j]) {
//                    int temp = arr[i];
//                    arr[i] = arr[j];
//                    arr[j] = temp;
//                }
//            }
//        }
//        return arr;
//    }
//
//
//    /**
//     * 将 char 转为对应的 asc值
//     */
//    public static int[] transferAscToNum(char[] chars, Map<String, Integer> ascToNumMap) {
//        int[] res = new int[chars.length];
//
//        for (int i = 0; i < chars.length; i++) {
//            res[i] = ascToNumMap.get(String.valueOf(chars[i]));
//        }
//        return res;
//    }
//
//    /**
//     * 将 char 转为对应的 asc值
//     */
//    public static String transferNumToAsc(int[] arrs,
//                                          Map<Integer, String> numToAscMap) {
//
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < arrs.length; i++) {
//            sb.append(numToAscMap.get(arrs[i]));
//
//        }
//        return sb.toString();
//    }
//
//
//
//}
