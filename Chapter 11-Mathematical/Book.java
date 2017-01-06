
public class Book {

    public String title;
    public String author;
    public int pgCnt;

    public Book() {
        title = "";
        author = "";
        pgCnt = 0;
    }

    public Book(String t, String a, int p) {
        title = t;
        author = a;
        pgCnt = p;
    }
    
    public int getPgCnt(){
        return pgCnt;
    }

}
