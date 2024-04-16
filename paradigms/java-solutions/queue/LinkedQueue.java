package queue;

public class LinkedQueue extends AbstractQueue {
    private Node first;
    private Node last;

    @Override
    protected void dequeueImpl() {
        first = first.next;
    }

    @Override
    protected void clearImpl() {
        first = null;
        last = null;
    }

    @Override
    protected void enqueueImpl(Object a) {
        if (isEmpty()) {
            last = new Node(a, last);
            first = last;
        } else {
            last.next = new Node(a, last);
            last = last.next;
        }
    }

    @Override
    protected Object elementImpl() {
        return first.element;
    }


    private static class Node {

        private final Object element;
        private Node next;

        private Node(Object element, Node next) {
            this.element = element;
            this.next = next;
        }
    }

}
