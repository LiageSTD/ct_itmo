package search;

import java.util.Arrays;

public class BinarySearchMax {
    //    arr: for [0,k - 1] : arr[k] < arr[k + 1], for [k,arr.length] arr[k] > arr[k + 1], arr[k - 1] > arr[arr.length - 1]
    public static void main(String[] args) {
        int[] arr = new int[args.length];
        //создаём массив
        if (args.length == 1) {
            System.out.println(args[0]);
            // если идёт нарукшение изначальных условий, то выкидываем ответ сами
        } else {
            for (int a = 0; a < args.length; a++) {
                arr[a] = Integer.parseInt(args[a]);
                // Простое присвоение
            }
            System.out.println(bSM(arr));
        // res = arr[i]
        }
    }

    // a - отсортированный массив с циклическим сдвигом (строчка 6)
    public static int bSM(int[] arr) {
        int left = -1;
        int right = arr.length;
        int n = arr.length;

//      left' < right' Сохранение данных условий в цикле позволяет сказать, что ans == left
//      right' != left'
//      left <= ans < right
        while (left < right - 1) {
//          mid = (left' + right') / 2 (ставим новое значение мида - по центру нынешнего отрезка)
//          left < mid < right
            int mid = (left + right) / 2;

            if (arr[mid] > arr[n - 1]) {
//           arr[mid] > arr[n-1] => mid <= ans <= right - 1

                left = mid;
//           left <= ans <= right
            } else {
//          arr[mid] <= arr[n-1]    =>     left + 1 <= ans < mid
                right = mid;
//          left <= ans < right
//  Т.к мы присваиваем left or right => границы поиска сужаются
            }
        }

        // left >= right - 1  =>
        if (left < 0) {
//            arr[left] < arr[right]
            // left < 0 => сдвиг равен длинне массива (левая граница не сдвинулась) => ans = arr[n-1] (из-за сортировки массива)
            return arr[n - 1];
        }
        // left >= 0    => arr[left] >= arr[i]
        return arr[left];
    }
//    res = arr[i]
}
