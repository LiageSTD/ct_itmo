package expression;

public interface NewEx extends Expression, TripleExpression {
    int evaluate(int x, int y, int z);
    boolean equals(Object a);
    String toString();
}
