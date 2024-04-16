package expression;

public class Divide extends Funcs{
    @Override
    protected int getRes(int a, int b) {
        return a / b;
    }

    public Divide(NewEx a, NewEx b) { super(a,b);}
    public String getChar() {
        return "/";
    }

}
