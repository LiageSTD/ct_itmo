package queue;

public abstract class AbstractQueue implements Queue {
    protected int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    protected abstract void dequeueImpl();

    protected abstract void clearImpl();

    protected abstract void enqueueImpl(Object a);

    public void clear() {
        size = 0;
        clearImpl();
    }

    public Object dequeue() {
        assert size > 0;
        Object el = element();
        size--;
        dequeueImpl();
        return el;
    }

    public void enqueue(Object a) {
        assert a != null;
        enqueueImpl(a);
        size++;

    }

    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    public int lastIndexOf(Object a) {
        return getRes(a,0);
    }

    public int indexOf(Object a) {
        return getRes(a, 1);
    }

    private int getRes(Object a, int logic) {
        int res = -1;
        boolean met = true;
        for (int i = 0; i < size; i++) {
            if (met && element().equals(a)) {
                res = i;
                if (logic == 1) {
                    met = false;
                }
            }
            enqueue(dequeue());
        }
        return res;
    }

}

