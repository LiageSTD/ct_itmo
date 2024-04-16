package expression.exceptions;

import expression.Funcs;
import expression.NewEx;

public class Set extends Funcs {

    @Override
    protected int getRes(int a, int b) {
        return a | (1 << b);
    }

    public Set(NewEx a, NewEx b) {
        super(a, b);
    }

    @Override
    public String getChar() {
        return "set";
    }

}
