package expression.Op;

import expression.generic.GenericTabulator;
import expression.generic.Mathematicians;

public class GConst<T> implements OpInterf<T>{
    String a;
    Mathematicians<T> mod;
    public GConst(String a, Mathematicians<T> mod) {
        this.a = a;
        this.mod = mod;
    }
    @Override
    public T evaluate(String x, String y, String z) {
        return mod.parse(a);
    }
}
