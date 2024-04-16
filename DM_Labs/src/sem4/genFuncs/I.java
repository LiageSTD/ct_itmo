    package sem4;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.util.Arrays;
    import java.util.StringTokenizer;

    public class I {
        private static final int MOD = 104857601;

        public static void main(String[] args) throws IOException {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer st = new StringTokenizer(input.readLine());

            int k = Integer.parseInt(st.nextToken());
            long n = Long.parseLong(st.nextToken()) - 1;

            st = new StringTokenizer(input.readLine());
            long[] A = getA(k, st);

            st = new StringTokenizer(input.readLine());
            long[] Q = getQ(k, st);

            System.out.println(getNth(n, k, A, Q));
        }
        private static long[] getQ(int k, StringTokenizer st) {
            long[] Q = new long[k+ 1];
            Q[0] = 1;
            for (int i = 1; i <= k; i++) {
                Q[i] = (MOD - Long.parseLong(st.nextToken())) % MOD;
            }
            return Q;
        }
        private static long[] getA(int k, StringTokenizer st) {
            long[] A = new long[2 * k];
            for (int i = 0; i < k; i++) {
                A[i] = Long.parseLong(st.nextToken());
            }
            return A;
        }
        private static long getNth(long n, int k, long[] A, long[] Q) {
            long[] mQ;
            long[] rec = new long[k + 1];
            while (n >= k) {
                for (int i = k; i < A.length; i++) {
                    A[i] = 0;
                    for (int j = 1; j < Q.length; j++) {
                        A[i] = (A[i] - A[i - j] * Q[j]) % MOD;
                        while (A[i] < 0) {
                            A[i] += MOD;
                        }
                    }
                }
                mQ = Arrays.copyOf(Q, Q.length);
                for (int i = 1; i < mQ.length; i += 2) {
                    mQ[i] = (MOD - mQ[i]) % MOD;
                }

                for (int i = 0; i <= 2 * k; i += 2) {
                    long cf = 0;
                    for (int j = 0; j <= i; j++) {
                        long a = (j > k) ? 0 : Q[j];
                        long b = (i - j > k) ? 0 : mQ[i - j];
                        cf += a * b + MOD;
                        cf %= MOD;
                    }
                    rec[i / 2] = cf;
                }
                Q = Arrays.copyOf(rec, rec.length);
                int pointer = 0;
                for (int i = 0; i < A.length; i++) {
                    if ((i & 1) == (n & 1)) {
                        A[pointer++] = A[i];
                    }
                }
                n /= 2;
            }
            return A[(int) n];
        }
    }
