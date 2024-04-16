package expression.generic;

import expression.exceptions.DivisionByZero;
import expression.exceptions.OverflowEx;

public class IntMathematicians implements Mathematicians<Integer>{


    @Override
    public Integer Add(Integer a, Integer b) {
        if (b > 0 && a > Integer.MAX_VALUE - b) {
            throw new OverflowEx("overflow at: " + a + " + " + b);
        } else if (a < 0 && b < Integer.MIN_VALUE - a) {
            throw new OverflowEx("overflow at: " + a + " + " + b);
        }
        return a + b;
    }

    @Override
    public Integer Divide(Integer a, Integer b) {
        if (b == 0) {
            throw new DivisionByZero("division by zero: " + a.toString() + " / " + b.toString());
        } else if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowEx("Overflow at: " + a.toString() + " / " + b.toString() );
        }
        return a / b;
    }

    @Override
    public Integer Subtract(Integer a, Integer b) {
        if (b < 0 && a > Integer.MAX_VALUE + b) {
            throw new OverflowEx("overflow at: " + a + " - " + b);
        } else if (b > 0 && a < Integer.MIN_VALUE + b) {
            throw new OverflowEx("overflow at: " + a + " - " + b);
        }
        return a - b;
    }

    @Override
    public Integer Multiply(Integer a, Integer b) {
        if (a > 0 && b > 0 && a > Integer.MAX_VALUE / b) {
            throw new OverflowEx("overflow at: " + a + " * " + b);
        } else if (a < 0 && b < 0 && a < Integer.MAX_VALUE / b) {
            throw new OverflowEx("overflow at: " + a + " * " + b);
        } else if (a < 0 && b > 0 && a < Integer.MIN_VALUE / b) {
            throw new OverflowEx("overflow at: " + a + " * " + b);
        } else if (a > 0 && b < 0 && b < Integer.MIN_VALUE / a) {
            throw new OverflowEx("overflow at: " + a + " * " + b);
        }
        return a * b;
    }

    @Override
    public Integer parse(String a) {
        return Integer.parseInt(a);
    }

    @Override
    public Integer Negate(Integer a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowEx("overflow at: " + "-" + a);
        }
        return -a;
    }
}
