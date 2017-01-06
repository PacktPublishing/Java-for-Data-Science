import java.io.BufferedReader;
import java.io.File;
import static java.lang.System.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SimpleSearching {

	public static void main(String[] args) {
		String toFind = "I";
		String replaceWith = "Ishmael";
		String dirtyText = "Call me Ishmael. Some years ago- never mind how";
		dirtyText += " long precisely - having little or no money in my purse,";
		dirtyText += " and nothing particular to interest me on shore, I thought"; 
		dirtyText += " I would sail about a little and see the watery part of the world.";
		
		//simpleSearch(dirtyText,toFind);

		//scannerSearch(dirtyText,toFind);
		
		simpleFindReplace(dirtyText,toFind,replaceWith);
		
		//searchWholeFile("C://Jenn Personal//Packt Data Science//Chapter 3 Data Cleaning//MobyDick.txt", toFind);
		
		try {
			Scanner textToClean = new Scanner(new File("C://Jenn Personal//Packt Data Science//Chapter 3 Data Cleaning//101nos.txt"));
			while(textToClean.hasNext()){
				//String dirtyText = textToClean.nextLine();

				//simpleSearch(dirtyText,toFind);

				//scannerSearch(dirtyText,toFind);
				
				//simpleFindReplace(dirtyText,toFind,replaceWith);

			}

			textToClean.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//searchWholeFile("C://Jenn Personal//Packt Data Science//Chapter 3 Data Cleaning//101nos.txt", toFind);

	}

	public static void simpleSearch(String text, String toFind){
		text = text.toLowerCase().trim();
		toFind = toFind.toLowerCase().trim();
		int count = 0;
		if(text.contains(toFind)){
			String[] words = text.split(" ");
			for(String word : words){
				if(word.equals(toFind)){
					count++;
				}
			}
			out.println("Found " + toFind + " " + count + " times in the text.");
		}
	}
	
	public static void scannerSearch(String text, String toFind){
		text = text.toLowerCase().trim();
		toFind = toFind.toLowerCase().trim();
		Scanner textLine = new Scanner(text);
		//NOTE horizon bound is zero - default to search entire file
		out.println("Found " + textLine.findWithinHorizon(toFind, 10));
	}
	
	public static void simpleFindReplace(String text, String toFind, String replaceWith){
		text = text.toLowerCase().trim();
		toFind = toFind.toLowerCase().trim();
		out.println(text);
		if(text.contains(toFind)){
			text = text.replaceAll(toFind, replaceWith);
			out.println(text);
//			for(String word : textLine){
//				out.print(word + " ");
//			}
		}

	}

	public static void searchWholeFile(String path, String toFind){
		try {
			int line = 0;
			String textLine = "";
			toFind = toFind.toLowerCase().trim();
			BufferedReader textToClean = new BufferedReader(new FileReader(path));
			while((textLine = textToClean.readLine()) != null){
				line++;
				if(textLine.toLowerCase().trim().contains(toFind)){
					out.println("Found " + toFind + " in " + textLine);
					//out.println("Found " + toFind + " on line " + line + " of file.");
//					String[] words = textLine.split(" ");
//					for(int x = 0; x < words.length; x++){
//						if(words[x].equals(toFind)){
//							out.println("On line " + line + " found " + toFind + " at location " + (x-1));
//						}
//					}

				}
			}
			textToClean.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
