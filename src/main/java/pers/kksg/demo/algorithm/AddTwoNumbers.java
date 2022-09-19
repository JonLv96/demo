package pers.kksg.demo.algorithm;

/**
 * Solution
 *
 * @Author Jonlv
 * @Description Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * @Date 2022/7/7 20:01
 */
public class AddTwoNumbers {
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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int incr = 0;
        ListNode reuslt = new ListNode();
        ListNode process = reuslt;
        while (l1 != null || l2 != null) {
            int add1 = l1 == null ? 0 : l1.val;
            int add2 = l2 == null ? 0 : l2.val;
            //计算结果
            process.val = (add1 + add2 + incr) % 10 ;
            //若两数之和大于9，则需进位
            incr = add1 + add2 + incr > 9 ? 1 : 0;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
            //截至临界条件
            if (l1 == null && l2 == null) {
                if (incr == 1) {
                    process.next = new ListNode(1);
                    break;
                }
                break;
            }
            process.next = new ListNode();
            process = process.next;
        }
        return reuslt;
    }
}
