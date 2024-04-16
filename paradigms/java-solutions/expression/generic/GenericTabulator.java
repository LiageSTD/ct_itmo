package expression.generic;

import expression.Op.OpInterf;
import expression.parser.ExpressionParser;

public class GenericTabulator implements Tabulator {

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Mathematicians<?> mod = Factory.getClass(mode);
        return doTheThing(mod, expression, x1, x2, y1, y2, z1, z2 );
    }
     private <T> Object[][][] doTheThing(Mathematicians<T> mod, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] res = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        ExpressionParser<T> parser = new ExpressionParser<T>();
        OpInterf<T> def = parser.parse(expression, mod);
        for (int x = 0; x1 + x <= x2; x++) {
            for (int y = 0; y1 + y <= y2; y++) {
                for (int z = 0; z1 + z <= z2; z++) {
                    try{
                        res[x][y][z] = def.evaluate(String.valueOf(x1 + x),String.valueOf(y1 + y),String.valueOf(z1 + z));
                    } catch (Exception e) {
                        res[x][y][z] = null;
                    }
                }
            }
        }
        return res;
    }
}
