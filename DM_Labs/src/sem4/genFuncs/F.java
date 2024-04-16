package sem4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class F {
    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int k = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());


        st = new StringTokenizer(input.readLine());
        ArrayList<Integer> nodes = getNodes(k, st);

        getRes(m, nodes);
    }
    private static ArrayList<Integer> getNodes(int k, StringTokenizer st) {
        ArrayList<Integer> pool = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            pool.add(Integer.parseInt(st.nextToken()));
        }
        return pool;
    }
    private static void getRes(int m, ArrayList<Integer> pool) {

        long[] c = new long[m + 1];
        long[] p = new long[m + 1];

        c[0] = 1;

        p[0] = 1;

        for (int i = 1; i <= m; i++) {
            for (int el : pool) {
                if (el > i) {
                    continue;
                }
                c[i] += p[i - el];
                c[i] %= MOD;
            }

            norm(p, c, i);

            System.out.print(c[i] + " ");
        }
    }
    private static void norm(long[] p, long[] c, int i) {
        for (int j = 0; j <= i; j++) {
            p[i] += c[j] * c[i-j];
            p[i] %= MOD;
        }
    }
}

