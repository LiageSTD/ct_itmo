package sem4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class A {
    private static StringBuilder result = new StringBuilder();

    private static final int MOD = 998_244_353;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stringTokenizer = new StringTokenizer(input.readLine());
        int n = Integer.parseInt(stringTokenizer.nextToken());
        int m = Integer.parseInt(stringTokenizer.nextToken());

        int[] firstM = fillArrays(input, n);
        int[] secondM = fillArrays(input, m);

        int[] res = add(firstM, secondM);
        getResult(res);
        System.out.println(result);
        result.setLength(0);

        res = multiply(firstM, secondM);
        getResult(res);
        System.out.println(result);
        result.setLength(0);

        res = divide(firstM, secondM);
        getResultForDivision(res);
        System.out.println(result);
    }
    private static int[] fillArrays(BufferedReader input, int size) throws IOException {
        StringTokenizer stringTokenizer = new StringTokenizer(input.readLine());
        int[] firstM = new int[size+1];
        for (int i = 0; i < firstM.length; i++) {
            firstM[i] = Integer.parseInt(stringTokenizer.nextToken());
        }
        return firstM;
    }
    private static int[] add(int[] left, int[] right) {
        int[] biggestArr = left.length > right.length ? left : right;
        biggestArr = Arrays.copyOf(biggestArr, biggestArr.length);
        int[] smallestArr = left.length > right.length ? right : left;
        for (int i = 0; i < smallestArr.length; i++) {
            biggestArr[i] = (smallestArr[i] + biggestArr[i]) % MOD;
        }
        return biggestArr;
    }
    private static int[] multiply(int[] left, int[] right) {
        int[] biggestArr = left.length > right.length ? left : right;
        int[] smallestArr = left.length > right.length ? right : left;
        int[] result = new int[left.length + right.length - 1];
        for (int i = 0; i < biggestArr.length; i++) {
            for(int j = 0; j < smallestArr.length; j++) {
                result[i+j] += (int) (((long) biggestArr[i] * smallestArr[j]) % MOD);
                result[i+j] %= MOD;
            }
        }
        return result;
    }
    public static int[] divide(int[] left, int[] right) {
        int[] result = new int[1000];
        for (int i = 0; i < 1000; i++) {
            result[i] = i >= left.length ? 0 : left[i];
            for (int j = Math.max(i - right.length + 1, 0); j < i; j++) {
                result[i] = (result[i] - (int) (((long) result[j] * right[i - j]) % MOD) + MOD) % MOD;
            }
        }
        return result;
    }
    private static void getResult(int[] arr) {
        int s = arr.length - 1;
        while (s >= 0 && arr[s] == 0) {
            s--;
        }
        result.append(s)
                .append("\n");
        for (int i = 0; i <= s; i++) {
            result.append(arr[i]).append(" ");
        }
    }
    private static void getResultForDivision(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            result.append(arr[i]);
            if (i != arr.length - 1) {
                result.append(" ");
            }
        }
    }
}
