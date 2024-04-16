package expression;

public class Count extends Funcs {
    public Count(NewEx a) {
        super(a);
    }
    @Override
    public String toString() {
        return "count(" + a.toString() + ")";
    }

    @Override
    public String getChar() {
        return "count";
    }
    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.bitCount((int) a.evaluate(x,y,z));
    }
}
