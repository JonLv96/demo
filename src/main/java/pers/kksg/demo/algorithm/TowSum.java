package pers.kksg.demo.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那 两个 整数，并返回它们的数组下标。
//
// 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
//
// 你可以按任意顺序返回答案。
//
//
//
// 示例 1：
//
//
//输入：nums = [2,7,11,15], target = 9
//输出：[0,1]
//解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
//
//
// 示例 2：
//
//
//输入：nums = [3,2,4], target = 6
//输出：[1,2]
//
//
// 示例 3：
//
//
//输入：nums = [3,3], target = 6
//输出：[0,1]
//
//
//
//
// 提示：
//
//
// 2 <= nums.length <= 10⁴
// -10⁹ <= nums[i] <= 10⁹
// -10⁹ <= target <= 10⁹
// 只会存在一个有效答案
//
//
// 进阶：你可以想出一个时间复杂度小于 O(n²) 的算法吗？
//
// Related Topics 数组 哈希表 👍 14422 👎 0


/**
 * Solution
 *
 * @Author Jonlv
 * @Description 两数之和
 * @Date 2022/5/18 18:07
 */
public class TowSum {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int arrLen =  in.nextInt();
        int arr[] = new int[arrLen];
        for (int i = 0; i < arrLen; i++) {
            arr[i] = in.nextInt();
        }
        int sort =  in.nextInt();
        for (int i =  0; i < arrLen - 1; i++) {
            for (int j = i + 1; j < arrLen; j++) {
                if (sort == 0) {
                    if (arr[i] > arr[j]) {
                        int temp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp;
                    }
                } else {
                    if (arr[i] < arr[j]) {
                        int temp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp;
                    }
                }
            }

        }
        StringBuffer sb =  new StringBuffer();
        for(int k = 0;k<arrLen;k++){
            sb.append(arr[k]).append(" ");
        }
        System.out.println(sb.substring(0,sb.length()-1));

    }


    //暴力枚举
    public int[] twoSum1(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }
    //hash表
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int tempNum = target - nums[i];
            if (null != map.get(tempNum) && i != map.get(tempNum)) {
                return new int[]{i, map.get(tempNum)};
            }


        }
        return new int[]{};
    }
    //hash表 优化，在put值的同时比较是否有符合条件的值
    public int[] twoSum3(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int tempNum = target - nums[i];
            if (null != map.get(tempNum) && i != map.get(tempNum)) {
                return new int[]{i, map.get(tempNum)};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }
    //hash表 优化，两头齐头并进put
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i <= nums.length / 2; i++) {
            int leftNum = target - nums[i];
            if (null != map.get(leftNum) && i != map.get(leftNum)) {
                return new int[]{i, map.get(leftNum)};
            }
            map.put(nums[i], i);
            int rightNum = target - nums[nums.length - i -1];
            if (null != map.get(rightNum) && (nums.length - i -1) != map.get(rightNum)) {
                return new int[]{nums.length - i -1, map.get(rightNum)};
            }
            map.put(nums[nums.length - i -1], nums.length - i -1);

        }
        return new int[]{};
    }
}
