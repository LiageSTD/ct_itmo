package expression.Op;

import expression.generic.GenericTabulator;
import expression.generic.Mathematicians;

public class GSubtract<T> extends AbstrB<T> {
    public GSubtract(OpInterf<T> a, OpInterf<T> b, Mathematicians<T> mod) {
        super(a,b,mod);
    }
    @Override
    protected T getResult(T a, T b) {
        return mod.Subtract(a, b);
    }

}
