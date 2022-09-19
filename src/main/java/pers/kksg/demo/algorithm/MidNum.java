package pers.kksg.demo.algorithm;

/**
 * MidNum
 *
 * @Author Jonlv
 * @Description 查找中位数
 * @Date 2022/7/19 11:48
 * <p>
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 * <p>
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 * 示例 2：
 * <p>
 * 输入：nums1 = [1,2], nums2 = [3,4]
 * 输出：2.50000
 * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
 */
public class MidNum {

    /**
     * 低效
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArraysTest(int[] nums1, int[] nums2) {
        if (nums1.length == 0) {
            return nums2.length % 2 == 0 ? (nums2[nums2.length / 2] + nums2[nums2.length / 2 - 1]) / 2.0 : nums2[nums2.length / 2];
        }
        if (nums2.length == 0) {
            return nums1.length % 2 == 0 ? (nums1[nums1.length / 2] + nums1[nums1.length / 2 - 1]) / 2.0 : nums1[nums1.length / 2];
        }
        if (nums2.length == 1 && nums1.length == 1) {
            return (nums1[0] + nums2[0]) / 2.0;
        }

        int nums1Index = 0, nums2Index = 0;
        int totalLen = nums1.length + nums2.length;
        int[] sortArr = new int[totalLen / 2 + 1];
        boolean odd = totalLen % 2 == 0;

        for (int i = 0; i < totalLen; i++) {
            if (odd && i == totalLen / 2 + 1) {
                return (sortArr[i - 1] + sortArr[i - 2]) / 2.0;
            }
            if (!odd && i == totalLen / 2 + 1) {
                return sortArr[i - 1];
            }


            if (nums1Index == nums1.length) {
                sortArr[i] = nums2[nums2Index++];
                continue;
            }
            if (nums2Index == nums2.length) {
                sortArr[i] = nums1[nums1Index++];
                continue;
            }

            if (nums1[nums1Index] >= nums2[nums2Index]) {
                sortArr[i] = nums2[nums2Index++];
            } else {
                sortArr[i] = nums1[nums1Index++];
            }
        }
        return 0D;
    }

    /**
     * 直接取中间值
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int toleLen = nums1.length + nums2.length;
        int num1Index = 0, num2Index = 0;
        int midNum1 = 0, midNum2 = 0;
        for (int i = 0; i <= toleLen / 2; i++) {
            midNum2 = midNum1;
            if (num1Index < nums1.length && (num2Index >= nums2.length || nums1[num1Index] < nums2[num2Index])) {
                midNum1 = nums1[num1Index++];
            } else {
                midNum1 = nums2[num2Index++];
            }
        }
        return toleLen % 2 == 0 ? ((midNum1 + midNum2) / 2.0) : midNum1;
    }


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int left = (n + m + 1) / 2;
        int right = (n + m + 2) / 2;
        //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k 。
        return (getKth(nums1, 0, n - 1, nums2, 0, m - 1, left) + getKth(nums1, 0, n - 1, nums2, 0, m - 1, right)) * 0.5;
    }

    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        //当 nums1  或者 nums2 空了
        if (len1 == 0) {
            return nums2[start2 + k - 1];
        }
        if (len2 == 0) {
            return nums1[start1 + k - 1];
        }

        //若k值为1， 则取小
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }

        //当
        int num1Index = Math.min((start1 + k / 2 - 1), end1);
        int num2Index = Math.min((start2 + k / 2 - 1), end2);

        if (nums1[num1Index] > nums2[num2Index]) {
            return getKth(nums1,start1,end1,nums2,num2Index + 1,end2,k -(num2Index -start2 + 1));
        }else {
            return getKth(nums1,num1Index + 1,end1,nums2,start2,end2,k -(num1Index -start1 + 1));
        }
    }


}
