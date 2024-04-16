import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class E_Reworked {
    static Set<Set<Integer>> cycles;
    static int n;

    static class Pair implements Comparable<Pair> {
        int first;
        int second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int compareTo(Pair o) {
            return Integer.compare(o.first, this.first);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("cycles.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());

        int m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        ArrayList<E.Pair> costs = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            costs.add(new E.Pair(Integer.parseInt(st.nextToken()), i));
        }
        cycles = new HashSet<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int cnt = Integer.parseInt(st.nextToken());
            Set<Integer> toAdd = new HashSet<>();
            for (int j = 0; j < cnt; j++) {
                int t = Integer.parseInt(st.nextToken()) - 1;
                toAdd.add(t);
            }
            cycles.add(toAdd);
        }
        br.close();

        Collections.sort(costs);

        Set<Integer> res = new HashSet<>();

        for (int i = 0; i < n; i++) {

        }
    }
}
