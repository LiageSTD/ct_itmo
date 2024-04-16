package expression;

public class Add extends Funcs {
    @Override
    protected int getRes(int a, int b) {
        return a + b;
    }

    public Add(NewEx a, NewEx b) {
        super(a,b);
    }
    @Override
    public String getChar() {
        return "+";
    }


}
