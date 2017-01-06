package packt.webcrawlermavenexample;

import java.io.IOException;
import java.sql.SQLException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static java.lang.System.out;
import java.util.ArrayList;

// Adapted from: http://www.programcreek.com/2012/12/how-to-make-a-web-crawler-using-java/
public class SimpleWebCrawler {

    private final String topic;
    private final String startingURL;
    private final String urlLimiter;
    private final int pageLimit = 20;
    private final ArrayList<String> visitedList = new ArrayList<>();
    private final ArrayList<String> pageList = new ArrayList<>();

    public SimpleWebCrawler() {
        startingURL = "https://en.wikipedia.org/wiki/Bishop_Rock,_Isles_of_Scilly";
        urlLimiter = "Bishop_Rock";
        topic = "shipping route";
        visitPage(startingURL);
    }

    public void visitPage(String url) {
        if (pageList.size() >= pageLimit) {
            return;
        }
        if (visitedList.contains(url)) {
            // URL already visited
        } else {
            visitedList.add(url);
            try {
                Document doc = Jsoup.connect(url).get();
                if (doc.text().contains(topic)) {
                    out.println((pageList.size() + 1) + ": [" + url + "]");
                    pageList.add(url);

                    // Process page links
                    Elements questions = doc.select("a[href]");
                    for (Element link : questions) {
                        if (link.attr("href").contains(urlLimiter)) {
                            visitPage(link.attr("abs:href"));
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new SimpleWebCrawler();
    }

}
