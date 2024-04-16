package expression.generic;

public class UnIntMathematicians implements Mathematicians<Integer>{
    @Override
    public Integer Add(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public Integer Divide(Integer a, Integer b) {
        return a / b;
    }

    @Override
    public Integer Subtract(Integer a, Integer b) {
        return a - b;
    }

    @Override
    public Integer Multiply(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer parse(String a) {
        return Integer.parseInt(a);
    }

    @Override
    public Integer Negate(Integer a) {
        return -a;
    }
}
