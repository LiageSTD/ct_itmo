package Game;

import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
        String line = "1 2 3       6";
        Scanner bob = new Scanner(System.in);
        System.out.println(bob.next());
        Scanner billy = new Scanner(bob.nextLine());
        System.out.println(billy.next());
    }
}
