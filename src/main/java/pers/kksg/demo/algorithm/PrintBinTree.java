package pers.kksg.demo.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/10/06/23:34
 * @Description: 从上到下打印二叉树 II
 * <p>
 * 给定二叉树: [3,9,20,null,null,15,7],
 *  3
 * / \
 * 9  20
 *   /  \
 *  15   7
 * <p>
 * <p>
 * 返回其层次遍历结果：
 * [
 * [3],
 * [9,20],
 * [15,7]
 * ]
 */
public class PrintBinTree {

    public static void main(String[] args) {
        TreeNode node = new TreeNode(3);
        TreeNode node1 = new TreeNode(9);
        node.left = node1;
        TreeNode node2 = new TreeNode(20);
        node.right = node2;
        TreeNode node3 = new TreeNode(15);
        node2.left = node3;
        TreeNode node4 = new TreeNode(7);
        node2.right = node4;
        levelOrder(node);

    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> lists = new ArrayList<>();
        if (root == null) {
            return lists;
        }
         Queue<TreeNode> nodes = new LinkedList<>();
        nodes.add(root);
        int length = nodes.size();
        while (!nodes.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            for (int i = nodes.size(); i > 0; i--) {
                TreeNode node = nodes.poll();
                if (node != null) {
                    list.add(node.val);
                }
                //奇数从右往做左，偶数从左边往右
                if (length % 2 == 0) {

                    if (node.left != null) {
                        nodes.add(node.left);
                    }
                    if (node.right != null) {
                        nodes.add(node.right);
                    }
                }else {
                    if (node.right != null) {
                        nodes.add(node.right);
                    }
                    if (node.left != null) {
                        nodes.add(node.left);
                    }

                }

            }
            length = nodes.size();
            lists.add(list);
        }
        return lists;
    }
}
