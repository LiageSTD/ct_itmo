package expression.exceptions;

import expression.*;

public class CheckedNegate extends FuncsU {
    @Override
    protected int getRes(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowEx("overflow at: " + "-" + a);
        }
        return -a;
    }

    public CheckedNegate(NewEx a) {
        super(a);
    }

    public String getChar() {
        return "-";
    }


}
