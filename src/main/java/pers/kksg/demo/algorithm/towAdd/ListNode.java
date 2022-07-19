package pers.kksg.demo.algorithm.towAdd;

import lombok.val;

/**
 * ListNode
 *
 * @Author Jonlv
 * @Description TODO
 * @Date 2022/7/8 10:24
 */
public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ListNode{");
        builder.append(val);
        ListNode process = next;
        while (process != null) {
            builder.append(",");
            builder.append(process.val);
            process = process.next;
        }
        builder.append("}");
        return builder.toString();
    }
}
