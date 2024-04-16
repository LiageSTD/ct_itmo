package search;

import java.util.Scanner;

public class BinarySearch {
//    arr : for i [1;arr.length] arr[i-1] > arr[i]
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
//        присваиваем x
        int[] arr = new int[args.length - 1];
        int c = 0;
        if (args.length == 1) {
//            Ловим нарушение условий, если придёт 1 число ( должно быть 2 минимум)
            System.out.println(Integer.parseInt(args[0]));
        } else {
// arr[i - 1] = args[i] && c += arr[i - 1]
            for (int a = 1; a < args.length; a++) {
                arr[a - 1] = Integer.parseInt(args[a]);
                c += Integer.parseInt(args[a]);
//              присвоение
            }
//          c % 2 == 0 => recursive method  else iterative
            if (c % 2 == 0) {
                System.out.println(bSR(arr,x,0,arr.length));
            } else {
                System.out.println(bSI(arr,x));
            }
//          res

//          System.out.println(bSI(arr, x));
//            System.out.println(bSR(arr, x, 0,arr.length));
        }
    }
//    a отсортирован
    public static int bSI(int[] a, int x) {
        int left = 0;
        int right = a.length;
        int result = a.length;
//      left' < a.length && left' <= right'
        while (left < a.length && left <= right) {
            // mid = (left' + right') / 2
            int mid = (left + right) / 2;

            if (a[mid] <= x) {
//            a[mid] <= x => left >= result >= mid (Т.к. а - отсорт массив, и требуемый элемент встречается где-то в сере
//            дине отрезка, а нам нужно найти такой элемент с минимальным индексом (массив отсортирован в порядке не
//                возрастания, а значит элементы, удовлетворяющие условию могут встретиться раньше
//                (ans < mid || ans = mid)
//              right = mid' - 1 (передвигаем границы итерируемого отрезка, используя логику выше)
                right = mid - 1;
//              присваиваем значение mid' в result, если левее mid не будет подходящих значений
                result = mid;
            } else {
//              a[mid'] > x   =>   mid' < result <= right
//              Т.к. Элемент по центру массива не удовлетворяет условию, и массив отсортирован по убыванию** =>
//                искомый элемент лежит в правом от мида участке (result < mid)
//                Двигаем границу по логике описанной выше
                left = mid + 1;
            }
        }
//      result принадлежит [left;right] || right (самый маленький элемент в массиве) > x

        return result;
    }
//   result == min(i): arr[i] <= x || ( arr[right] (самый маленький элемент в arr) > x) => result = arr.length

    //  int[] arr - отсортированный массив*, left = 0, right = arr.length (на самой первой итерации)
    // int[] arr - отсортированный массив* (для последующих) arr' = arr
    public static int bSR(int[] arr, int x, int left, int right) {
//      arr == [n;n-1] => ответ содержится в значении n, логика описана во втором ретурне ||
//      left == arr.length => массив стал точкой и сомкнулся за пределами массива => значиение, удовлетовящее условию,
//      не содержится в массиве => возвращаем длину массива
        if (left > right || left == arr.length) {
            return left;
        }
        // mid = (right' + left') / 2
        int mid = (right + left) / 2;
        if (arr[mid] > x) {
//      arr[mid] > x => mid > ans >= right
            return bSR(arr, x, mid + 1, right);
//      Двигаем границу по лоигке, описанной выше (left = mid' + 1)
        } else {
//          arr[mid] <= x    =>  left <= ans <= mid
            return bSR(arr, x, left, mid - 1);
//          right = mid - 1;
//          Сдвигаем границу по неравеству выше.
//          Если ответ содержится в mid, то будет вызываться первый ретурн, где сдвигается левая граница, которая остановится
//          в значении mid

        }
    }
}
