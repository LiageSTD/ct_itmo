package expression.parser;

import expression.exceptions.NotEnEx;
import expression.exceptions.WrongBracketsEx;
import expression.exceptions.WrongFormatEx;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {


    List<Type> L = new ArrayList<>();
    List<Object> Def = new ArrayList<>();
    protected static Tokenizer tokenize(String expr, MapOfClasses mp) {
        if (expr.length() == 0) {
            throw new NotEnEx("Not enough elements");
        }
        Tokenizer tokens = new Tokenizer();
        int pos = 0;
        while (pos < expr.length()) {
            expression.parser.Type Class;
            while (pos < expr.length() && Character.isWhitespace(expr.charAt(pos))) {
                pos++;
            }
            if (pos >= expr.length()) {
                return tokens;
            }
            int start = pos;
            Class = mp.getValue(expr.charAt(start));
            if (Class == Type.CLEAR || Class == Type.COUNT) {
                pos += 5;
            }
//            if (Class.equals(Type.SET)) {
//                pos+= 3;
//            }
            if (Class == null) {
                throw new NotEnEx("Not enough elements");
            }
            while (pos < expr.length() && Class == mp.getValue(expr.charAt(pos))) {
                pos++;
                if (Class == Type.LB || Class == Type.RB || Class == Type.MINUS) {
                    break;
                }
            }
            switch (Class) {
                case NUM -> {
                    tokens.add(Type.NUM, expr.substring(start, pos));
                }
                case PLUS -> {
                    tokens.add(Type.PLUS, "~~");
                }
                case LB -> {
                    int bCnt = 1;
                    int bPos = pos - 1;
                    while (bPos < expr.length() - 1 && bCnt != 0) {
                        bPos++;
                        if (expr.charAt(bPos) == '(') {
                            bCnt++;
                        } else if (expr.charAt(bPos) == ')') {
                            bCnt--;
                        }
                        if (bCnt < 0) {
                            throw new RuntimeException("Wrong bracket sequence \"" + expr + "\"");
                        }
                    }
                    if (bCnt != 0) {
                        throw new WrongBracketsEx("Wrong bracket sequence \"" + expr + "\"");
                    }
                    tokens.add(Type.EXPR, expr.substring(start + 1, bPos));
                    pos = bPos + 1;
                }
                case MINUS -> {
                    try {
                        int len = tokens.getLen() - 1;
                        if ('0' <= expr.charAt(pos) && expr.charAt(pos) <= '9' && (tokens.isEmpty() || !tokens.isEmpty()
                                && (tokens.getType(len) != Type.NUM
                                && !tokens.getDef(len).equals("~") && tokens.getType(len) != Type.EXPR))) {
                            do {
                                if (expr.charAt(pos) == '-') {
                                    break;
                                }
                                pos++;
                            } while (pos < expr.length() && '0' <= expr.charAt(pos) && expr.charAt(pos) <= '9');
                            tokens.add(Type.NUM, expr.substring(start, pos));
                        } else {
                            tokens.add(Type.MINUS, "~~");
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        throw new NotEnEx("Minus Trouble \"" + expr + "\"");
                    }
                }
                case DIV -> {
                    tokens.add(Type.DIV, "~~");
                }
                case MUL -> {
                    tokens.add(Type.MUL, "~~");
                }
                case X -> {
                    tokens.add(Type.X, "~");
                }
                case Y -> {
                    tokens.add(Type.Y, "~");
                }
                case Z -> {
                    tokens.add(Type.Z, "~");
                }
                case RB -> {
                    tokens.add(Type.RB, "10");
                }

            }
        }

        return tokens;
    }
    private static Type takeClass(Character x, String expr, int pos) {
        if (x >= '0' && x <= '9') {
            return Type.NUM;
        } else {
            switch (x) {
                case '+' -> {
                    return Type.PLUS;
                }
                case '-' -> {
                    return Type.MINUS;
                }
                case '*' -> {
                    return Type.MUL;
                }
                case '/' -> {
                    return Type.DIV;
                }
                case '(' -> {
                    return Type.LB;
                }
                case ')' -> {
                    return Type.RB;
                }
                case 'x' -> {
                    return Type.X;
                }
                case 'y' -> {
                    return Type.Y;
                }
                case 'z' -> {
                    return Type.Z;
                }
                case 's' -> {
                    char Cpos3 = expr.charAt(pos + 3);
                    if (pos == 0 || pos + 3 > expr.length() - 1) {
                        throw new NotEnEx("Not enough elements");
                    }
                    if (Character.isWhitespace(expr.charAt(pos - 1)) || expr.charAt(pos - 1) == ')' && (Character.isWhitespace(Cpos3)
                            || Cpos3 == '-' || Cpos3 == '(')) {
                        return Type.SET;
                    } else {
                        throw new NotEnEx("Wrong format: >" + expr + "<");
                    }
                }
                case 'c' -> {
                        char Cpos5 = expr.charAt(pos + 5);
                        if (pos + 5 < expr.length()) {
                            boolean whitespace = Character.isWhitespace(Cpos5) || Cpos5 == '-' || Cpos5 == '(';
                            if (expr.startsWith("count", pos) && whitespace) {
                                return Type.COUNT;
                            }
                            if (pos - 1 > 0 ) {
                                boolean whitespace1 = Character.isWhitespace(expr.charAt(pos - 1));
                                if (expr.startsWith("clear", pos) && (whitespace1 || expr.charAt(pos - 1) == ')') && whitespace) {
                                    return Type.CLEAR;
                                }
                            } else {
                                throw new WrongFormatEx("Wrong format: >" + expr + "<");
                            }
                        } else {
                            throw new WrongFormatEx("Wrong format: >" + expr + "<");
                        }
                }

            }
        }
        return null;
    }

    protected void add(Type token, String def) {
        L.add(token);
        Def.add(def);
    }
    protected void add(Tokenizer tokens, int s, int e) {
        for (;s <= e; s++) {
            L.add(tokens.getType(s));
            Def.add(tokens.getDef(s));
        }
    }
    protected boolean contains(String t) {return Def.contains(t);}
    protected boolean contains(Type t) {
        return L.contains(t);
    }
    protected int getPos(Type t) {
        return L.indexOf(t);
    }
    protected int getLen() {
        return L.size();
    }
    protected Type getType(int i) {
        return L.get(i);
    }
    protected Object getDef(int i) {
        return Def.get(i);
    }
    protected int lastIndexOf(Type t) {
        return L.lastIndexOf(t);
    }
    protected boolean isEmpty() {
       return L.isEmpty();
    }
    protected String showAll() {
        StringBuilder sb = new StringBuilder();
        for (Type type : L) {
            sb.append(type).append(" ");
        }
        return sb.delete(sb.length()-1,sb.length()).toString();
    }

    protected void add(Tokenizer tokens) {
        L.addAll(tokens.L);
        Def.addAll(tokens.Def);
    }
}
