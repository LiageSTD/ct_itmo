import java.util.*;
public class ReverseOct {
    public static void main(String[] args) {
        MyScanner linesScanner = new MyScanner(System.in);
        int[][] nums = new int[1][1];
        int topPos = -1;
        while (linesScanner.hasNextLine()) {
            topPos++;
            if (topPos + 1 > nums.length) {

                nums = Arrays.copyOf(nums, nums.length * 2);
            }

            String line = linesScanner.nextLine();

            MyScanner intScanner = new MyScanner(line);
            int intCnt = 0;
            int botPos = -1;
            nums[topPos] = new int[1];
            while (intScanner.hasNext()) {
                botPos++;
                intCnt++;
                int num = Integer.parseUnsignedInt(intScanner.next(), 8);
                if (intCnt > nums[topPos].length) {
                    nums[topPos] = Arrays.copyOf(nums[topPos], nums[topPos].length * 2);
                }
                nums[topPos][botPos] = num;
            }
            intScanner.close();
            if (nums[topPos].length > intCnt) {
                nums[topPos] = Arrays.copyOf(nums[topPos], intCnt);
            }
        }

        linesScanner.close();
        if (nums.length > topPos + 1) {
            nums = Arrays.copyOf(nums, topPos + 1);
        }
        for (int topCounter = nums.length - 1; topCounter >= 0; topCounter--) {
            for (int botCounter = nums[topCounter].length - 1; botCounter >= 0; botCounter--) {
                System.out.print(Integer.toUnsignedString(nums[topCounter][botCounter], 8) + " ");
            }
            System.out.println();
        }
    }
}    