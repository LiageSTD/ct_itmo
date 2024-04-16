package expression.Op;

import expression.generic.GenericTabulator;
import expression.generic.Mathematicians;

public class GAdd<T> extends AbstrB<T> {
    public GAdd(OpInterf<T> a, OpInterf<T> b, Mathematicians<T> mod) {
        super(a,b,mod);
    }

    @Override
    protected T getResult(T a, T b) {
        return mod.Add(a, b);
    }
}
