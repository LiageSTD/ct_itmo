package queue;


import java.util.Arrays;

public class LinkedQueueTestByMySelf {
    public static void Test() {
        LinkedQueue p1 = new LinkedQueue();
        LinkedQueue p2 = new LinkedQueue();
        for (int i = 0; i < 31; i++) {
            p1.enqueue("el_" + i);
            p2.enqueue("el_" + i);
        }
        for (int i = 0; i < 11; i++) {
            System.out.println(p1.dequeue());
            System.out.println(p2.dequeue());
        }
        for (int i = 0; i < 101; i++) {
            p1.enqueue("el_" + i);
            p2.enqueue("el_" + i);

        }
        while (!p1.isEmpty()) {
            System.out.println(p1.dequeue());

        }
        while (!p2.isEmpty()) {
            System.out.println(p2.dequeue());
        }
    }
}


