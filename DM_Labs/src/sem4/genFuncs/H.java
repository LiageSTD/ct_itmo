package sem4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class H {
    static final int MOD = 998_244_353;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int k = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        long[] A = new long[(k - 2) / 2 + 2];
        long[] Q = new long[(k - 1) / 2 + 1];

        for (int i = 0; i <= (k - 2) / 2; i++) {
            A[i + 1] = ((i % 2 == 0 ? 1 : -1) * binC(k - (i + 2), i)) % MOD;
            A[i + 1] += MOD;
            A[i + 1] %= MOD;
        }
        for (int i = 0; i <= (k - 1) / 2; i++) {
            Q[i] = ((i % 2 == 0 ? 1 : -1) * binC(k - (i + 1), i)) % MOD;
            Q[i] += MOD;
            Q[i] %= MOD;
        }

        long[] res = divide(A, Q, n);

        StringBuilder r = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            r.append(res[i]).append("\n");
        }
        System.out.println(r);
    }

    public static long[] gcd(long a, long b, long x, long y) {
        if (a == 0) {
            x = 0;
            y = 1;
            return new long[] {b,x,y};
        }
        long x1 = 0;
        long y1 = 0;
        long[] res = gcd(b % a, a, x1, y1);
        x1 = res[1];
        y1 = res[2];
        res[1] = y1 - (b / a) * x1;
        res[2] = x1;
        return res;
    }

    public static long binC(long n, long k) {
        long res = 1;
        for (long i = 0; i < k; i++) {
            res *= (n - i);
            res %= MOD;
            res += MOD;
            res %= MOD;
        }

        for (long j = 0; j < k; j++) {
            res *= rev(j + 1);
            res %= MOD;
            res += MOD;
            res %= MOD;
        }
        return res;
    }

    public static long[] divide(long[] left, long[] right, int n) {
        long[] result = new long[n + 1];
        for (int i = 0; i < n + 1; i++) {
            result[i] = i >= left.length ? 0 : left[i];
            for (int j = Math.max(i - right.length + 1, 0); j < i; j++) {
                result[i] = (result[i] - ((result[j] * right[i - j]) % MOD) + MOD) % MOD;
            }
            result[i] *= rev(right[0]);
            result[i] %= MOD;
        }
        return result;
    }

    private static long rev(long i) {
        return (gcd(i, MOD,0,0)[1] % MOD + MOD) % MOD;
    }

}
