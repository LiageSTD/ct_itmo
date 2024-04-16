package expression.generic;

public class Factory {
    public static Mathematicians<?> getClass(String string) {
        switch (string) {
            case "i" -> {
                return new IntMathematicians();
            }
            case "d" -> {
                return new DoubleMathematicians();
            }
            case "u" -> {
                return new UnIntMathematicians();
            }
            case "f" -> {
                return new FloatMathematicians();
            }
            case "s" -> {
                return new ShortMathematicians();
            }
            default -> {
                return new BigIntegerMathematicians();
            }
        }
    }
}
