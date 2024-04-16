package expression.Op;

import expression.generic.GenericTabulator;
import expression.generic.Mathematicians;

public class GNegate<T> extends AbstrU<T>{
    public GNegate(OpInterf<T> a, Mathematicians<T> mod) {
        super(a, mod);
    }
    @Override
    protected T getResult(T a) {
        return mod.Negate(a);
    }
}
