import java.util.*;
public class ReverseEven {
    public static void main(String[] args) {
        Scanner linesScanner = new Scanner(System.in);
        int[][] nums = new int[1][1];
        int linesCnt = 0;
        int topPos = -1;
        while (linesScanner.hasNextLine()) {
            topPos++;
            linesCnt++;
            if (linesCnt > nums.length) {
                nums = Arrays.copyOf(nums, nums.length * 2);
            }
            String line = linesScanner.nextLine();
            if (line.trim() == "") {
                nums[topPos] = new int[0];
                continue;
            }
            Scanner intScanner = new Scanner(line);
            int intCnt = 0;
            int botPos = -1;
            nums[topPos] = new int[1];
            while (intScanner.hasNextInt()) {
                botPos++;
                intCnt++;
                int num = intScanner.nextInt();
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
        if (nums.length > linesCnt) {
        	nums = Arrays.copyOf(nums, linesCnt);
        } 
        for (int topCounter = nums.length - 1; topCounter >= 0; topCounter--) {     
	        for (int botCounter = nums[topCounter].length - 1; botCounter >= 0; botCounter--) {
	            if (nums[topCounter][botCounter] % 2 == 0) {
                   System.out.print(nums[topCounter][botCounter] + " ");
                
                }
            }
	        System.out.println();
	    }
    }
}    