import java.io.*;
import java.util.*;
public class WordStatInput{
	public static boolean isWord(char arg){
		if (Character.isLetter(arg) || Character.getType(arg) == Character.DASH_PUNCTUATION || 
			arg == '\'') {
			return true;
		}
		else {
			return false;
		}
	}
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(args[0]), "UTF-8")); 
			try {
				BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(args[1]), "UTF-8"));
				String line;
				try{	
					LinkedHashMap <String,Integer> wordStat = new LinkedHashMap <>();
					int start = 0;
					int end = 1;
					while (true){
						line = reader.readLine();
						if (line == null) {
							break;
						}
						line = line.toLowerCase();
						int lenLine = line.length();
						
						for (int charIndex = 0; charIndex < lenLine; charIndex++) {
							char ch = line.charAt(charIndex); //если помеенять местами 34 и 35 , то работает в 1000 раз дольше 
							if (isWord(ch) && charIndex != 0 && isWord(line.charAt(charIndex - 1))) {
								
								end++;
							}

							else if (isWord(ch)) {
								start = charIndex;
								end = charIndex + 1;
							}

							if (((charIndex != lenLine - 1 && !isWord(line.charAt(charIndex + 1)) && isWord(ch))) ||
								(charIndex == lenLine - 1 && isWord(ch)) ) {
								
								if (wordStat.containsKey(line.substring(start,end))) {
									wordStat.replace(line.substring(start,end),wordStat.get(line.substring(start,end)) + 1);
								}
								
								else {
									wordStat.put(line.substring(start,end), 1);

								}
							}
						}
					}
					for (Map.Entry<String,Integer> entry : wordStat.entrySet()){
						writer.write(entry.getKey() + " " + entry.getValue() + "\n");
					}
				}
				finally{
					writer.close();
				}
			}
			catch (IOException e){
				System.out.println("Жаль, но Вы убили прогу:" + e.getMessage());
			}
			finally {
				reader.close();
			}
		}
		catch (IOException e){
			System.out.println("Жаль, но Вы убили прогу:" + e.getMessage());
		}
	}

}
//входной файл
//нет выходного файла
// ошибка счёта строк 