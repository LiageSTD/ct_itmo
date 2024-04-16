package queue;

import java.util.Arrays;

public class ArrayQueueModuleTestByMySelf {
    public static void Test() {
//        for (int i = 0; i < 20; i++) {
//            ArrayQueueModule.enqueue(i + "el");
//        }
//        for (int i = 0; i < 11; i++) {
//            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.dequeue());
//        }
//        for (int i = 20; i < 30; i++) {
//            ArrayQueueModule.enqueue(i + "el");
//        }
//        while (!ArrayQueueModule.isEmpty()) {
//            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.dequeue());
//        }
//        ArrayQueueADT q1 = ArrayQueueADT.create();
//        ArrayQueueADT q2 = ArrayQueueADT.create();
//        for (int i = 0; i < 20; i++) {
//            ArrayQueueADT.enqueue(q1,i + "el for 1");
//        }
//        for (int i = 0; i < 11; i++) {
//            System.out.println(ArrayQueueADT.size(q1) + " " + ArrayQueueADT.dequeue(q1));
//        }
//        for (int i = 20; i < 30; i++) {
//            ArrayQueueADT.enqueue(q1,i + "el for 1 added");
//        }
//        while (!ArrayQueueADT.isEmpty(q1)) {
//            System.out.println(ArrayQueueADT.size(q1) + " _ " + ArrayQueueADT.dequeue(q1));
//        }
//        System.out.println("____________________________________________________");
//        for (int i = 0; i < 20; i++) {
//            ArrayQueueADT.enqueue(q2,i + "el for 2");
//        }
//        for (int i = 0; i < 11; i++) {
//            System.out.println(ArrayQueueADT.size(q2) + "__" + ArrayQueueADT.dequeue(q2));
//        }
//        for (int i = 20; i < 30; i++) {
//            ArrayQueueADT.enqueue(q2,i + "el for 2 added");
//        }
//        while (!ArrayQueueADT.isEmpty(q2)) {
//            System.out.println(ArrayQueueADT.size(q2) + "__" + ArrayQueueADT.dequeue(q2));
//        }
        int[] a = new int[100];
        System.out.println(Arrays.toString(a));
        for (int i = 0; i < 100; i++) {
            ArrayQueueModule.enqueue(i);
            System.out.println(Arrays.toString(ArrayQueueModule.toArray()));
        }
        System.out.println(Arrays.toString(ArrayQueueModule.toArray()));
        for (char i = 0; i < 100; i++) {
            System.out.println( ArrayQueueModule.dequeue());

        }
        for (int i = 0; i < 100; i++) {
            ArrayQueueModule.enqueue(i);
            System.out.println(Arrays.toString(ArrayQueueModule.toArray()));
        }

    }
}
