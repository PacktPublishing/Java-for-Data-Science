import static java.lang.System.out;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import com.aliasi.tokenizer.EnglishStopTokenizerFactory;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;

public class SimpleStringCleaning {

	public static void main(String[] args) {

		String dirtyText = "Call me Ishmael. Some years ago- never mind how";
		dirtyText += " long precisely - having little or no money in my purse,";
		dirtyText += " and nothing particular to interest me on shore, I thought"; 
		dirtyText += " I would sail about a little and see the watery part of the world.";
		
		//Example 1 - basic clean (regex & String class methods)
		//simpleClean(dirtyText);

		//Example 2 - clean and put in array (split)
		//simpleCleanToArray(dirtyText);

		//Example 3 - join
		//cleanAndJoin(dirtyText);

		//Example 4 - simple remove stop words 
		//removeStopWords(dirtyText);

		//Example 5 - remove stop words with removeAll
		//removeStopWordsRemoveAll(dirtyText);

		//Example 6 - remove stop words with LingPipe
		removeStopWithLing(dirtyText);



	}

	public static String simpleClean(String text){

		out.println("Dirty text: " + text);
		text = text.toLowerCase();
		//explain what each part of this regex does
		text = text.replaceAll("[\\d[^\\w\\s]]+", " ");
		//NOTE trim only works on leading/trailing spaces
		text = text.trim();
		//is this the best way to do this? This isn't great - talk about it even?
		while(text.contains("  ")){
			text = text.replaceAll("  ", " ");
		}		
		out.println("Cleaned text: " + text);
		return text;
	}

	public static String[] simpleCleanToArray(String text){
		out.println("Dirty text: " + text);
		text = text.replaceAll("[\\d[^\\w\\s]]+", "");
		String[] cleanText = text.toLowerCase().trim().split("[\\W\\d]+");
		out.print("Cleaned text: ");
		for(String clean : cleanText){
			out.print(clean + " ");
		}
		out.println();
		return cleanText;
	}

	public static String cleanAndJoin(String text){
		out.println("Dirty text: " + text);
		String[] words = text.toLowerCase().trim().split("[\\W\\d]+");
		String cleanText = String.join(" ", words);
		out.println("Cleaned text: " + cleanText);
		return cleanText;
	}

	public static void removeStopWords(String text){
		//discuss stop words file - how to choose stop words? use whole alphabet as way to handle I'M --> I M

		//****************** SIMPLE EXAMPLE *******************************************************************************************

		try {
			//read in list of stop words
			Scanner readStop = new Scanner(new File("C://Jenn Personal//Packt Data Science//Chapter 3 Data Cleaning//stopwords.txt"));
			//create an ArrayList to hold dirty text - call simpleCleanToArray to perform basic cleaning and put in array first
			ArrayList<String> words = new ArrayList<String>(Arrays.asList(simpleCleanToArray(text)));
			//loop through stop words file and check array for each word
			out.println("Original clean text: " + words.toString());
			ArrayList<String> foundWords = new ArrayList();
			while(readStop.hasNextLine()){
				String stopWord = readStop.nextLine().toLowerCase();
				if(words.contains(stopWord)){
					foundWords.add(stopWord);
				}
			}
			words.removeAll(foundWords);
			out.println("Text without stop words: " + words.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void removeStopWordsRemoveAll(String text){
		//******************EXAMPLE WITH REMOVE ALL *******************************************************************************************

		try {
			out.println(text);
			Scanner stopWordList = new Scanner(new File("C://Jenn Personal//Packt Data Science//Chapter 3 Data Cleaning//stopwords.txt"));
			TreeSet<String> stopWords = new TreeSet<String>();
			while(stopWordList.hasNextLine()){
				stopWords.add(stopWordList.nextLine());
			}
			ArrayList<String> dirtyText = new ArrayList<String>(Arrays.asList(text.split(" ")));
			dirtyText.removeAll(stopWords);
			out.println("Clean words: ");
			for(String x : dirtyText){
				out.print(x + " ");
			}
			out.println();
			stopWordList.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void removeStopWithLing(String text){
		//******************EXAMPLE WITH ling pipe *******************************************************************************************
		//mention lower vs upper case
		out.println(text);
		text = text.toLowerCase().trim();
		TokenizerFactory fact = IndoEuropeanTokenizerFactory.INSTANCE;
		fact = new EnglishStopTokenizerFactory(fact);
		Tokenizer tok = fact.tokenizer(text.toCharArray(), 0, text.length());
		for(String word : tok){
			out.print(word + " ");
		}
	}
}



