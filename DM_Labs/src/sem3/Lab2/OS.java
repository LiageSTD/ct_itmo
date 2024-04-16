import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OS {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream ("res.log"));
        System.out.println(sc.next());
    }
}
