import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MyScanner {
    private char defOfBuff;
    private Reader readly;
    private int numOfBuff;
    private  String next;
    private char[] buff = new char[256];
    private StringBuilder bufflines = new StringBuilder();
    private int posInBuff = -1;
    private int num;
    private String line;
    private String word;

    public MyScanner(File name) throws IOException {
        readly =  new InputStreamReader(new FileInputStream(name), StandardCharsets.UTF_8);
    }
    public MyScanner(InputStream sysIn) {
        readly = new BufferedReader(new InputStreamReader(sysIn));
    }
    public MyScanner(String line) {
        readly = new BufferedReader(new StringReader (line));
    }
    public void renewBuff() {
        try{
            numOfBuff = readly.read(buff);
            posInBuff = 0;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void checkPos() {
        if (posInBuff >= numOfBuff && numOfBuff != -1) {
            renewBuff();
        }
    }
    public boolean hasNextLine() {

        if (posInBuff == -1) {
            renewBuff();
        }
        checkPos();
        while (numOfBuff != -1) {
            checkPos();
            defOfBuff = buff[posInBuff];
            if (defOfBuff == '\n') {
                line = bufflines.toString();
                bufflines.setLength(0);
                posInBuff += 1;
                return true;
            } else if (defOfBuff == '\r') {
                line = bufflines.toString();
                bufflines.setLength(0);
                posInBuff++;
                checkPos();    
                if (buff[posInBuff] == '\n') {
                    posInBuff++;
                }
                return true;
            } else {
                bufflines.append(defOfBuff);
                posInBuff++;
            }
        }

//        if (bufflines.length() == 0) {
//            return false;
//        } else {
//            line = bufflines.toString();
//            bufflines.setLength(0);
//            return true;
//        }
        return false;
    }
    public String nextLine() {
        return line;
    }
    public boolean hasNextInt() {
        if (posInBuff == -1) {
            renewBuff();
        }
        checkPos();
        while (posInBuff <= numOfBuff) {
            checkPos();
            if (Character.isDigit(buff[posInBuff]) || buff[posInBuff] == '-') {
                bufflines.append(buff[posInBuff]);
                posInBuff++;
                checkPos();
                if (!Character.isDigit(buff[posInBuff])) {
                    num = Integer.parseInt(new String(bufflines));
                    bufflines.setLength(0);
                    posInBuff++;
                    return true;
                }
            } else {
                posInBuff++;
            }
        }
        if (bufflines.length() != 0) {
            num = Integer.parseInt(bufflines.toString());
            bufflines.setLength(0);
            return true;
        }
        else {
            return false;
        }
    }
    public Integer nextInt() {
        return num;
    }
    public boolean hasNextWord() {
        if (posInBuff == -1) {
            renewBuff();
        }
        checkPos();
        while (numOfBuff != -1) {
            checkPos();
            if (isWord(buff[posInBuff])) {
                bufflines.append(buff[posInBuff]);
                posInBuff++;
                checkPos();
                if (!isWord(buff[posInBuff])) {
                    word = bufflines.toString();
                    bufflines.setLength(0);
                    posInBuff++;

                    return true;
                }
            } else {
                posInBuff++;
                checkPos();
            }
        }
        if (bufflines.length() == 0) {
            return false;
        } else {
            word = bufflines.toString();
            bufflines.setLength(0);
            return true;
        }
    }

    public String nextWord() {
        return word;
    }
    public boolean hasNext() {
        if (posInBuff == -1) {
            renewBuff();
        }
        while (numOfBuff != -1) {
            defOfBuff = buff[posInBuff];
            if (!Character.isWhitespace(defOfBuff)) {
                bufflines.append(defOfBuff);
                posInBuff++;
                checkPos();
                if (Character.isWhitespace(buff[posInBuff])) {
                    next = bufflines.toString();
                    posInBuff++;
                    checkPos();
                    bufflines.setLength(0);
                    return true;
                }

            } else {
              posInBuff++;
              checkPos();
            }
        }
        if (bufflines.length() == 0) {
            return false;
        } else {
            next = bufflines.toString();
            bufflines.setLength(0);
            return true;
        }

    }
    public String next() {
        return next;
    }
    public  boolean isWord(char arg){
        return Character.isLetter(arg) || Character.getType(arg) == Character.DASH_PUNCTUATION ||
                arg == '\'';
    }
    public void close() {
        try{readly.close();}
        catch(IOException e){e.printStackTrace();}
    }
}