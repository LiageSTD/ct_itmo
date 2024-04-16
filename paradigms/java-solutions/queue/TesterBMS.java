package queue;

public class TesterBMS {
    public static void main(String[] args) {
        switch (args[0]) {
            case "ADT" -> {
                ArrayQueueAdtTestByMySelf.Test();
            }
            case "Module" -> {
                ArrayQueueModuleTestByMySelf.Test();
            }
            case "ArrayQueue" -> {
                ArrayQueueTestByMySelf.Test();
            }
            case "LinkedQueue" -> {
                LinkedQueueTestByMySelf.Test();
            }
        }
    }
}
