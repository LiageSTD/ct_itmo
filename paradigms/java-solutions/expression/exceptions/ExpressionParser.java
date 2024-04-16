package expression.exceptions;

import expression.*;
import expression.exceptions.*;
import expression.parser.TripleParser;

public class ExpressionParser implements TripleParser {


    public TripleExpression parse(String expr) {
        //System.err.println("start\n" + expr + "\nend");
        Tokenizer tokens = tokenize(expr);

        return parsingTokens(tokens);
    }

    private NewEx parsingTokens(Tokenizer tokens) {
        if (tokens.contains(Type.RB)) {
            throw new NotEnEx("Wrong bracket sequence");
        }
        if (tokens.getLen() == 0) {
            throw new NotEnEx("Not enough elements");
        }
        if ((tokens.getLen() > 1 && !tokens.contains("~~"))) {
            throw new NotEnEx("Operator is missing");
        }
        if ((!tokens.contains("~") && !tokens.contains(Type.EXPR) && !tokens.contains(Type.NUM))) {
            throw new NotEnEx("Not enough elements");
        }
        if (tokens.contains(Type.SET)) {
            int pos = tokens.getPos(Type.SET);
            Tokenizer tksL = new Tokenizer();
            Tokenizer tksR = new Tokenizer();
            tksL.add(tokens, 0, pos - 1);
            tksR.add(tokens, pos + 1, tokens.getLen() - 1);
            return new Set(parsingTokens(tksL), parsingTokens(tksR));
        } else if (tokens.contains(Type.CLEAR)) {
            int pos = tokens.getPos(Type.CLEAR);
            Tokenizer tksL = new Tokenizer();
            Tokenizer tksR = new Tokenizer();
            tksL.add(tokens, 0, pos - 1);
            tksR.add(tokens, pos + 1, tokens.getLen() - 1);
            return new Clear(parsingTokens(tksL), parsingTokens(tksR));
        } else if (tokens.contains(Type.PLUS)) {
            int pos = tokens.getPos(Type.PLUS);
            Tokenizer tksL = new Tokenizer();
            Tokenizer tksR = new Tokenizer();
            tksL.add(tokens, 0, pos - 1);
            tksR.add(tokens, pos + 1, tokens.getLen() - 1);
            return new CheckedAdd(parsingTokens(tksL), parsingTokens(tksR));
        } else if (tokens.contains(Type.MINUS)) {
            int pos = tokens.lastIndexOf(Type.MINUS);
            for (; pos > 0; pos--) {
                if (tokens.getType(pos) == Type.MINUS) {
                    NewEx l = newMinusF(tokens, pos);
                    if (l != null) {
                        return l;
                    }
                }
            }
        }

        if (tokens.contains(Type.DIV)) {
            int pos = tokens.lastIndexOf(Type.DIV) + 1;
            boolean flag = true;

            for (; pos < tokens.getLen(); pos++) {
                if (tokens.getType(pos) == Type.EXPR && tokens.getType(pos) == Type.NUM
                        && tokens.getDef(pos).equals("~")) {
                    break;
                }
                if (tokens.getType(pos) == Type.MUL) {
                    flag = false;
                }
            }
            if (flag) {
                return divF(tokens);
            }
        }
        if (tokens.contains(Type.MUL)) {
            return mulF(tokens);
        } else if (tokens.contains(Type.COUNT)) {
            int pos = tokens.getPos(Type.COUNT);
            Tokenizer tks = new Tokenizer();
            tks.add(tokens, pos + 1, tokens.getLen() - 1);
            return new Count(parsingTokens(tks));
        } else if (tokens.contains(Type.MINUS)) {
            int pos = tokens.getPos(Type.MINUS);
            Tokenizer tks = new Tokenizer();
            tks.add(tokens, pos + 1, tokens.getLen() - 1);
            return new CheckedNegate(parsingTokens(tks));
        } else if (tokens.contains(Type.NUM)) {
            String var = tokens.getDef(tokens.getPos(Type.NUM));
            try {
                return new Const(Integer.parseInt(tokens.getDef(tokens.getPos(Type.NUM))));
            } catch (NumberFormatException e) {
                throw new ConstLenEx("Too big Const");
            }

        } else if (tokens.contains(Type.EXPR)) {
            return parsingTokens(tokenize(tokens.getDef(tokens.getPos(Type.EXPR))));
        } else {
            switch (tokens.getType(0)) {
                case X -> {
                    return new Variable("x");
                }
                case Y -> {
                    return new Variable("y");
                }
                case Z -> {
                    return new Variable("z");
                }
                default -> throw new RuntimeException("Ты чего наделал? Это ошибка падать не должна... " +
                        "Выключи компьютер и больше не включай");
            }
        }
    }
    // KOD OT DODO PIZZA 4546

