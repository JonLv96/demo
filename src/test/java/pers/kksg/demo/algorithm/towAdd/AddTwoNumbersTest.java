package pers.kksg.demo.algorithm.towAdd;

import org.junit.jupiter.api.Test;

class AddTwoNumbersTest {

    @Test
    void addTwoNumbers() {
        ListNode node1 = new ListNode(9);
        ListNode node2 = new ListNode(9);
        ListNode node3 = new ListNode(9);
        ListNode node4 = new ListNode(9);
        ListNode node5 = new ListNode(9);
        ListNode node6 = new ListNode(9);
        ListNode node7 = new ListNode(9);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        System.out.println(node1);


        ListNode next1 = new ListNode(9);
        ListNode next2 = new ListNode(9);
        ListNode next3 = new ListNode(9);
        ListNode next4 = new ListNode(9);
        next1.next = next2;
        next2.next = next3;
        next3.next = next4;
        System.out.println(next1);

        AddTwoNumbers numbers = new AddTwoNumbers();
        ListNode listNode = numbers.addTwoNumbers(node1, next1);
        System.out.println(listNode.toString());

    }
}