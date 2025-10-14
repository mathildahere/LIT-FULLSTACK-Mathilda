package session6;

public class ListNode {
    int value;
    ListNode next;


    public ListNode(int value) {
        this.value = value;
        this.next = null;
    }

    public ListNode(int value, ListNode next) {
        this.value = value;
        this.next = next;
    }

    public static int count(ListNode head) {
        int counter = 0;
        ListNode current = head;
        while (current != null) {
            counter++;
            current = current.next;
        }
        return counter;
    }

    public static int countRec(ListNode head) {
        if (head == null) {
            return 0;
        }
        return 1 + countRec(head.next);
    }
}