    private Tokenizer tokenize(String expr) {
        if (expr.length() == 0) {
            throw new NotEnEx("Not enough elements");
        }
        Tokenizer tokens = new Tokenizer();
        int pos = 0;
        while (pos < expr.length()) {
            Type Class;
            while (pos < expr.length() && Character.isWhitespace(expr.charAt(pos))) {
                pos++;
            }
            if (pos >= expr.length()) {
                return tokens;
            }
            int start = pos;
            char lol = expr.charAt(start);
            Class = takeClass(expr.charAt(start), expr, pos);
            if (Class == Type.CLEAR || Class == Type.COUNT) {
                pos += 5;
            }
            if (Class == null) {
                throw new NotEnEx("Not enough elements");
            }
            while (pos < expr.length() && Class == takeClass(expr.charAt(pos), expr, pos)) {
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
                case SET -> {
                    if (expr.charAt(pos++) == 'e' && expr.charAt(pos++) == 't')
                        tokens.add(Type.SET, "~~");
                }
                case COUNT -> {
                    tokens.add(Type.COUNT, "~~");
                }
                case CLEAR -> {
                    tokens.add(Type.CLEAR, "~~");
                }
                case PARSENEXT -> {
                    if (expr.charAt(pos++) == 'o') {
                        if (expr.charAt(pos++) == 'u') {
                            if (expr.charAt(pos++) == 'n') {
                                if (expr.charAt(pos++) == 't') {
                                    tokens.add(Type.COUNT, "~~");
                                }
                            }
                        }
                    } else if (expr.charAt(pos) == 'l') {
                        pos++;
                        if (expr.charAt(pos++) == 'e') {
                            if (expr.charAt(pos++) == 'a') {
                                if (expr.charAt(pos++) == 'r') {
                                    tokens.add(Type.CLEAR, "~~");
                                }
                            }
                        }
                    }
                }
            }
        }

        return tokens;
    }

    private Type takeClass(Character x, String expr, int pos) {
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
                    if (pos == 0 || pos + 3 > expr.length() - 1) {
                        throw new NotEnEx("Not enough elements");
                    }
                    if (Character.isWhitespace(expr.charAt(pos - 1)) || expr.charAt(pos - 1) == ')' && (Character.isWhitespace(expr.charAt(pos + 3)) || expr.charAt(pos + 3) == '-' || expr.charAt(pos + 3) == '(')) {
                        return Type.SET;
                    } else {
                        throw new NotEnEx("Not enough elements");
                    }
                }
                case 'c' -> {
                    if (pos + 5 < expr.length()) {
                        boolean whitespace = Character.isWhitespace(expr.charAt(pos + 5)) || expr.charAt(pos + 5) == '-' || expr.charAt(pos + 5) == '(';
                        if (expr.startsWith("count", pos) && whitespace) {
                            return Type.COUNT;
                        }
                        if (pos - 1 > 0 ) {
                            boolean whitespace1 = Character.isWhitespace(expr.charAt(pos - 1));
                            if (expr.startsWith("clear", pos) && (whitespace1 || expr.charAt(pos - 1) == ')') && whitespace) {
                                return Type.CLEAR;
                            }
                        } else {
                            throw new NotEnEx("Not enough elements");
                        }
                    } else {
                        throw new WrongFormatEx("Wrong format");
                    }
                }

            }
        }
        return null;
    }

    private NewEx mulF(Tokenizer tokens) {
        int pos = tokens.lastIndexOf(Type.MUL);
        Tokenizer tksL = new Tokenizer();
        Tokenizer tksR = new Tokenizer();
        tksL.add(tokens, 0, pos - 1);
        tksR.add(tokens, pos + 1, tokens.getLen() - 1);
        return new CheckedMultiply(parsingTokens(tksL), parsingTokens(tksR));
    }

    private NewEx divF(Tokenizer tokens) {
        int pos = tokens.lastIndexOf(Type.DIV);
        Tokenizer tksL = new Tokenizer();
        Tokenizer tksR = new Tokenizer();
        tksL.add(tokens, 0, pos - 1);
        tksR.add(tokens, pos + 1, tokens.getLen() - 1);
        return new CheckedDivide(parsingTokens(tksL), parsingTokens(tksR));
    }

    private NewEx newMinusF(Tokenizer tokens, int pos) {
        int k = pos + 1;
        int f = pos - 1;
        if (pos > 0 && pos < tokens.getLen() - 1 && (tokens.getType(f) == Type.EXPR || tokens.getType(f) == Type.NUM
                || tokens.getDef(f).equals("~"))
                && (tokens.getType(k) == Type.EXPR || tokens.getType(k) == Type.NUM
                || tokens.getDef(k).equals("~") || tokens.getType(k) == Type.MINUS || tokens.getType(k) == Type.COUNT)) {
            Tokenizer tksL = new Tokenizer();
            Tokenizer tksR = new Tokenizer();
            tksL.add(tokens, 0, pos - 1);
            tksR.add(tokens, pos + 1, tokens.getLen() - 1);
            return new CheckedSubtract(parsingTokens(tksL), parsingTokens(tksR));
        }
        return null;
    }
}
