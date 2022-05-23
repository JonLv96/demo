package pers.kksg.demo.algorithm.reverseLinkedList;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/31/0:28
 * @Description: 反转链表
 * <p>
 * 假设链表为 1——》2——》3-》null
 * <p>
 * 现在改为null——》3——》2——》1
 */
public class ReverseLinkedList {

    static class ListNode {
        public ListNode(Integer data, ListNode next) {
            this.data = data;
            this.next = next;
        }

        private Integer data;
        private ListNode next;


    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, null))));
        System.out.println(toStr(head));
//        ListNode reverseList = reverseList(head);
//        System.out.println(toStr(reverseList));
        ListNode reverseEvenList = reverseEvenList(head);
        System.out.println(toStr(reverseEvenList));

    }

    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public static ListNode reverseEvenList(ListNode head) {
        if (null == head || head.next == null) {
            return head;
        }
        int count = 1;
        ListNode evenNode = null;
        ListNode curr = head;
        while (curr.next != null) {
            count++;
            if (count % 2 == 0) {
                if (evenNode == null) {
                    evenNode = curr.next;
                } else {
                    evenNode.next = curr.next;
                }
            }
            curr = curr.next;
        }
        ListNode reverseEvenNode = reverseList(evenNode);

        count = 1;
        ListNode curr2 = head;
        while (curr2.next != null) {
            count++;
            if (count % 2 == 0) {
                ListNode temp = curr2.next;
                curr2.next = reverseEvenNode;
                reverseEvenNode.next = temp.next;
                reverseEvenNode = reverseEvenNode.next;
            }
        }
        return head;
    }


    public static String toStr(ListNode node) {
        StringBuffer sb = new StringBuffer();
        while (node != null) {
            sb.append(node.data);
            if (node.next != null) {
                sb.append("-->");
            }
            node = node.next;
        }
        sb.append("-->null");
        return sb.toString();
    }



}
