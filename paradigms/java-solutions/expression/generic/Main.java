package expression.generic;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        GenericTabulator gt = new GenericTabulator();
        System.out.println(Arrays.deepToString(gt.tabulate(sc.next(),sc.next(),-2,2,-2,2,-2,2)));
    }
}
