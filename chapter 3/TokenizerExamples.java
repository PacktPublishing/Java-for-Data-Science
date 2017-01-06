import static java.lang.System.out;

import java.util.StringTokenizer;

public class TokenizerExamples{
	
	public static void main(String[] args){
		
		String dirtyText = "Call me Ishmael. Some years ago- never mind how";
		dirtyText += " long precisely - having little or no money in my purse,";
		dirtyText += " and nothing particular to interest me on shore, I thought"; 
		dirtyText += " I would sail about a little and see the watery part of the world.";
		
		StringTokenizer tokenizer = new StringTokenizer(dirtyText," ");
		while(tokenizer.hasMoreTokens()){
			out.print(tokenizer.nextToken() + " ");
		}
	}
}