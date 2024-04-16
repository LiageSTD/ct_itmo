import java.io.*;
import java.util.*;

public class B2 {

    static class Pair implements Comparable<E.Pair> {
        int first;
        int second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int compareTo(E.Pair o) {
            return Integer.compare(o.first, this.first);
        }
    }
    static class Edge implements Comparable<Edge> {
        int b, e, w, n;

        Edge(int begin, int end, int weight, int order) {
            b = begin;
            e = end;
            w = weight;
            n = order;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(other.w, this.w);
        }
    }

    static List<Integer> dsu;

    static int find(int elem) {
        if (elem == dsu.get(elem)) return elem;
        return dsu.set(elem, find(dsu.get(elem)));
    }

    static void unite(int a, int b) {
        a = find(a);
        b = find(b);
        dsu.set(b, a);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("destroy.in"));
        PrintWriter writer = new PrintWriter(new FileWriter("destroy.out"));

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int n = Integer.parseInt(tokenizer.nextToken());
        int m = Integer.parseInt(tokenizer.nextToken());
        int s = Integer.parseInt(tokenizer.nextToken());

        dsu = new ArrayList<>(Collections.nCopies(n, 0));
        for (int i = 0; i < n; i++)
            dsu.set(i, i);

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int b = Integer.parseInt(tokenizer.nextToken()) - 1;
            int e = Integer.parseInt(tokenizer.nextToken()) - 1;
            int w = Integer.parseInt(tokenizer.nextToken());

            edges.add(new Edge(b, e, w, i + 1));
        }

        Collections.sort(edges);

        Set<Integer> mst = new HashSet<>();
        for (Edge edge : edges) {
            if (find(edge.b) != find(edge.e)) {
                mst.add(edge.n);
                unite(edge.b, edge.e);
            }
        }

        List<Pair> tryDelete = new ArrayList<>();
        for (Edge edge : edges) {
            if (!mst.contains(edge.n)) {
                tryDelete.add(new Pair(edge.w, edge.n));
            }
        }

        tryDelete.sort(Comparator.comparingInt(o -> o.first));

        Set<Integer> ans = new HashSet<>();
        for (Pair pair : tryDelete) {
            if (s >= pair.first) {
                ans.add(pair.second);
                s -= pair.first;
            }
        }

        writer.println(ans.size());
        for (int i : ans) {
            writer.print(i + " ");
        }

        reader.close();
        writer.close();
    }
}
