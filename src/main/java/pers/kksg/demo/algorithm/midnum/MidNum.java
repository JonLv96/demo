package pers.kksg.demo.algorithm.midnum;

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

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
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
        int[] sortArr = new int[totalLen/2 + 1];
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

}
