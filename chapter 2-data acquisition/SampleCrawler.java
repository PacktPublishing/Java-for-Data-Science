package packt.crawlerj4mavenexample;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.regex.Pattern;
import static java.lang.System.out;

public class SampleCrawler extends WebCrawler {
    private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if (IMAGE_EXTENSIONS.matcher(href).matches()) {
            return false;
        }
        return href.startsWith("https://en.wikipedia.org/wiki/");
    }

    @Override
    public void visit(Page page) {
        int docid = page.getWebURL().getDocid();
        String url = page.getWebURL().getURL();

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            if (text.contains("shipping route")) {
                out.println("\nURL: " + url);
                out.println("Text: " + text);
                out.println("Text length: " + text.length());
            }
        }
    }
}
