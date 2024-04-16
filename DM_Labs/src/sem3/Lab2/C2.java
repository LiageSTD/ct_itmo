//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class Matching {
//
//    private int n;
//    private List<List<Integer>> graph;
//    private int[] match;
//    private int[] backMatch;
//    private boolean[] used;
//
//    private class Vertex {
//        private int v;
//        private int w;
//
//        public Vertex(int v, int w) {
//            this.v = v;
//            this.w = w;
//        }
//
//        public int getV() {
//            return v;
//        }
//
//        public int getW() {
//            return w;
//        }
//    }
//
//    public boolean kuhnAlgo(int v) {
//        if (used[v]) {
//            return false;
//        }
//        used[v] = true;
//        for (Integer to : graph.get(v)) {
//            if (match[to] == -1 || kuhnAlgo(match[to])) {
//                match[to] = v;
//                backMatch[v] = to;
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void findGreedyMatching() {
//        for (int i = 0; i < n; ++i) {
//            int v = order.get(i).getV();
//            used = new boolean[n];
//            kuhnAlgo(v);
//        }
//    }
//
//    public void readInput() throws Exception {
//        Scanner scanner = new Scanner(System.in);
//        n = scanner.nextInt();
//        graph = new ArrayList<>();
//        for (int i = 0; i < n; ++i) {
//            graph.add(new ArrayList<>());
//        }
//        match = new int[n];
//        backMatch = new int[n];
//        used = new boolean[n];
//
//        for (int i = 0; i < n; ++i) {
//            int w = scanner.nextInt();
//            order.add(new Vertex(i, w));
//        }
//
//        for (int i = 0; i < n; ++i) {
//            int u = scanner.nextInt();
//            for (int j = 0; j < u; ++j) {
//                int v = scanner.nextInt();
//                v--;
//                graph.get(i).add(v);
//            }
//        }
//
//        Arrays.sort(order.toArray(new Vertex[n]), (v1, v2) -> v2.getW() - v1.getW());
//    }
//
//    public void writeOutput() {
//        for (int i = 0; i < n; ++i) {
//            System.out.print(backMatch[i] + 1 + " ");
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        Matching matching = new Matching();
//        matching.readInput();
//        matching.findGreedyMatching();
//        matching.writeOutput();
//    }
//}
