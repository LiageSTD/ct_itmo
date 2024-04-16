import java.io.*;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Set;
import java.util.StringTokenizer;

public class D {
    static Set<Set<Integer>> S;

    static boolean checkSecond() {
        for (Set<Integer> a : S){
            for (int mask = 0; mask < (1 << a.size()); mask++) {
                Set<Integer> subset = new TreeSet<>();
                int i = 0;
                for (int x : a) {
                    if ((mask & (1 << i)) != 0) {
                        subset.add(x);
                    }
                    i++;
                }
                if (!S.contains(subset)) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean checkThird() {
        for (Set<Integer> first : S) {
            for (Set<Integer> second : S) {
                if (first.size() > second.size()) {
                    Set<Integer> unique = new TreeSet<>(first);
                    unique.removeAll(second);
                    boolean mayBe = false;
                    for (int uni : unique) {
                        Set<Integer> prime = new TreeSet<>(second);
                        prime.add(uni);
                        if (S.contains(prime)) {
                            mayBe = true;
                        }
                    }
                    if (!mayBe) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("check.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        S = new HashSet<>();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int s = Integer.parseInt(st.nextToken());

            TreeSet<Integer> toAdd = new TreeSet<>();

            for (int j = 0; j < s; j++) {
                toAdd.add(Integer.parseInt(st.nextToken()) - 1);
            }
            S.add(toAdd);
        }
        br.close();

        if (S.contains(new TreeSet<Integer>()) && checkSecond() && checkThird()) {
            BufferedWriter bw = new BufferedWriter(new FileWriter("check.out"));
            bw.write("YES");
            bw.close();
        } else {
            BufferedWriter bw = new BufferedWriter(new FileWriter("check.out"));
            bw.write("NO");
            bw.close();
        }

    }
}
