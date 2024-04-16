package expression.Op;
import expression.generic.Mathematicians;

public class GVariable<T> implements OpInterf<T>{
    String x;
    Mathematicians<T> mod;
    public GVariable(String x, Mathematicians<T> mod) {
        this.x = x;
        this.mod = mod;
    }

    @Override
    public T evaluate(String x, String y, String z) {
        if (this.x.equals("x")) {
            return mod.parse(x);
        }
        if (this.x.equals("y")) {
            return mod.parse(y);
        }
        return mod.parse(z);
    }
}
