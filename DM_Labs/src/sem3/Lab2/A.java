import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class A {

    static long time = 0;

    static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("schedule.in"));

        int quantity = Integer.parseInt(reader.readLine());

        getAllTasks(reader, quantity);

        reader.close();

        tasks.sort(Comparator.comparingLong(Task::getDeadline));

        long penalty = countPenalty();

        BufferedWriter writer = new BufferedWriter(new FileWriter("schedule.out"));

        writer.write(Long.toString(penalty));

        writer.close();
    }
    public static void getAllTasks(BufferedReader reader, int quantity) throws IOException {
        for (int i = 0; i < quantity; i++) {
            String[] line = reader.readLine().split(" ");
            tasks.add(new Task(Long.parseLong(line[0]), Long.parseLong(line[1])));
        }
    }
    public static long countPenalty() {
        long penalty = 0;

        PriorityQueue<Long> punish = new PriorityQueue<>();

        for (Task task : tasks) {
            if (time >= task.getDeadline()) {
                if (!punish.isEmpty() && punish.peek() < task.getPenalty()) {
                    penalty += punish.poll();
                    punish.offer(task.getPenalty());
                } else {
                    penalty += task.getPenalty();
                }
            } else {
                punish.offer(task.getPenalty());
                time++;
            }
        }
        return penalty;
    }
    static class Task {
        private final long deadline;
        private final long penalty;

        public Task(long deadline, long penalty) {
            this.deadline = deadline;
            this.penalty = penalty;
        }

        public long getDeadline() {
            return deadline;
        }

        public long getPenalty() {
            return penalty;
        }
    }
}
