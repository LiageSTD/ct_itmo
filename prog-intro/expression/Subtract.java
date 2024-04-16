package expression;

public class Subtract extends Funcs {
    public Subtract(NewEx a, NewEx b) {
        super(a,b);
    }
    public String getChar() {
        return "-";
    }

    @Override
    public int evaluate(int x) {
        return a.evaluate(x) - b.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return a.evaluate(x,y,z) - b.evaluate(x,y,z);
    }

}
