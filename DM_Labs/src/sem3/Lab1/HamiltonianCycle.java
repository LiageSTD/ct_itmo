import java.util.Scanner;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

class Graph {
    private boolean[][] matrix;
    private LinkedList<Integer> vertices;
    private int size;

    public Graph(int size) {
        matrix = new boolean[size + 1][size + 1];
        vertices = new LinkedList<>();
        this.size = size;
    }

    public void addEdge(int i, int j, boolean flag) {
        matrix[i][j] = matrix[j][i] = flag;
    }

    public void addVertex(int i) {
        vertices.add(i);
    }

    private Optional<Integer> getByDirak() {
        for (int i = 2; i < vertices.size(); i++) {
            if (matrix[vertices.getFirst()][vertices.get(i)] && matrix[vertices.get(1)][vertices.get(i + 1)]) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    private int getByChvatal() {
        for (int i = 1; i < vertices.size(); i++) {
            if (matrix[vertices.getFirst()][vertices.get(i)]) {
                return i;
            }
        }
        return -1;
    }

    public void findHamiltonianCycle() {
        for (int counter = 0; counter < size * (size - 1); counter++) {
            if (matrix[vertices.getFirst()][vertices.get(1)]) {
                vertices.addLast(vertices.pollFirst());
            } else {
                Optional<Integer> found = getByDirak();
                int i;
                if (found.isPresent()) {
                    i = found.get();
                } else {
                    i = getByChvatal();
                }
                for (int j = 0; ; j++) {
                    if (1 + j < i - j) {
                        int temp = vertices.get(1 + j);
                        vertices.set(1 + j, vertices.get(i - j));
                        vertices.set(i - j, temp);
                    } else {
                        break;
                    }
                }
                vertices.addLast(vertices.pollFirst());
            }
        }
    }

    public Deque<Integer> getVertices() {
        return vertices;
    }
}

public class HamiltonianCycle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Graph g = new Graph(n);
        String currLine = "";
        for (int i = 1; i < n + 1; i++) {
            char c;
            g.addVertex(i);
            for (int j = 1; j < i; j++) {
                if (j == 1) {
                    currLine = scanner.next();
                }
                c = currLine.charAt(j-1);
                g.addEdge(i, j, c != '0');
            }
        }

        g.findHamiltonianCycle();
        Deque<Integer> vertices = g.getVertices();
        for (int item : vertices) {
            System.out.print(item + " ");
        }
    }
}
