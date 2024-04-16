package expression;

public class Set extends Funcs {

    public Set(NewEx a, NewEx b) {
        super(a, b);
    }

    @Override
    public String getChar() {
        return "set";
    }
    @Override
    public int evaluate(int x, int y, int z) {
        return a.evaluate(x,y,z) | (1 << b.evaluate(x,y,z));
    }
}
