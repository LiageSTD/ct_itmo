package expression.exceptions;

import expression.FuncsU;
import expression.NewEx;

public class Count extends FuncsU {
    @Override
    protected int getRes(int a) {
        return Integer.bitCount(a);
    }

    public Count(NewEx a) {
        super(a);
    }


    @Override
    public String getChar() {
        return "count";

    }
}
