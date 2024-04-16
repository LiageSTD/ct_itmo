import java.util.Arrays;

public class IntList {
    private int cntIn = 0;
    private int[] poses = new int[10];

    // evaluate -> eval
    public void evPoses() {
        if (poses.length <= cntIn) {
            poses = Arrays.copyOf(poses, poses.length * 2);
        }

    }

    // copy-paste :: evPoses
    public void evPairPoses() {
        while (poses.length <= cntIn) {
            poses = Arrays.copyOf(poses, poses.length * 2 + 1);
        }
    }

    public void addNumInPairs(int cntLine) {
        cntIn++;
        evPairPoses();
        poses[cntIn - 1] = cntLine;
    }

    public void addNum(int num) {
        cntIn++;
        evPoses();
        poses[cntIn - 1] = num;
    }

    public void trimArr(int num) {
        poses = Arrays.copyOf(poses, num);
    }

    public String ArrToSring() {
        trimArr(cntIn);
        StringBuilder line = new StringBuilder();
        line.append(cntIn + " ");
        for (int posIter = 0; posIter < poses.length; posIter++) {
            // copy-paste
            if (posIter == poses.length - 1) {
                line.append(poses[posIter]);
            } else {
                line.append(poses[posIter] + " ");
            }
        }
        return line.toString();
    }

    public String arrPairsToString() {
        trimArr(cntIn);
        StringBuilder line = new StringBuilder();
        line.append(cntIn / 2 + " ");
        for (int posIter = 0; posIter < poses.length - 1; posIter += 2) {
            if (posIter == poses.length - 2) {
                line.append(poses[posIter] + ":");
                line.append(poses[posIter + 1]);
            } else {
                line.append(poses[posIter] + ":");
                line.append(poses[posIter + 1] + " ");
            }
        }
        return line.toString();
    }
}
