import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static java.lang.System.out;

public class SimpleSort {

	public static void main(String[] args) {

		basicSort();
		complexSort();

	}


	public static void basicSort(){

		//make original list and print
		String[] words = {"cat","dog","house","boat","road","zoo"};
		ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(words));
		Integer[] nums = {12,46,52,34,87,123,14,44};
		ArrayList<Integer> numsList = new ArrayList<>(Arrays.asList(nums));

		out.println("Original Word List: " + wordsList.toString());
		//simple sort with collections.sort()
		Collections.sort(wordsList);
		out.println("Ascending Word List: " + wordsList.toString());

		out.println("Original Integer List: " + numsList.toString());

		Collections.reverse(numsList);
		out.println("Reversed Integer List: " + numsList.toString());

		Collections.sort(numsList);
		out.println("Ascending Integer List: " + numsList.toString());

		//Use Comparator Interface
		Comparator<Integer> basicOrder = Integer::compare;
		Comparator<Integer> descendOrder = basicOrder.reversed();
		Collections.sort(numsList,descendOrder);
		out.println("Descending Integer List: " + numsList.toString());

		//Using a lambda expression with Comparator and Collections
		Comparator<Integer> compareInts = (Integer first, Integer second) -> Integer
				.compare(first, second);
		Collections.sort(numsList,compareInts);
		out.println("Sorted integers using Lambda: " + numsList.toString());

		Comparator<String> basicWords = String::compareTo;
		Comparator<String> descendWords = basicWords.reversed();
		Collections.sort(wordsList,descendWords);
		out.println("Reversed Words Using Comparator: " + wordsList.toString());

		Comparator<String> compareWords = (String first, String second) -> first.compareTo(second);
		Collections.sort(wordsList,compareWords);
		out.println("Sorted words using Lambda: " + wordsList.toString());
	}


	public static void complexSort() {
		out.println();
		ArrayList<Dogs> dogs = new ArrayList<Dogs>();
		dogs.add(new Dogs("Zoey", 8));
		dogs.add(new Dogs("Roxie", 10));
		dogs.add(new Dogs("Kylie", 7));
		dogs.add(new Dogs("Shorty", 14));
		dogs.add(new Dogs("Ginger", 7));
		dogs.add(new Dogs("Penny", 7));
		out.println("Name " + " Age");
		for(Dogs d : dogs){
			out.println(d.getName() + " " + d.getAge());
		}
		out.println();
		dogs.sort(Comparator.comparing(Dogs::getName).thenComparing(Dogs::getAge));
		out.println("Name " + " Age");
		for(Dogs d : dogs){
			out.println(d.getName() + " " + d.getAge());
		}
		out.println();
		dogs.sort(Comparator.comparing(Dogs::getAge).thenComparing(Dogs::getName));
		out.println("Name " + " Age");
		for(Dogs d : dogs){
			out.println(d.getName() + " " + d.getAge());
		}
		out.println();
	}
}

