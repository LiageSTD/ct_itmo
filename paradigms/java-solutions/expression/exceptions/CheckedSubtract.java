package expression.exceptions;

import expression.Funcs;
import expression.NewEx;
import expression.TripleExpression;
import expression.Variable;

public class CheckedSubtract extends Funcs {
    @Override
    protected int getRes(int a, int b) {
        if (b < 0 && a > Integer.MAX_VALUE + b) {
            throw new OverflowEx("overflow at: " + a + " - " + b);
        } else if (b > 0 && a < Integer.MIN_VALUE + b) {
            throw new OverflowEx("overflow at: " + a + " - " + b);
        }
        return a - b;
    }

    public CheckedSubtract(NewEx a, NewEx b) {
        super(a,b);
    }
    public String getChar() {
        return "-";
    }
    

}
