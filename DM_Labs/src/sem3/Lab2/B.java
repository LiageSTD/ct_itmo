import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class B {
    static class Edge implements Comparable<Edge> {
        int from;
        int  to;
        BigInteger cost;
        int rd;
        boolean used = false;

        Edge(int from, int to, BigInteger costs, int rd) {
            this.from = from;
            this.to = to;
            this.cost = costs;
            this.rd = rd;
        }

        @Override
        public int compareTo(Edge other) {
            return other.cost.compareTo(cost);
        }
    }

    static ArrayList<Integer> p = new ArrayList<>();
    static ArrayList<Integer> r = new ArrayList<>();

    static int find(int v) {
        if (v == p.get(v)) {
            return v;
        } else {
            p.set(v, find(p.get(v)));
            return p.get(v);
        }
    }

    static void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a != b) {
            if (r.get(a) < r.get(b)) {
                int r = a;
                a = b;
                b = r;
            }
            p.set(b, a);
            if (Objects.equals(r.get(a), r.get(b))) {
                r.set(a, r.get(a) + 1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader rb = new BufferedReader(new FileReader("destroy.in"));
        StringTokenizer st = new StringTokenizer(rb.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        BigInteger s = BigInteger.valueOf(Long.parseLong(st.nextToken()));

        for (int i = 0; i < n; i++) {
            p.add(i);
            r.add(0);
        }
        ArrayList<Edge> edges = new ArrayList<>();
        BigInteger allSum = BigInteger.ZERO;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(rb.readLine());
            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;
            BigInteger costs = BigInteger.valueOf(Long.parseLong(st.nextToken()));
            allSum = allSum.add(costs);
            edges.add(new Edge(from,to,costs,i));
        }
        rb.close();
        Collections.sort(edges);
        BigInteger sumTemp = BigInteger.ZERO;
        for (int i = 0; i < m; i++) {
            Edge curr = edges.get(i);
            if (find(curr.from) !=find(curr.to)) {
                union(curr.from, curr.to);
                sumTemp = sumTemp.add(curr.cost);
                curr.used = true;
                edges.set(i,curr);
            }
        }
        int pointer = 0;
        while (sumTemp.add(s).compareTo(allSum) < 0) {
            Edge curr = edges.get(pointer);
            if (!curr.used) {
                curr.used = true;
                edges.set(pointer,curr);
                sumTemp = sumTemp.add(curr.cost);
            }
            pointer++;
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for (Edge el : edges) {
            if (!el.used) {
                ans.add(el.rd);
            }
        }
        Collections.sort(ans);
        BufferedWriter br = new BufferedWriter(new FileWriter("destroy.out"));
        br.write(String.valueOf(ans.size()));
        br.newLine();
        for (int el : ans) {
            br.write(String.valueOf(el + 1));
            br.write(" ");
        }
        br.close();
    }
}