public class SumFloat{
	
	public static void main(String[] args){
		
		float sumAll = 0;	
		for (int lineIndex = 0; lineIndex < args.length; lineIndex++){
			String line = args[lineIndex].strip();
			int end = 0;
			int start = 0;	
			for (int charIndex = 0; charIndex < line.length(); charIndex++){

				if (line.length() == 0){
					
					break;
				}
				if (!Character.isWhitespace(line.charAt(charIndex)) && charIndex != 0 && !Character.isWhitespace(line.charAt(charIndex - 1))){
					
					end++;
				}
				else if (!Character.isWhitespace(line.charAt(charIndex)) && line.length() != 0){
					
					start = charIndex;
					end = charIndex + 1;
				}
				if ( charIndex == line.length() - 1 || (charIndex != line.length() -1 && Character.isWhitespace(line.charAt(charIndex + 1)) && !Character.isWhitespace(line.charAt(charIndex)))){
					
					sumAll += Float.parseFloat(line.substring(start,end));
				}
			} 
		}		
		System.out.println(sumAll);
	}
}
