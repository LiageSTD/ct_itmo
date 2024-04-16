package Game;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class TicTacToeBoard implements Board, Position {

    private static final Map<Cell, String> SYMBOLS = Map.of(
            Cell.X, "X",
            Cell.O, "O",
            Cell.E, "."
    );


    private final Cell[][] cells;
    private Cell turn;
    private final int mB, nB, kB;
    private int remind;

    public TicTacToeBoard(int m, int n, int k, Cell par) {
        this.mB = m;
        this.nB = n;
        this.kB = k;
        this.remind = m * n;
        this.cells = new Cell[n][m];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = par;
    }

    int row = 1;
    int colum = 1;
    int diagRL = 1;
    int diagLR = 1;

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        cells[move.getRow()][move.getColumn()] = move.getValue();
        this.remind--;
        if (remind <= 0) {
            return Result.DRAW;
        }
        turn = move.getValue();
        if (exists(move.getRow(), move.getColumn() - 1) && cells[move.getRow()][move.getColumn() - 1] == turn) {
            row++;
            checkL(move.getRow(), move.getColumn() - 1);
        }
        if (exists(move.getRow(), move.getColumn() + 1) && cells[move.getRow()][move.getColumn() + 1] == turn) {
            row++;
            checkR(move.getRow(), move.getColumn() + 1);
        }
        if (exists(move.getRow() + 1, move.getColumn() + 1) && cells[move.getRow() + 1][move.getColumn() + 1] == turn) {
            diagLR++;
            checkRD(move.getRow() + 1, move.getColumn() + 1);
        }
        if (exists(move.getRow() - 1, move.getColumn() - 1) && cells[move.getRow() - 1][move.getColumn() - 1] == turn) {
            diagLR++;
            checkLRD(move.getRow() - 1, move.getColumn() - 1);
        }
        if (exists(move.getRow() - 1, move.getColumn() + 1) && cells[move.getRow() - 1][move.getColumn() + 1] == turn) {
            diagRL++;
            checkLD(move.getRow() - 1, move.getColumn() + 1);
        }
        if (exists(move.getRow() + 1, move.getColumn() - 1) && cells[move.getRow() + 1][move.getColumn() - 1] == turn) {
            diagRL++;
            checkLLD(move.getRow() + 1, move.getColumn() - 1);
        }
        if (exists(move.getRow() + 1, move.getColumn()) && cells[move.getRow() + 1][move.getColumn()] == turn) {
            colum++;
            checkT(move.getRow() + 1, move.getColumn());
        }
        if (exists(move.getRow() - 1, move.getColumn()) && cells[move.getRow() - 1][move.getColumn()] == turn) {
            colum++;
            checkB(move.getRow() - 1, move.getColumn());
        }
        if (row >= this.kB || colum >= this.kB || diagRL >= this.kB || diagLR >= this.kB) {
            return Result.WIN;
        }
        row = colum = diagLR = diagRL = 1;
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    private void checkRD(int x, int y) {
        if (exists(x + 1, y + 1) && cells[x + 1][y + 1] == turn) {
            this.diagLR++;
            checkRD(x + 1, y + 1);
        }
    }

    private void checkLD(int x, int y) {
        if (exists(x - 1, y + 1) && cells[x - 1][y + 1] == turn) {
            this.diagRL++;
            checkLD(x - 1, y + 1);
        }
    }

    private void checkLLD(int x, int y) {
        if (exists(x + 1, y - 1) && cells[x + 1][y - 1] == turn) {
            this.diagRL++;
            checkLLD(x + 1, y - 1);
        }
    }

    private void checkLRD(int x, int y) {
        if (exists(x - 1, y - 1) && cells[x - 1][y - 1] == turn) {
            this.diagLR++;
            checkR(x - 1, y - 1);
        }
    }

    private void checkL(int x, int y) {
        if (exists(x, y - 1) && cells[x][y - 1] == turn) {
            checkL(x, y - 1);
            this.row++;
        }
    }

    private void checkR(int x, int y) {
        if (exists(x, y + 1) && cells[x][y + 1] == turn) {
            this.row++;
            checkR(x, y + 1);
        }
    }

    private void checkT(int x, int y) {
        if (exists(x + 1, y) && cells[x + 1][y] == turn) {
            checkL(x + 1, y);
            this.colum++;
        }
    }

    private void checkB(int x, int y) {
        if (exists(x - 1, y) && cells[x - 1][y] == turn) {
            checkL(x - 1, y);
            this.colum++;
        }
    }

    /*  @Override
      public Result makeMove(final Move move) {
          if (!isValid(move)) {
              return Result.LOSE;
          }

          cells[move.getRow()][move.getColumn()] = move.getValue();

          int inDiag1 = 0;
          int inDiag2 = 0;
          int empty = 0;
          for (int u = 0; u < 3; u++) {
              int inRow = 0;
              int inColumn = 0;
              for (int v = 0; v < 3; v++) {
                  if (cells[u][v] == turn) {
                      inRow++;
                  }
                  if (cells[v][u] == turn) {
                      inColumn++;
                  }
                  if (cells[u][v] == Cell.E) {
                      empty++;
                  }
              }
              if (inRow == 3 || inColumn == 3) {
                  return Result.WIN;
              }
              if (cells[u][u] == turn) {
                  inDiag1++;
              }
              if (cells[u][2 - u] == turn) {
                  inDiag2++;
              }
          }
          if (inDiag1 == 3 || inDiag2 == 3) {
              return Result.WIN;
          }
          if (empty == 0) {
              return Result.DRAW;
          }

          turn = turn == Cell.X ? Cell.O : Cell.X;
          return Result.UNKNOWN;
      }
  */
    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < this.nB
                && 0 <= move.getColumn() && move.getColumn() < this.mB
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell();
    }

    private boolean exists(int row, int colum) {
        if (0 <= row && row < nB && 0 <= colum && colum < mB) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int x = 1; x <= this.mB; x++) {
            sb.append("  ").append(x);
        }
        for (int r = 0; r < this.nB; r++) {
            sb.append("\n").append("\n");
            sb.append(r + 1);
            for (int c = 0; c < this.mB; c++) {
                sb.append("  ").append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}
