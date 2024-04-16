package Game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Game {
    private final boolean log;
    private final Player player1, player2;
    private int var = 1;
    private int res1 = 0;
    private int res2 = 0;

    public Game(final boolean log, final Player player1, final Player player2) {
        this.log = log;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(Board board) {
            while (true) {
                final int result1 = move(board, player1, 1);
                if (result1 != -1) {
                    return result1;
                }
                final int result2 = move(board, player2, 2);
                if (result2 != -1) {
                    return result2;
                }
            }

    }

    public int playRev(Board board) {
        while (true) {
            final int result2 = move(board, player2, 2);
            if (result2 != -1) {
                return result2;
            }
            final int result1 = move(board, player1, 1);
            if (result1 != -1) {
                return result1;
            }
        }

    }
    public int match(int m,int n,int k, int num) {
        Cell chr = Cell.X;
        do {
            Board board = new TicTacToeBoard(m,n,k, chr);
            if (var % 2 == 0) {
                switch (play(board)) {
                    case 1: {res1++; break;}
                    case 2: {res2++; break;}
                }
            } else {
                switch (playRev(board)) {
                    case 1: {res1++; break;}
                    case 2: {res2++; break;}
                }
            }
            var++;
            chr = chr == Cell.X ? Cell.O : Cell.X;
        } while (res1 < num && res2 < num);
        if (res1 >= num) {
            System.out.println("Победил первый");
        } else {
            System.out.println("Победил второй");
        }
        return num;
    }

    private int move(final Board board, final Player player, final int no) {
        final Move move = player.move(board.getPosition(), board.getCell());
        final Result result = board.makeMove(move);
        log("Player " + no + " move: " + move);
        log("Position:\n" + board);
        if (result == Result.WIN) {
            log("Player " + no + " won");
            return no;
        } else if (result == Result.LOSE) {
            log("Player " + no + " lose");
            return 3 - no;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else {
            return -1;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
