package Game;

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            final Move move = new Move(take(in, "x") - 1, takeForLast(in, "y") - 1, cell);
            if (position.isValid(move)) {
                return move;
            }
            final int row = move.getRow();
            final int column = move.getColumn();
            out.println("Move " + move + " is invalid");
        }
    }
    private static int take(Scanner reader, String par) {
        try {
            int per = Integer.parseInt(reader.next());
            while (per < 1) {
                System.out.println("Вы ввели неверное значение " + par + ". Повторите ввод. " + par + " должен быть больше 1");
                per = Integer.parseInt(reader.next());
            }

            return per;
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели неверное значение " + par +". " + par + " должен быть цифрой." );
            take(reader, par);
        }
        return 0;
    }
    private static int takeForLast(Scanner reader, String par) {
        try {
            Scanner bob = new Scanner(reader.nextLine());
            int per = Integer.parseInt(bob.next());
            while (per < 1 || bob.hasNext()) {
                System.out.println("Вы ввели неверное значение " + par + ". Повторите ввод. " + par +
                        " должен быть больше 1 (это должен быть один элемент)");
                per = Integer.parseInt(reader.next());
                bob = new Scanner(reader.nextLine());
                System.err.println(bob.hasNext());

            }
            return per;
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели неверное значение " + par +". " + par + " должен быть цифрой." );
            take(reader, par);
        }
        return 0;
    }
}
