package expression.generic;

public class FloatMathematicians implements Mathematicians<Float> {
    @Override
    public Float Add(Float a, Float b) {
        return a + b;
    }

    @Override
    public Float Divide(Float a, Float b) {
        return a / b;
    }

    @Override
    public Float Subtract(Float a, Float b) {
        return a - b;
    }

    @Override
    public Float Multiply(Float a, Float b) {
        return a * b;
    }

    @Override
    public Float parse(String a) {
        return Float.parseFloat(a);
    }

    @Override
    public Float Negate(Float a) {
        return - a;
    }
}
