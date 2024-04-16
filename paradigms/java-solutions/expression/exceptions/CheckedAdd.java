package expression.exceptions;

import expression.*;

public class CheckedAdd extends Funcs {
    @Override
    protected int getRes(int a, int b) {
        if (b > 0 && a > Integer.MAX_VALUE - b) {
            throw new OverflowEx("overflow at: " + a + " + " + b);
        } else if (a < 0 && b < Integer.MIN_VALUE - a) {
            throw new OverflowEx("overflow at: " + a + " + " + b);
        }
        return a + b;
    }

    public CheckedAdd(NewEx a, NewEx b) {
        super(a, b);
    }

    @Override
    public String getChar() {
        return "+";
    }


}
