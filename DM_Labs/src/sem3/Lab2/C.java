import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class C {
    static boolean[] used;
    static int[] mt;
    static ArrayList<int[]> graph;

    static boolean try_khun(int v) {
        if (used[v]) return false;
        used[v] = true;
        for (int i = 0; i < graph.get(v).length; i++) {
            int to = graph.get(v)[i];
            if (mt[to] == -1 || try_khun(mt[to])) {
                mt[to] = v;
                return true;
            }
        }
        return false;
    }
    static class Vertex implements Comparable<Vertex> {
        int i;
        int cost;

        Vertex(int pos, int costs) {
            i = pos;
            cost = costs;
        }

        @Override
        public int compareTo(Vertex other) {
            return Integer.compare(other.cost, this.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("matching.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());

        mt = new int[n];

        used = new boolean[n];

        st = new StringTokenizer(br.readLine());

        Vertex[] cost = new Vertex[n];

        for (int i = 0; i < n; i++) {
            cost[i] = new Vertex(i, Integer.parseInt(st.nextToken()));
        }

        Arrays.sort(cost);

        graph = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int c = Integer.parseInt(st.nextToken());
            graph.add(new int[c]);
            for (int j = 0; j < c; j++) {
                graph.get(i)[j] = Integer.parseInt(st.nextToken()) - 1;
            }
        }

        br.close();

        for (int i = 0; i < n; i++) {
            mt[i] = -1;
        }
        for (int i = 0; i < n; i++) {
            int v = cost[i].i;
            for (int j = 0; j < n; j++) {
                used[j] = false;
            }
            try_khun(v);
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter("matching.out"));
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = -1;
        }

        for (int i = 0; i < n; i++) {
            if (mt[i] != -1) {
                res[mt[i]] = i;
            }
        }
        for (int i : res) {
            bw.write(String.valueOf(i + 1));
            bw.write(' ');
        }

        bw.close();
    }
}
