package expression.exceptions;

import expression.Funcs;
import expression.NewEx;
import expression.TripleExpression;
import expression.Variable;

public class CheckedMultiply extends Funcs {
    @Override
    protected int getRes(int a, int b) {
        return a * b;
    }

    public CheckedMultiply(NewEx a, NewEx b) {
        super(a,b);
    }
    public String getChar() {
        return "*";
    }

    @Override
    public int evaluate(int x) {
        return a.evaluate(x) * b.evaluate(x);
    }
    @Override
    public int evaluate(int x, int y, int z) {
        if (a.evaluate(x,y,z) > 0 && b.evaluate(x,y,z) > 0 && a.evaluate(x,y,z) > Integer.MAX_VALUE / b.evaluate(x,y,z)) {
            throw new OverflowEx("overflow at: " + a.toString() + " * " + b.toString());
        } else if (a.evaluate(x,y,z) < 0 && b.evaluate(x,y,z) < 0 && a.evaluate(x,y,z) < Integer.MAX_VALUE / b.evaluate(x,y,z)) {
            throw new OverflowEx("overflow at: " + a.toString() + " * " + b.toString());
        } else if (a.evaluate(x,y,z) < 0 && b.evaluate(x,y,z) > 0 && a.evaluate(x,y,z) < Integer.MIN_VALUE / b.evaluate(x,y,z)) {
            throw new OverflowEx("overflow at: " + a.toString() + " * " + b.toString());
        } else if (a.evaluate(x,y,z) > 0 && b.evaluate(x,y,z) < 0 && b.evaluate(x,y,z) < Integer.MIN_VALUE / a.evaluate(x,y,z)) {
            throw new OverflowEx("overflow at: " + a.toString() + " * " + b.toString());
        }
        return a.evaluate(x, y, z) * b.evaluate(x, y, z);
    }
}
