package sem4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class G {

    private static final int SIZE = 7;
    private static String input;
    private static int position = 0;

    public static int gcd(int a, int b) {
        return b != 0 ? gcd(b, a % b) : a;
    }

    public static long binC(long n, long k) {
        long res = 1;
        for (long i = n - k + 1; i <= n; ++i) {
            res *= i;
        }
        for (long j = 2; j <= k; ++j) {
            res /= j;
        }
        return res;
    }

    public static List<Long> parse() {
        List<Long> result = new ArrayList<>(Collections.nCopies(SIZE, 0L));

        char current = input.charAt(position);

        switch (current) {
            case 'B' -> makeB(result);
            case 'L' -> makeL(result);
            case 'S' -> makeS(result);
            case 'C' -> makeC(result);
            case 'P' -> makeP(result);
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader inputI = new BufferedReader(new InputStreamReader(System.in));
        input = inputI.readLine();

        List<Long> result = parse();

        for (Long l : result) {
            System.out.print(l + " ");
        }
    }

    private static void makeB(List<Long> result) {
        for (int i = 0; i < SIZE; ++i) {
            result.set(i, i == 1 ? 1L : 0L);
        }
        position++;
    }

    private static void makeL(List<Long> result) {
        position += 2;
        List<Long> l = parse();
        position++;
        result.set(0, 1L);
        for (int i = 1; i < SIZE; ++i) {
            long res = 0;
            for (int j = 1; j <= i; ++j) {
                res += l.get(j) * result.get(i - j);
            }
            result.set(i, res);
        }
    }

    private static void makeS(List<Long> result) {
        position += 2;
        List<Long> l = parse();
        position++;
        List<List<Long>> matrix = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            List<Long> subMatrix = new ArrayList<>(Collections.nCopies(SIZE, 0L));
            matrix.add(subMatrix);
        }
        matrix.get(0).set(0, 1L);
        for (int i = 1; i < SIZE; i++) {
            matrix.get(0).set(i, 1L);
            matrix.get(i).set(0, 0L);
        }
        result.set(0, 1L);
        for (int i = 1; i < SIZE; ++i) {
            for (int j = 1; j < SIZE; ++j) {
                long res = 0;
                for (int k = 0; k <= i / j; ++k) {
                    res += binC(Math.max(l.get(j) + k - 1, 0L), k) * matrix.get(i - k * j).get(j - 1);
                }
                matrix.get(i).set(j, res);
            }
            result.set(i, matrix.get(i).get(i));
        }
    }

    private static void makeC(List<Long> result) {
        position += 2;
        List<Long> l = parse();
        position++;
        List<List<Long>> matrix = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            List<Long> subMatrix = new ArrayList<>(Collections.nCopies(SIZE, 0L));
            matrix.add(subMatrix);
        }
        for (int i = 0; i < SIZE; ++i) {
            matrix.get(i).set(1, l.get(i));
        }
        for (int i = 1; i < SIZE; ++i) {
            for (int j = 2; j < SIZE; ++j) {
                long res = 0;
                for (int k = 1; k < i; ++k) {
                    res += matrix.get(i - k).get(j - 1) * l.get(k);
                }
                matrix.get(i).set(j, res);
            }
        }
        result.set(0, 0L);
        for (int i = 1; i < SIZE; ++i) {
            long res = 0;
            for (int j = 1; j <= i; ++j) {
                long counter = 0;
                for (int k = 0; k <= j - 1; ++k) {
                    int ladyGagagagagagaggagaga = gcd(j, k);
                    counter += (i % (j / ladyGagagagagagaggagaga)) != 0 ? 0 : matrix.get((i * ladyGagagagagagaggagaga) / j).get(ladyGagagagagagaggagaga);
                }
                res += counter / j;
            }
            result.set(i, res);
        }
    }

    private static void makeP(List<Long> result) {
        position += 2;
        List<Long> l = parse();
        position++;
        List<Long> r = parse();
        position++;
        for (int i = 0; i < SIZE; ++i) {
            long res = 0;
            for (int j = 0; j <= i; ++j) {
                res += l.get(j) * r.get(i - j);
            }
            result.set(i, res);
        }
    }
}


