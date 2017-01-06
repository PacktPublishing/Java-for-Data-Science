/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packt;

import info.bliki.api.Page;
import info.bliki.api.User;
import info.bliki.wiki.filter.SectionHeader;
import info.bliki.wiki.model.ITableOfContent;
import info.bliki.wiki.model.Reference;
import info.bliki.wiki.model.WikiModel;
import java.util.List;
import static java.lang.System.out;

public class BlikiExample {

    public static void main(String[] args) {
        User user = new User("", "", "http://en.wikipedia.org/w/api.php");
        user.login();

        String[] titles = {"Data science"};
        List<Page> pageList = user.queryContent(titles);

        for (Page page : pageList) {
            WikiModel wikiModel = new WikiModel("${image}", "${title}");
            out.println("Image Base URL: " + wikiModel.getImageBaseURL() + "\n"
                    + "Page Name: " + wikiModel.getPageName() + "\n"
                    + "Wiki Base URL: " + wikiModel.getWikiBaseURL());
            String htmlStr = wikiModel.render("This is a simple [[Hello World]] wiki tag");
            System.out.println(htmlStr);

            String htmlText = wikiModel.render(page.toString());
            out.println("Title: " + page.getTitle() + "\n"
                    + "Image URL: " + page.getImageUrl()+ "\n"
                    + "Timestamp: " + page.getCurrentRevision().getTimestamp());
            
            List <Reference> referenceList = wikiModel.getReferences();
            out.println(referenceList.size());
            for(Reference reference : referenceList) {
                out.println(reference.getRefString());
            }
            
            ITableOfContent toc = wikiModel.getTableOfContent();
            List<SectionHeader> sections = toc.getSectionHeaders();
            for(SectionHeader sh : sections) {
                out.println(sh.getFirst());
            }
            
            out.println(htmlText);
        }
    }

}
