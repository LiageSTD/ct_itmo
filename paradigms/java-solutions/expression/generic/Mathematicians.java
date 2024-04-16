package expression.generic;

public interface Mathematicians<T> {
    abstract T Add(T a, T b);
    abstract T Divide(T a, T b);
    abstract T Subtract(T a, T b);
    abstract T Multiply(T a, T b);
    public abstract T parse(String a);
    T Negate(T a);
}
