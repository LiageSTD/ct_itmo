package queue;

import java.util.Objects;

public class ArrayQueueModule {
// MODEL : a[1] ... a[n];
// INVAR: for i  [1, n] : a[i] != 0 && n >= 0
// imm(n) : for i in [1,n] a[i] = a'[i]
    private static Object[] arr = new Object[16];
    private static int start = 0;
    private static int size = 0;

//    PRE: el != null;
//    POST: n' = n + 1 && imm(n') && arr[n] = l
    public static void enqueue(Object n) {
        Objects.requireNonNull(n);
        if (end() == start && size > 0) {
            Object[] arrN = new Object[arr.length * 2];
            enC(arr,arrN);
            arr = arrN;
            start = 0;
        }
        arr[end()] = n;
        size++;
    }

//    PRE: n > 0
//    POST: n' = n + 1 && imm(n') && res = arr[n]
    public static Object dequeue() {
        Object var = arr[start];
        start++;
        start = mod(start, arr.length);
        size--;
        return var;
    }

    //    PRE: n>0
//    POST: res = arr[n] && imm(n)
    public static Object element() {
        return arr[start];
    }

    //  PRE: True
//  POST: res = n && imm(n)
    public static int size() {
        return size;
    }

    //  PRE: queue - exists
//  POST: res = n == 0 && imm(n)
    public static boolean isEmpty() {
        return size == 0;
    }

    // PRE: True
// POST: n = 0
    public static void clear() {
        start = 0;
        size = 0;
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


    private static int end() {
        return mod(start + size(), arr.length);
    }

    //   PRE: True
//   POST: res = arr[] && imm(n)
    public static Object[] toArray() {
        Object[] res = new Object[size];
        if (size == 0) {
            return res;
        }
        int pos = 0;

        enC(arr, res);
        return res;
    }
    private static void enC(Object[] arr, Object[] arrN) {
        if (start > mod(end() - 1, arr.length)) {
            System.arraycopy(arr,start,arrN, 0,arr.length - start);
            System.arraycopy(arr,0,arrN, arr.length - start,end());
        } else {
            System.arraycopy(arr,start,arrN, 0,size);
        }
    }

}
