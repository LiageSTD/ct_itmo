package expression.parser;

import expression.exceptions.Type;

public class TypeCon {
    Type type;
    String b;
    protected TypeCon (Type type, String b) {
        this.type = type;
        this.b = b;
    }
}
