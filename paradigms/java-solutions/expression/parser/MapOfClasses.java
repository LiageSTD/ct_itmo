package expression.parser;
import expression.parser.Type;

import java.util.HashMap;
import java.util.Map;

public class MapOfClasses {

     static Map<Character, expression.parser.Type> mapOfClasses = new HashMap();
    protected void create() {

        mapOfClasses.put('0', expression.parser.Type.NUM);
        mapOfClasses.put('1', expression.parser.Type.NUM);
        mapOfClasses.put('2', expression.parser.Type.NUM);
        mapOfClasses.put('3', expression.parser.Type.NUM);
        mapOfClasses.put('4', expression.parser.Type.NUM);
        mapOfClasses.put('5', expression.parser.Type.NUM);
        mapOfClasses.put('6', expression.parser.Type.NUM);
        mapOfClasses.put('7', expression.parser.Type.NUM);
        mapOfClasses.put('8', expression.parser.Type.NUM);
        mapOfClasses.put('9', expression.parser.Type.NUM);
        mapOfClasses.put('+', expression.parser.Type.PLUS);
        mapOfClasses.put('-', expression.parser.Type.MINUS);
        mapOfClasses.put('*', expression.parser.Type.MUL);
        mapOfClasses.put('/', expression.parser.Type.DIV);
        mapOfClasses.put('(', expression.parser.Type.LB);
        mapOfClasses.put(')', expression.parser.Type.RB);
        mapOfClasses.put('x', expression.parser.Type.X);
        mapOfClasses.put('y', expression.parser.Type.Y);
        mapOfClasses.put('z', expression.parser.Type.Z);

        }
    protected static expression.parser.Type getValue(char r) {
        if (Character.isWhitespace(r)) {
            return Type.ERR;
        }
        return mapOfClasses.get(r);
    }
}

