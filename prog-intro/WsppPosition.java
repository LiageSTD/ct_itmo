import java.io.*;
import java.util.*;

public class WsppPosition {

    public static void main(String[] args) {
        try {
            MyScanner readingLines = new MyScanner(new File(args[0]));
            LinkedHashMap<String, IntList> wordStat = new LinkedHashMap<>();
            try {
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(args[1]), "UTF-8"));
                int lineCnt = 0;
                while (readingLines.hasNextLine()) {
                    lineCnt++;
                    int wordCnt = 0;
                    String line = readingLines.nextLine().toLowerCase();
                    MyScanner readingWords = new MyScanner(line);
                    while (readingWords.hasNextWord()) {
                        wordCnt++;
                        String word = readingWords.nextWord();
                        IntList list = wordStat.getOrDefault(word, new IntList());
                        list.addNumInPairs(lineCnt);
                        list.addNumInPairs(wordCnt);
                        wordStat.put(word, list);
                    }
                }
                try {
                    for (Map.Entry<String, IntList> entry : wordStat.entrySet()) {
                        writer.write(entry.getKey() + " " + entry.getValue().arrPairsToString());
                        writer.newLine();
                    }

                } catch (IOException e) {
                    // e.getMessage()
                    e.printStackTrace();
                } finally {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // finally
            readingLines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}