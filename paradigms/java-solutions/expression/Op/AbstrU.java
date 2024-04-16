package expression.Op;

import expression.generic.Mathematicians;

public abstract class AbstrU<T> implements OpInterf<T>{
    OpInterf<T> a;
    Mathematicians<T> mod;
    public AbstrU(OpInterf<T> a, Mathematicians<T> mod) {
        this.a = a;
        this.mod = mod;
    }
    @Override
    public T evaluate(String x, String y, String z) {
        return getResult(a.evaluate(x,y,z));
    }
    protected abstract T getResult(T a);
}
