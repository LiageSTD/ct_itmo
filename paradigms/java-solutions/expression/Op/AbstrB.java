package expression.Op;

import expression.common.Op;
import expression.generic.Mathematicians;

public abstract class AbstrB<T> implements OpInterf<T> {

    OpInterf<T> a;
    OpInterf<T> b;
    Mathematicians<T> mod;
    public AbstrB(OpInterf<T> a, OpInterf<T> b, Mathematicians<T> mod) {
        this.a = a;
        this.b = b;
        this.mod = mod;
    }
    @Override
    public T evaluate(String x, String y, String z) {
        return getResult(a.evaluate(x,y,z), b.evaluate(x,y,z));
    }

    protected abstract T getResult(T a, T b) ;
}
