package expression;

public class Multiply extends Funcs {
    @Override
    protected int getRes(int a, int b) {
        return a * b;
    }

    public Multiply(NewEx a, NewEx b) {
        super(a,b);
    }
    public String getChar() {
        return "*";
    }
}
