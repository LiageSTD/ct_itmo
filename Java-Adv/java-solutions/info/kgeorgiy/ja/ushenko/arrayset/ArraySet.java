package info.kgeorgiy.ja.ushenko.arrayset;

import java.util.*;

public class ArraySet<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {
    private final List<T> dataSource;
    private final Comparator<? super T> comparator;

    public ArraySet(Collection<T> data, Comparator<? super T> comparator) {
        this.comparator = comparator;
        TreeSet<T> temp = new TreeSet<>(comparator);
        temp.addAll(data);
        dataSource = new ArrayList<>(temp);
    }

    public ArraySet() {
        dataSource = new ArrayList<T>();
        comparator = null;
    }
    public ArraySet(Comparator<? super T> comparator) {
        dataSource = new ArrayList<>();
        this.comparator = comparator;
    }

    public ArraySet(Collection<T> data) {
        TreeSet<T> temp = new TreeSet<T>(data);
        dataSource = new ArrayList<T>(temp);
        comparator = null;
    }

    // :NOTE: ArraySet(Comparator)

    @Override
    public Iterator<T> iterator() {
        return dataSource.iterator();
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        if (comparator != null && comparator.compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException();
        } else if (comparator == null && fromElement.compareTo(toElement) > 0) {
            throw  new IllegalArgumentException();
        }
        return new ArraySet<>(dataSource.subList(indexOf(fromElement), indexOf(toElement)), comparator);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return retSet(0, indexOf(toElement));
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return retSet(indexOf(fromElement), size());
    }

    private SortedSet<T> retSet(int left, int right) {
        return new ArraySet<>(dataSource.subList(left, right), comparator);
    }

    @Override
    public T first() {
        if (size() == 0) throw new NoSuchElementException();
        return dataSource.getFirst();
    }

    @Override
    public T last() {
        if (size() == 0) throw new NoSuchElementException();
        return dataSource.getLast();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object object) {
        return Collections.binarySearch(dataSource, (T) object, comparator) >= 0;
    }

    private int indexOf(T object) {
        int index = Collections.binarySearch(dataSource, object, comparator);
        return index >= 0 ? index : -index - 1;
    }
}
