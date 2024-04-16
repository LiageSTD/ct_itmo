import java.io.*;
import java.util.*;

public class Wspp {

    public static void main(String[] args) {
        try {
            MyScanner readingLines = new MyScanner(new File(args[0]));
            LinkedHashMap<String, IntList> wordStat = new LinkedHashMap<>();
            try {
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(args[1]), "UTF-8"));
                int wordCnt = 0;
                while (readingLines.hasNextLine()) {

                    String line = readingLines.nextLine().toLowerCase();
                    MyScanner readingWords = new MyScanner(line);
                    while (readingWords.hasNextWord()) {
                        wordCnt++;
                        String word = readingWords.nextWord();
                        IntList list = wordStat.getOrDefault(word, new IntList());
                        list.addNum(wordCnt);
                        wordStat.put(word, list);
                    }
                }
                try {
                    for (Map.Entry<String, IntList> entry : wordStat.entrySet()) {
                        writer.write(entry.getKey() + " " + entry.getValue().ArrToSring());
                        writer.newLine();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            readingLines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
//	public static void main1(String[] args) {
//
//			MyScanner readingLines = new MyScanner(args[0]);
//			try {
//				BufferedWriter writer = new BufferedWriter(
//                new OutputStreamWriter(
//                        new FileOutputStream(args[1]), "UTF-8"));
//				String line;
//				try{
//					LinkedHashMap <String,Integer> wordStat = new LinkedHashMap <>();
//					int start = 0;
//					int end = 1;
//					while (true){
//						line = readingLines.nextLine();
//						line = line.toLowerCase();
//						int lenLine = line.length();
//
//						for (int charIndex = 0; charIndex < lenLine; charIndex++) {
//							char ch = line.charAt(charIndex); //если помеенять местами 34 и 35 , то работает в 1000 раз дольше
//							if (isWord(ch) && charIndex != 0 && isWord(line.charAt(charIndex - 1))) {
//
//								end++;
//							}
//
//							else if (isWord(ch)) {
//								start = charIndex;
//								end = charIndex + 1;
//							}
//
//							if (((charIndex != lenLine - 1 && !isWord(line.charAt(charIndex + 1)) && isWord(ch))) ||
//								(charIndex == lenLine - 1 && isWord(ch)) ) {
//
//								if (wordStat.containsKey(line.substring(start,end))) {
//									wordStat.replace(line.substring(start,end),wordStat.get(line.substring(start,end)) + 1);
//								}
//
//								else {
//									wordStat.put(line.substring(start,end), 1);
//
//								}
//							}
//						}
//					}
//					for ( Map.Entry<String,Integer> entry : wordStat.entrySet()){
//						writer.write(entry.getKey() + " " + entry.getValue());
//						writer.newLine();
//					}
//				}
//				finally{
//					writer.close();
//				}
//			}
//			catch (IOException e){
//				System.out.println("Жаль, но Вы убили прогу:" + e.getMessage());
//			}
//			finally {
//				readingLines.close();
//			}
//
//
//
//}

//входной файл
//нет выходного файла
// ошибка счёта строк 