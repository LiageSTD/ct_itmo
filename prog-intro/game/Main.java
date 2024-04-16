package Game;

import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {


    public static void main(String[] args) {
        int result;
        int m, n, k;
        Scanner reader = new Scanner(System.in);
        System.out.println("Введите m n k");
        m = reader.nextInt();
        n = reader.nextInt();
        k = reader.nextInt();
        while (k > Math.max(m, n)) {
            System.out.println("Вы ввели недопустимое K, пожалуйста выберите его в диапазоне от 0 до " + Math.max(m, n) + ".");
            k = reader.nextInt();
        }
        System.out.println("Введите количество раундов");
        int numOfM = reader.nextInt();
        do {

                final Game game = new Game(false, new HumanPlayer(), new RandomPlayer(m, n));
                result = game.match(m,n,k, numOfM);
                //System.out.println("Game result: " + result);

        } while (result != numOfM) ;

    }
}
