package expression.generic;

public class DoubleMathematicians implements Mathematicians<Double>{

    @Override
    public Double Add(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double Divide(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double Subtract(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double Multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double parse(String a) {
        return Double.parseDouble(a);
    }

    @Override
    public Double Negate(Double x) {
        return -x;
    }
}
