package queue;

public class ArrayQueue extends AbstractQueue {
    private Object[] arr = new Object[16];
    private int first = 0;

    @Override
    protected void dequeueImpl() {
        arr[first] = null;
        first++;
        first = first % arr.length;
    }

    @Override
    protected void clearImpl() {
        first = 0;
    }

    @Override
    protected void enqueueImpl(Object a) {
        if (end() == first && size > 0) {
            Object[] arrN = new Object[arr.length * 2];
            enC(arr, arrN);
            arr = arrN;
            first = 0;
        }
        arr[end()] = a;
    }


    @Override
    protected Object elementImpl() {
        return arr[first];
    }

    private int end() {
        return (first + size()) % arr.length;
    }

    private void enC(Object[] arr, Object[] arrN) {
        if (first > (end() - 1 + arr.length) % arr.length) {
            System.arraycopy(arr, first, arrN, 0, arr.length - first);
            System.arraycopy(arr, 0, arrN, arr.length - first, end());
        } else {
            System.arraycopy(arr, first, arrN, 0, size);
        }
    }
    public Object[] toArray() {
        Object[] res = new Object[size];
        if (size == 0) {
            return res;
        }
        enC(arr, res);
        return res;
    }


}
