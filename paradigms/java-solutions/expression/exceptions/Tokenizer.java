package expression.exceptions;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    List<Type> L = new ArrayList<>();
    List<String> Def = new ArrayList<>();

    public void add(Type token, String def) {
        L.add(token);
        Def.add(def);
    }
    public void add(Tokenizer tokens, int s, int e) {
        for (;s <= e; s++) {
            L.add(tokens.getType(s));
            Def.add(tokens.getDef(s));
        }
    }
    public boolean contains(String t) {return Def.contains(t);}
    public boolean contains(Type t) {
        return L.contains(t);
    }
    public int getPos(Type t) {
        return L.indexOf(t);
    }
    public int getLen() {
        return L.size();
    }
    public Type getType(int i) {
        return L.get(i);
    }
    public String getDef(int i) {
        return Def.get(i);
    }
    public int lastIndexOf(Type t) {
        return L.lastIndexOf(t);
    }
    public boolean isEmpty() {
       return L.isEmpty();
    }
    public void delete(int i) {
        L.remove(i);
        Def.remove(i);
    }
    public void add(Tokenizer tokens) {
        L.addAll(tokens.L);
        Def.addAll(tokens.Def);
    }
}
