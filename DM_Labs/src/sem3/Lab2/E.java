import java.io.*;
import java.util.*;

public class E {

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

        ArrayList<Pair> costs = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            costs.add(new Pair(Integer.parseInt(st.nextToken()), i));
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

        Set<Integer> answer = new HashSet<>();

        for (int i = 0; i < n; i++) {
            Set<Integer> temp = new HashSet<>(answer);
            temp.add(costs.get(i).second);
            if (!tempContainsCycle(temp)) {
                answer = temp;
            }

        }
        int ans = 0;
        for (Pair el : costs) {
            if (answer.contains(el.second)) {
                ans += el.first;
            }
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("cycles.out"));

        bw.write(String.valueOf(ans));
        bw.close();

    }
    static boolean tempContainsCycle(Set<Integer> temp) {
        for (Set<Integer> curr : cycles) {
            Set<Integer> copyCurr = new HashSet<>(curr);
            copyCurr.removeAll(temp);
            if (copyCurr.isEmpty()) {
                return true;
            }
        }
        return false;
    }

}