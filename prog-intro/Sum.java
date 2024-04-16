
public class Sum{
	
	public static void main(String[] args){
		
		Integer sumAll = 0;	
		for (int z = 0; z < args.length; z++){
			String line = args[z]; 
			String num = "";
			for (int i = 0; i < line.length(); i++){
					
				char[] chLine = line.toCharArray();
				if (line.length() == 1 & !Character.isWhitespace(chLine[i])){
					num += chLine[0];
					sumAll += Integer.parseInt(num);
					num = "";
				}
				else if (!Character.isWhitespace(chLine[i])){	
					num += chLine[i];
					
					if (i != (line.length() - 1) && Character.isWhitespace(chLine[i + 1])){
							
						sumAll += Integer.parseInt(num);
						num = "";
					}
						
					else if (i == (line.length() - 1)){

						sumAll += Integer.parseInt(num);
						num = "";
					}
				}
			} 
		}		
		System.out.println(sumAll);
	}
}