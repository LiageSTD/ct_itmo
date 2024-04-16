package expression;

public class Const implements NewEx {
    private int con;

    public Const(int i) {
        this.con = i;
    }

    @Override
    public String toString() {
        return String.valueOf(con);
    }

    public int evaluate(int x) {
        return con;
    }

    public boolean equals(Object a) {
        if (!(a instanceof Const) || !(con == ((Const) a).con)) {
            return false;
        }
        return true;
    }
    @Override
    public int hashCode() {
        return con;
    }
    @Override
    public int evaluate(int x, int y, int z) {return con;}

}
