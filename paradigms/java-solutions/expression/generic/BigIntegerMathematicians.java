package expression.generic;

import java.math.BigInteger;

public class BigIntegerMathematicians implements Mathematicians<BigInteger> {

    @Override
    public BigInteger Add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger Divide(BigInteger a, BigInteger b) {
        return a.divide(b);
    }

    @Override
    public BigInteger Subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger Multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger parse(String a) {
        return BigInteger.valueOf(Long.parseLong(a));
    }

    @Override
    public BigInteger Negate(BigInteger x) {
        return x.multiply(BigInteger.valueOf(-1));
    }
}
