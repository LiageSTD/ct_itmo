package expression;

public abstract class Funcs implements NewEx {
    protected NewEx a, b;

    protected abstract int getRes(int a, int b);

    protected Funcs(NewEx a, NewEx b) {
        this.a = a;
        this.b = b;
    }

    public Funcs(NewEx a) {
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
        return a.equals(((Funcs) bE).a) && b.equals(((Funcs) bE).b);
    }

    public String toString() {
        return "(" + a.toString() + " " + getChar() + " " + b.toString() + ")";
    }

    @Override
    public int evaluate(int x) {
        return 0;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return getRes(a.evaluate(x,y,z), b.evaluate(x,y,z));
    }

    @Override
    public int hashCode() {
        return (3 * getChar().hashCode() + a.hashCode()) * 19 + b.hashCode() * 11;
    }
}
