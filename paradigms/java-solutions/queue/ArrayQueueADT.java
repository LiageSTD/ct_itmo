package queue;

import java.util.Objects;

public class ArrayQueueADT {
// MODEL : a[1] ... a[n];
// INVAR: for i  [1, n] : a[i] != 0 && n >= 0
// imm(n) : for i in [1,n] a[i] = a'[i]

    private Object[] arr = new Object[10];
    private int start = 0;
    private int size = 0;

//    PRE: el != null;
//    POST: n' = n + 1 && imm(n') && arr[n] = l
    public static void enqueue(ArrayQueueADT q, Object n) {
        Objects.requireNonNull(n);
        if (end(q) == q.start && q.size > 0) {
            Object[] arrN = new Object[q.arr.length * 2];
            enC(q,q.arr,arrN);
            q.arr = arrN;
            q.start = 0;
        }
        q.arr[end(q)] = n;
        q.size++;
    }
//    PRE: n > 0
//    POST: n' = n + 1 && imm(n') && res = arr[n]
    public static Object dequeue(ArrayQueueADT q) {
        Object var = q.arr[q.start];
        q.start++;
        q.start = mod(q.start, q.arr.length);
        q.size--;
        return var;
    }
//    PRE: n>0
//    POST: res = arr[n] && imm(n)
    public static Object element(ArrayQueueADT q) {
        return q.arr[q.start];
    }
//  PRE: True
//  POST: res = n && imm(n)
    public static int size(ArrayQueueADT q) {
        return q.size;
    }
//  PRE: queue - exists
//  POST: res = n == 0 && imm(n)
    public static boolean isEmpty(ArrayQueueADT q) {
        return size(q) == 0;
    }
// PRE: True
// POST: n = 0
    public static void clear(ArrayQueueADT q) {
        q.start = 0;
        q.size = 0;
    }

    private static int mod(int x, int n) {
        if (x > 0) {
            while (x >= n) {
                x -= n;
            }
        } else {
            while (x < 0) {
                x += n;
            }
        }
        return x;
    }
//   PRE: True
//   POST: res = arr[] && imm(n)
    public static Object[] toArray(ArrayQueueADT q) {
        Object[] res = new Object[q.size];
        if (q.size == 0) {
            return res;
        }
        enC(q,q.arr,res);
        return res;
    }

    private static int end(ArrayQueueADT q) {
        return mod(q.start + size(q), q.arr.length);
    }

    private static void enC(ArrayQueueADT q, Object[] arr, Object[] arrN) {
        if (q.start > mod(end(q) - 1, arr.length)) {
            System.arraycopy(arr,q.start,arrN, 0,q.arr.length - q.start);
            System.arraycopy(arr,0,arrN, q.arr.length - q.start,end(q));
        } else {
            System.arraycopy(arr,q.start,arrN, 0,q.size);
        }
    }
}
