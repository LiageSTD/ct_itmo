package expression.Op;

import expression.generic.GenericTabulator;
import expression.generic.Mathematicians;

public class GMultiply<T> extends AbstrB<T>{

    public GMultiply(OpInterf<T> a, OpInterf<T> b, Mathematicians<T> mod) {
        super(a,b,mod);
    }

    protected T getResult(T a, T b) {
        return mod.Multiply(a, b);
    }
}
