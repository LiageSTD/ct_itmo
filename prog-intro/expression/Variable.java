package expression;

public class Variable implements NewEx {
    private String loc;
    public Variable(String x) {
        this.loc = x;
    }
    public String toString() {
        return loc;
    }
    public int evaluate(int x) {
        return x;
    }

    public boolean equals(Object b) {
        if (!(b instanceof Variable)){
            return false;
        } else return loc.equals(((Variable) b).loc);
    }
    @Override
    public int hashCode() {
        return loc.hashCode();
    }
    @Override
    public int evaluate(int x, int y, int z) {
        switch (loc) {
            case "x" -> {
                return x;
            }
            case "y" -> {
                return y;
            }
            case "z" -> {
                return z;
            }
        }
        return 0;
    }

}
