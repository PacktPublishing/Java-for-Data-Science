package packt.webcrawlermavenjsoup;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static java.lang.System.out;

public class JSoupExamples {

    public JSoupExamples() {
        try {
            Document document = Jsoup.connect("https://en.wikipedia.org/wiki/Data_science").get();
            displayImages(document);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        loadDocumentFromFile();
    }

    public void loadDocumentFromFile() {
        try {
            File file = new File("Example.html");
            Document document = Jsoup.parse(file, "UTF-8", "");
            listHyperlinks(document);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void parseString() {
        String html = "<html>\n"
                + "<head><title>Example Document</title></head>\n"
                + "<body>\n"
                + "<p>The body of the document</p>\n"
                + "Interesting Links:\n"
                + "<br>\n"
                + "<a href=\"https://en.wikipedia.org/wiki/Data_science\">Data Science</a>\n"
                + "<br>\n"
                + "<a href=\"https://en.wikipedia.org/wiki/Jsoup\">Jsoup</a>\n"
                + "<br>\n"
                + "Images:\n"
                + "<br>\n"
                + " <img src=\"eyechart.jpg\" alt=\"Eye Chart\"> \n"
                + "</body>\n"
                + "</html>";
        Document document = Jsoup.parse(html);
        listHyperlinks(document);
    }

    public void displayBodyText(Document document) {
        // Displays the entire body of the document
        String title = document.title();
        out.println("Title: " + title);

        out.println("---Body---");
        Elements element = document.select("body");
        out.println("Text: " + element.text());
    }

    public void displayImages(Document document) {
        out.println("---Images---");
        Elements images = document.select("img[src$=.png]");
        for (Element image : images) {
            out.println("\nImage: " + image);
        }
    }

    public void listHyperlinks(Document document) {
        out.println("---Links---");
        Elements links = document.select("a[href]");
        for (Element link : links) {
            out.println("Link: " + link.attr("href")
                    + " Text: " + link.text());
        }
        out.println("\n****************");
    }

    public static void main(String[] args) {
        new JSoupExamples();
    }
}
