package expression;

public class Subtract extends Funcs {
    @Override
    protected int getRes(int a, int b) {
        return a - b;
    }

    public Subtract(NewEx a, NewEx b) {
        super(a,b);
    }

    public String getChar() {
        return "-";
    }

}
