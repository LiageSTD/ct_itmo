package queue;

import java.util.Arrays;

public class ArrayQueueAdtTestByMySelf {
    public static void Test() {
        ArrayQueueADT q1 = new ArrayQueueADT();
        ArrayQueueADT q2 = new ArrayQueueADT();
        for (int i = 0; i < 20; i++) {
            ArrayQueueADT.enqueue(q1,i + "el for 1");
        }
        for (int i = 0; i < 11; i++) {
            System.out.println(ArrayQueueADT.size(q1) + " " + ArrayQueueADT.dequeue(q1));
            System.out.println(Arrays.toString(ArrayQueueADT.toArray(q1)));
        }
        for (int i = 20; i < 30; i++) {
            ArrayQueueADT.enqueue(q1,i + "el for 1 added");
        }
        while (!ArrayQueueADT.isEmpty(q1)) {
            System.out.println(ArrayQueueADT.size(q1) + " _ " + ArrayQueueADT.dequeue(q1));
            System.out.println(Arrays.toString(ArrayQueueADT.toArray(q1)));
        }
        System.out.println("____________________________________________________");
        for (int i = 0; i < 20; i++) {
            ArrayQueueADT.enqueue(q2,i + "el for 2");
        }
        for (int i = 0; i < 11; i++) {
            System.out.println(ArrayQueueADT.size(q2) + "__" + ArrayQueueADT.dequeue(q2));
            System.out.println(Arrays.toString(ArrayQueueADT.toArray(q2)));
        }
        for (int i = 20; i < 30; i++) {
            ArrayQueueADT.enqueue(q2,i + "el for 2 added");
        }
        while (!ArrayQueueADT.isEmpty(q2)) {
            System.out.println(ArrayQueueADT.size(q2) + "__" + ArrayQueueADT.dequeue(q2));
            System.out.println(Arrays.toString(ArrayQueueADT.toArray(q2)));
        }
    }
}
