package sem4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class C {

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        int k = Integer.parseInt(input.readLine());
        int[] f = new int[k];
        int[] c = new int[k];
        int[] p = new int[k + 1];
        int[] q = new int[k + 1];

        StringTokenizer st = new StringTokenizer(input.readLine());
        for (int i = 0; i < k; i++) {
            f[i] = Integer.parseInt(st.nextToken());
        }
        q[0] = 1;

        st = new StringTokenizer(input.readLine());

        for (int i = 0; i < k; i++) {
            c[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 1; i <= k; i++) {
            q[i] = -c[i - 1];
        }
        for (int i = 0; i < k; i++) {
            long sum = 0L;
            for (int j = 0; j < i; j++)
                sum += (long) c[j] * f[i - j - 1];
            p[i] = (int) (f[i] - sum);
        }


        for (int i = k; i >= 0; i--) {
            if (p[i] != 0) break;
            k--;
        }

        System.out.println(k);
        for (int i = 0; i <= k; i++) System.out.print(p[i] + " ");
        System.out.println();

        System.out.println(q.length - 1);
        for (Integer i : q) System.out.print(i + " ");
    }
}

