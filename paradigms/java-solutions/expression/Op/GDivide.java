package expression.Op;

import expression.generic.Mathematicians;

public class GDivide<T> extends AbstrB<T>{
    public GDivide(OpInterf<T> a, OpInterf<T> b, Mathematicians<T> mod) {
        super(a,b,mod);
    }
    @Override
    protected T getResult(T a, T b) {
        return mod.Divide(a, b);
    }
}
