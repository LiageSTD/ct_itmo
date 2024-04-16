package expression;

import expression.exceptions.ExpressionParser;

public class Main {
    public static void main(String[] args) {

        System.out.println(new ExpressionParser().parse("z clear(y clear z)").toString());
    }

}
// x + -4 + z + z + y * (z * (-30 + x))
// ((x + (-4 + (z + (z + y)))) * (z * (-30 + x)))
// |x| |- y|  |- z|  |(y + x) * - z||