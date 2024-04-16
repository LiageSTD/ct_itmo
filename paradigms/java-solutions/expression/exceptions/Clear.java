package expression.exceptions;

import expression.Funcs;
import expression.NewEx;

public class Clear extends Funcs {
    @Override
    protected int getRes(int a, int b) {
        return a & ~(1 << b);
    }

    public Clear(NewEx a, NewEx b) {
        super(a, b);
    }


    public String getChar() {
        return "clear";
    }

}
