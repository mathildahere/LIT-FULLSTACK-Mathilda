package session6;

public class CountTest {
    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3)));
        System.out.println(ListNode.count(head));      // Should print 3
        System.out.println(ListNode.countRec(head));   // Should print 3
        System.out.println(ListNode.count(null));      // Should print 0
        System.out.println(ListNode.countRec(null));   // Should print 0
    }
}
