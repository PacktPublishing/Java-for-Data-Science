
import static java.lang.System.out;
import java.util.ArrayList;

public class Java8MapReduceExample2 {

    public static void main(String[] args) {

        ArrayList<Book> books = new ArrayList<>();
        double average;
        int totalPg = 0;

        books.add(new Book("Moby Dick", "Herman Melville", 822));
        books.add(new Book("Charlotte's Web", "E.B. White", 189));
        books.add(new Book("The Grapes of Wrath", "John Steinbeck", 212));
        books.add(new Book("Jane Eyre", "Charlotte Bronte", 299));
        books.add(new Book("A Tale of Two Cities", "Charles Dickens", 673));
        books.add(new Book("War and Peace", "Leo Tolstoy", 1032));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 275));

        totalPg = books
                .stream()
                .parallel()
                .map((b) -> b.pgCnt)
                .reduce(totalPg, (accumulator, _item) -> {
                    out.println(accumulator + " " +_item);
                    return accumulator + _item;
                        });

        average = 1.0 * totalPg / books.size();
        out.println("Average Page Count: " + average);

        average = books
                .parallelStream()
                .map(b -> b.pgCnt)
                .mapToDouble(s -> s)
                .average()
                .getAsDouble();
        out.println("Average Page Count: " + average);
    }

}
