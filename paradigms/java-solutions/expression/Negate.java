package expression;

public class Negate extends FuncsU {
    @Override
    protected int getRes(int a) {
        return -a;
    }

    public Negate(NewEx a) {
        super(a);
    }
    public String getChar() {
        return "-";
    }

    @Override
    public String toString() {
        return "-" + "(" + a.toString() + ")";
    }

}
