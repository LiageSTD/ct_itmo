package expression.generic;

import expression.exceptions.DivisionByZero;

public class ShortMathematicians implements Mathematicians<Short>{

    @Override
    public Short Add(Short a, Short b) {
        return (short) (a + b);
    }

    @Override
    public Short Divide(Short a, Short b) {
        if (b == 0) {
            throw new DivisionByZero(a + "/" + b);
        }
        return (short) (a / b);
    }

    @Override
    public Short Subtract(Short a, Short b) {
        return (short) (a - b);
    }

    @Override
    public Short Multiply(Short a, Short b) {
        return (short) (a * b);
    }

    @Override
    public Short parse(String a) {
        return (short) Integer.parseInt(a);
    }

    @Override
    public Short Negate(Short a) {
        return (short) - a;
    }
}
