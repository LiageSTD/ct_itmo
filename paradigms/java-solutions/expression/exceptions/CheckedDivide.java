package expression.exceptions;

import expression.Funcs;
import expression.NewEx;
import expression.TripleExpression;
import expression.Variable;

public class CheckedDivide extends Funcs {

    @Override
    protected int getRes(int a, int b) {
        return a / b;
    }

    public CheckedDivide(NewEx a, NewEx b) {
        super(a, b);
    }

    public String getChar() {
        return "/";
    }

    @Override
    public int evaluate(int x) {
        return a.evaluate(x) / b.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (b.evaluate(x,y,z) == 0) {
            throw new DivisionByZero("division by zero: " + a.toString() + " / " + b.toString());
        } else if (a.evaluate(x,y,z) == Integer.MIN_VALUE && b.evaluate(x,y,z) == -1) {
            throw new OverflowEx("Overflow at: " + a.toString() + " / " + b.toString() );
        }
        return a.evaluate(x, y, z) / b.evaluate(x, y, z);
    }
}
