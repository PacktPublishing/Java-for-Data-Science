package httpurlconnectionexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import static java.lang.System.out;

public class HttpURLConnectionExample {

    public static void main(String[] args) {
        try {
            URL url = new URL("https://en.wikipedia.org/wiki/Data_science");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            out.println("Response Code: " + connection.getResponseCode());
            out.println("Content Type: " + connection.getContentType());
            out.println("Content Length: " + connection.getContentLength());

            InputStreamReader isr = new InputStreamReader(
                    (InputStream) connection.getContent());
            BufferedReader br = new BufferedReader(isr);
            StringBuilder buffer = new StringBuilder();
            String line;
            do {
                line = br.readLine();
                buffer.append(line + "\n");
            } while (line != null);
            out.println(buffer.toString());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
