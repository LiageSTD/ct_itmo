package expression;

public class Add extends Funcs {
    public Add(NewEx a, NewEx b) {
        super(a,b);
    }
    @Override
    public String getChar() {
        return "+";
    }

    @Override
    public int evaluate(int x) {
        return a.evaluate(x) + b.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return a.evaluate(x, y, z) + b.evaluate(x, y, z);
    }
}
