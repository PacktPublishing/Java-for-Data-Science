
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.stream.Collectors.toCollection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.out;

public class SimpleSubsetting {

	public static void main(String[] args) throws FileNotFoundException {

		//	treeSubSetMethod();
		//		simpleSubSet();
		subSetSkipLines();

	}

	public static void treeSubSetMethod(){

		//sub set is not populating - not sure why
		//http://www.tutorialspoint.com/java/util/treeset_subset.htm

		Integer[] nums = {12,46,52,34,87,123,14,44};
		TreeSet <Integer> fullNumsList = new TreeSet<Integer>(new ArrayList<>(Arrays.asList(nums)));
		TreeSet <Integer> partNumsList = new TreeSet<Integer>();
		out.println("Original List: " + fullNumsList.toString());
		partNumsList = (TreeSet<Integer>) fullNumsList.subSet(1,3); 
		out.println("SubSet of List: " + partNumsList.toString());
		out.println(partNumsList.size());


	}    

	public static void simpleSubSet(){
		Integer[] nums = {12,46,52,34,87,123,14,44};
		ArrayList<Integer> numsList = new ArrayList<>(Arrays.asList(nums));
		out.println("Original List: " + numsList.toString());
		Set<Integer> fullNumsList = new TreeSet<Integer>(numsList);
		Set<Integer> partNumsList = fullNumsList.stream().skip(5).collect(toCollection(TreeSet::new));
		out.println("SubSet of List: " + partNumsList.toString());

	}

	public static void subSetSkipLines() throws FileNotFoundException{

		//not behaving as expected
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Jenn Personal\\Packt Data Science\\Chapter 3 Data Cleaning\\stopwords.txt"))) {
			br
			.lines()
			.filter(s -> !s.equals(""))
			.forEach(s -> out.println(s));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		//Scanner file = new Scanner(new File("C:\\Jenn Personal\\Packt Data Science\\Chapter 3 Data Cleaning\\stopwords.txt"));
		//		ArrayList<String> lines = new ArrayList<>();
		//		while(file.hasNextLine()){
		//			lines.add(file.nextLine());
		//		}
		//		out.println("Original List: " + lines.toString());
		//		out.println("Original list is " + lines.size() + " elements long");
		//		Set<String> fullWordsList = new TreeSet<String>(lines);
		//		Set<String> partWordsList = fullWordsList.stream().skip(2).collect(toCollection(TreeSet::new));
		//		out.println("SubSet of List: " + partWordsList.toString());
		//		out.println("Subsetted list is " + partWordsList.size() + " elements long");
		//		
		//		file.close();
	}
}
