package packt.com.packt.java.guava;

import static java.lang.System.out;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		String dirtyText = "Call me Ishmael. Some years ago- never mind how";
		dirtyText += " long precisely - having little or no money in my purse,";
		dirtyText += " and nothing particular to interest me on shore, I thought"; 
		dirtyText += " I would sail about a little and see the watery part of the world.";
    	
//		Splitter split = Splitter.on(',').omitEmptyStrings().trimResults();
//    	Iterable<String> words = split.split(dirtyText); 
//    	for(String token: words){
////    	 out.println(token);
//    	}
    	//cleanAndJoin(dirtyText);
    	findReplaceGuava(dirtyText);
    	
    }
    
	public static String cleanAndJoin(String text){
		out.println("Dirty text: " + text);
		String[] words = text.toLowerCase().trim().split("[\\W\\d]+");
		String cleanText = Joiner.on(" ").skipNulls().join(words);
		out.println("Cleaned text: " + cleanText);
		return cleanText;
	}
	

	public static String findReplaceGuava(String text){
		out.println(text);
		text = text.replace("me", " ");
		out.println("With double spaces: " + text);

		  // trim whitespace at ends, and replace/collapse whitespace into single spaces
		String spaced = CharMatcher.WHITESPACE.trimAndCollapseFrom(text, ' ');
		out.println("With double spaces removed: " + spaced);
		String noDigits = CharMatcher.JAVA_DIGIT.replaceFrom(text, "*"); // star out all digits
		String lowerAndDigit = CharMatcher.JAVA_DIGIT.or(CharMatcher.JAVA_LOWER_CASE).retainFrom(text);
		  // eliminate all characters that aren't digits or lowercase
		
		return text;
	}
}
