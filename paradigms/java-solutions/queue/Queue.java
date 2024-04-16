package queue;

// MODEL : arr[1] ... arr[n];

// INVAR: for i  [1, n] : a[i] != 0 && n >= 0
interface Queue {
    // imm(n) : for i in [1,n] a[i] = a'[i]
    int size = 0;

    //    PRE: el != null;
//    POST: n' = n + 1 && imm(n') && arr[n] = el
    void enqueue(Object a);

    //    PRE: n > 0
//    POST: n' = n - 1 && imm(n') && res = arr[0]
    Object dequeue();

    //    PRE: n>0
//    POST: res = arr[n] && imm(n)
    Object element();

    //  PRE: True
//  POST: res = n && imm(n)
    int size();

    //  PRE: queue - exists
//  POST: res = n == 0 && imm(n)
    boolean isEmpty();

    // PRE: True
// POST: n = 0
    void clear();

    //    PRE: TRUE
//    POST: imm(n) && (res = firstIndexOf || res = -1)
    int indexOf(Object a);

    //    PRE: TRUE
//    POST: imm(n) && (res = lastIndexOf || res = -1)
    int lastIndexOf(Object a);
}


