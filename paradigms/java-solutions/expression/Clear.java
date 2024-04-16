package expression;

public class Clear extends Funcs{
    public Clear(NewEx a, NewEx b) {
        super(a, b);
    }

    @Override
    public String getChar() {
        return "clear";
    }
    @Override
    public int evaluate(int x, int y, int z) {
        return a.evaluate(x,y,z) & ~(1 << b.evaluate(x,y,z));
    }
}
