package expression;

public abstract class FuncsU implements NewEx {
    protected NewEx a;

    protected abstract int getRes(int a);


    public FuncsU(NewEx a) {
        this.a = a;
    }

    abstract public String getChar();

    public boolean equals(Object bE) {
        if (!(bE instanceof Funcs)) {
            return false;
        }
        if (!(this.getChar().equals(((Funcs) bE).getChar()))) {
            return false;
        }
        return a.equals(((Funcs) bE).a);
    }

    public String toString() {
        return getChar() + "(" + a.toString() + ")";
    }
    @Override
    public int evaluate(int x) {
        return 0;
    }


    @Override
    public int evaluate(int x, int y, int z) {
        return getRes(a.evaluate(x,y,z));
    }

    @Override
    public int hashCode() {
        return (3 * getChar().hashCode() + a.hashCode()) * 19 ;
    }
}

