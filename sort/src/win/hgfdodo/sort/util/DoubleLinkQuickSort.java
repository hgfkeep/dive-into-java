package win.hgfdodo.sort.util;

import java.util.Random;

public class DoubleLinkQuickSort {

    public static void doublelinkQuickSort(Node<Integer> low, Node<Integer> high) {
        if (low == null || high == null || low == high) {
            return;
        }

        Node<Integer> left = low;
        Node<Integer> right = high;

        int pivot = left.value;
        while (left != right) {
            //此处必须跳过相等的，不然会出现死循环
            while (left != right && right.value >= pivot)
                right = right.pre;
            left.value = right.value;
            //此处必须跳过相等的，不然会出现死循环
            while (left != right && left.value <= pivot)
                left = left.next;
            right.value = left.value;
        }
        left.value = pivot;
        if (low != left) doublelinkQuickSort(low, left.pre);
        if (left != high) doublelinkQuickSort(left.next, high);
    }

    public static void main(String[] args) {

        for (int i = 4; i < 15; i++) {
            System.out.println("--------------------------");
            System.out.println("测试：" + i);
            Node a = Node.generate(i);
            Node.print(a);
            Node tail = Node.tail(a);
            System.out.println("tail：" + tail.value);
            doublelinkQuickSort(a, tail);
            Node.print(a);
            System.out.println("--------------------------");
        }
    }
}


class Node<T> {
    T value;
    Node<T> next;
    Node<T> pre;

    public static Node generate(int n) {
        int seed = 100;
        Random random = new Random();
        Node head = new Node();
        head.value = random.nextInt(seed);
        head.next = null;
        head.pre = null;
        Node last = head;
        for (int i = 1; i < n; i++) {
            Node nn = new Node();
            nn.value = random.nextInt(seed);
            nn.pre = last;
            nn.next = null;
            last.next = nn;

            last = nn;
        }

        return head;
    }

    public static <T> Node<T> toNode(T[] a) {
        Node res = null;
        if (a.length > 0) {
            Node last = null;
            for (T t : a) {
                Node<T> tmp = new Node<>();
                tmp.value = t;
                tmp.next = null;
                if (last == null) {
                    tmp.pre = null;
                    res = tmp;
                } else {
                    tmp.pre = last;
                    last.next = tmp;
                }
                last = tmp;
            }
        }
        return res;
    }

    public static void print(Node node) {
        Node cur = node;
        while (cur != null) {
            System.out.print(cur.value + ",");
            cur = cur.next;
        }

        System.out.println();
    }

    public static Node tail(Node head) {
        Node cur = head;
        Node last = cur;
        while (cur != null) {
            last = cur;
            cur = cur.next;
        }

        return last;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Node{ value=");
        stringBuffer.append(value);
        stringBuffer.append(", ");
        stringBuffer.append("prev=");
        if (pre != null) {
            stringBuffer.append(pre.value);
        } else {
            stringBuffer.append("null");
        }
        stringBuffer.append(", ");
        stringBuffer.append("next=");
        if (next != null) {
            stringBuffer.append(next.value);
        } else {
            stringBuffer.append("null");
        }
        stringBuffer.append("}");
        return stringBuffer.toString();

    }
}