package expression.parser;
import expression.exceptions.ConstLenEx;
import expression.exceptions.NotEnEx;
import expression.exceptions.WrongBracketsEx;
import expression.Op.*;
import expression.generic.Mathematicians;

import static expression.parser.Tokenizer.tokenize;

public class ExpressionParser<T> {



    public OpInterf<T> parse(String expr, Mathematicians<T> mod) {
        MapOfClasses mp = new MapOfClasses();
        mp.create();
        Tokenizer tokens = tokenize(expr,mp);
        return parsingTokens(tokens, mp, mod);
    }

    private OpInterf<T> parsingTokens(Tokenizer tokens, MapOfClasses mp, Mathematicians<T> mod) {
        if (tokens.contains(Type.RB)) {
            throw new WrongBracketsEx("NO LB >" + tokens.showAll() + "<");
        }
        if (tokens.getLen() == 0) {
            throw new NotEnEx("Not enough elements or no elements");
        }
        if ((tokens.getLen() > 1 && !tokens.contains("~~"))) {
            throw new NotEnEx("Operator is missing >" + tokens.showAll() + "<");
        }
        if ((!tokens.contains("~") && !tokens.contains(Type.EXPR) && !tokens.contains(Type.NUM))) {
            throw new NotEnEx("No expr or nums >" + tokens.showAll() + "<");
        }
        Tokenizer tksL = new Tokenizer();
        Tokenizer tksR = new Tokenizer();
         if (tokens.contains(Type.PLUS)) {
            int pos = tokens.getPos(Type.PLUS);
            tksL.add(tokens, 0, pos - 1);
            tksR.add(tokens, pos + 1, tokens.getLen() - 1);
            return new GAdd<T>(parsingTokens(tksL, mp, mod), parsingTokens(tksR, mp, mod),mod);
        } else if (tokens.contains(Type.MINUS)) {
            int pos = tokens.lastIndexOf(Type.MINUS);
            for (; pos > 0; pos--) {
                if (tokens.getType(pos) == Type.MINUS) {
                    OpInterf<T> l = newMinusF(tokens, pos, tksL, tksR, mp, mod);
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
                return divF(tokens, tksL, tksR, mp, mod);
            }
        }
        if (tokens.contains(Type.MUL)) {
            return mulF(tokens, tksL, tksR, mp, mod);
        } else if (tokens.contains(Type.MINUS)) {
            int pos = tokens.getPos(Type.MINUS);
            tksL.add(tokens, pos + 1, tokens.getLen() - 1);
            return new GNegate<T>(parsingTokens(tksL, mp,mod), mod);
        } else if (tokens.contains(Type.NUM)) {
            try {
                return new GConst<T>(tokens.getDef(tokens.getPos(Type.NUM)).toString(), mod);
            } catch (NumberFormatException e) {
                throw new ConstLenEx("Too big Const");
            }

        } else if (tokens.contains(Type.EXPR)) {
            return parsingTokens(tokenize(tokens.getDef(tokens.getPos(Type.EXPR)).toString(), mp),mp, mod);
        } else {
            switch (tokens.getType(0)) {
                case X -> {
                    return new GVariable<T>("x",mod);
                }
                case Y -> {
                    return new GVariable<T>("y",mod);
                }
                case Z -> {
                    return new GVariable<T>("z",mod);
                }
                default -> throw new RuntimeException("Ты чего наделал? Это ошибка падать не должна... " +
                        "Выключи компьютер и больше не включай");
            }
        }
    }
    // KOD OT DODO PIZZA 4546


    private OpInterf<T> mulF(Tokenizer tokens, Tokenizer tksL, Tokenizer tksR, MapOfClasses mp, Mathematicians<T> mod) {
        int pos = tokens.lastIndexOf(Type.MUL);
        tksL.add(tokens, 0, pos - 1);
        tksR.add(tokens, pos + 1, tokens.getLen() - 1);
        return new GMultiply<T>(parsingTokens(tksL, mp, mod), parsingTokens(tksR,mp,mod),mod);
    }

    private OpInterf<T> divF(Tokenizer tokens, Tokenizer tksL, Tokenizer tksR, MapOfClasses mp, Mathematicians<T> mod) {
        int pos = tokens.lastIndexOf(Type.DIV);
        tksL.add(tokens, 0, pos - 1);
        tksR.add(tokens, pos + 1, tokens.getLen() - 1);
        return new GDivide<T>(parsingTokens(tksL, mp, mod), parsingTokens(tksR, mp, mod), mod);
    }

    private OpInterf<T> newMinusF(Tokenizer tokens, int pos, Tokenizer tksL, Tokenizer tksR, MapOfClasses mp, Mathematicians<T> mod) {
        int k = pos + 1;
        int f = pos - 1;
        if (pos > 0 && pos < tokens.getLen() - 1 && (tokens.getType(f) == Type.EXPR || tokens.getType(f) == Type.NUM
                || tokens.getDef(f).equals("~"))
                && (tokens.getType(k) == Type.EXPR || tokens.getType(k) == Type.NUM
                || tokens.getDef(k).equals("~") || tokens.getType(k) == Type.MINUS || tokens.getType(k) == Type.COUNT)) {

            tksL.add(tokens, 0, pos - 1);
            tksR.add(tokens, pos + 1, tokens.getLen() - 1);
            return new GSubtract<T>(parsingTokens(tksL, mp, mod), parsingTokens(tksR, mp, mod), mod);
        }
        return null;
    }
}
