package packt;

/*
    <dependencies>
        <dependency>
            <groupId>com.google.oauth-client</groupId>
            <artifactId>google-oauth-client-jetty</artifactId>
            <version>1.20.0</version>
        </dependency>
        <dependency>
            <groupId>com.google.apis</groupId>
            <artifactId>google-api-services-youtube</artifactId>
            <version>v3-rev171-1.22.0</version>
        </dependency>
        <dependency>
            <groupId>com.google.oauth-client</groupId>
            <artifactId>google-oauth-client</artifactId>
            <version>1.22.0</version>
            <type>jar</type>
        </dependency>
        <dependency>
            <groupId>com.google.http-client</groupId>
            <artifactId>google-http-client-jackson2</artifactId>
            <version>1.22.0</version>
            <type>jar</type>
        </dependency>
    </dependencies>
*/
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.List;
import static java.lang.System.out;

// Adapted from https://developers.google.com/youtube/v3/code_samples/java#search_by_keyword
public class Search {

    public static void main(String[] args) {
        try {
            YouTube youtube = new YouTube.Builder(
                    Auth.HTTP_TRANSPORT,
                    Auth.JSON_FACTORY,
                    new HttpRequestInitializer() {
                        public void initialize(HttpRequest request) throws IOException {
                        }
                    })
                    .setApplicationName("application_name")
                    .build();
            
            String queryTerm = "cats";
            YouTube.Search.List search = youtube
                    .search()
                    .list("id,snippet");

            String apiKey = "AIzaSyDiVWbm1q3s3cI3RZNCfH85hXS95H8opgs";
            search.setKey(apiKey);
            search.setQ(queryTerm);

            // Valid types: "channel" "playlist" "video"
            search.setType("video");

            search.setFields("items(id/kind,id/videoId,snippet/title," + 
                    "snippet/description,snippet/thumbnails/default/url)");
            search.setMaxResults(10L);
            SearchListResponse searchResponse = search.execute();

            List<SearchResult> searchResultList = searchResponse.getItems();
            SearchResult video = searchResultList.iterator().next();
            Thumbnail thumbnail = video.getSnippet().getThumbnails().getDefault();

            out.println("Kind: " + video.getKind());
            out.println("Video Id: " + video.getId().getVideoId());
            out.println("Title: " + video.getSnippet().getTitle());
            out.println("Description: " + video.getSnippet().getDescription());
            out.println("Thumbnail: " + thumbnail.getUrl());
        } catch (GoogleJsonResponseException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
