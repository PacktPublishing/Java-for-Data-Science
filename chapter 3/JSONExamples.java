package packt.jsonexamples;

/*
    <dependencies>
        <!-- http://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.7.4</version>
        </dependency>
        <!-- http://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.7.4</version>
        </dependency>
    </dependencies>
*/

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import java.io.File;
import java.io.IOException;
import static java.lang.System.out;
import java.util.Iterator;
import java.util.Map;

public class JSONExamples {

    public JSONExamples() {
//        parsePerson();
//        parsePersons("Persons.json");
        treeTraversalSolution();
        
        // Data binding not illustrated here
        // See http://www.studytrails.com/java/json/java-jackson-Data-Binding.jsp
        // If time permits
    }

    public void treeTraversalSolution() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // use the ObjectMapper to read the json string and create a tree
            JsonNode node = mapper.readTree(new File("Persons.json"));
            Iterator<String> fieldNames = node.fieldNames();
            while (fieldNames.hasNext()) {
                JsonNode personsNode = node.get("persons");
                Iterator<JsonNode> elements = personsNode.iterator();
                while (elements.hasNext()) {
                    JsonNode element = elements.next();
                    JsonNodeType nodeType = element.getNodeType();
                    
                    if (nodeType == JsonNodeType.STRING) {
                        out.println("Group: " + element.textValue());
                    }

                    if (nodeType == JsonNodeType.ARRAY) {
                        Iterator<JsonNode> fields = element.iterator();
                        while (fields.hasNext()) {
                            parsePerson(fields.next());
                        }
                    }
                }
                fieldNames.next();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void parsePerson(JsonNode node) {
        Iterator<JsonNode> fields = node.iterator();
        while(fields.hasNext()) {
            JsonNode subNode = fields.next();
            out.println(subNode.asText());
        }
    }

    public static void parsePerson() {
        try {
            JsonFactory jsonfactory = new JsonFactory();
            JsonParser parser = jsonfactory.createParser(new File("Person.json"));
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String token = parser.getCurrentName();

                if ("firstname".equals(token)) {
                    parser.nextToken();
                    String fname = parser.getText();
                    out.println("firstname : " + fname);
                }
                if ("lastname".equals(token)) {
                    parser.nextToken();
                    String lname = parser.getText();
                    out.println("lastname : " + lname);
                }
                if ("phone".equals(token)) {
                    parser.nextToken();
                    long phone = parser.getLongValue();
                    out.println("phone : " + phone);
                }
                if ("address".equals(token)) {
                    out.println("address :");
                    parser.nextToken();
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        out.println(parser.getText());
                    }
                }
            }
            parser.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void parsePersons(String filename) {
        try {
            JsonFactory jsonfactory = new JsonFactory();
            File source = new File(filename);
            JsonParser parser = jsonfactory.createParser(source);
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String token = parser.getCurrentName();
                if ("persons".equals(token)) {
                    out.println("Persons found");
                    JsonToken jsonToken = parser.nextToken();
                    jsonToken = parser.nextToken();
                    token = parser.getCurrentName();
                    out.println("Next token is " + token);
                    if ("groupname".equals(token)) {
                        parser.nextToken();
                        String groupname = parser.getText();
                        out.println("Group : " + groupname);
                        parser.nextToken();
                        token = parser.getCurrentName();
                        if ("person".equals(token)) {
                            out.println("Found person");
                            parsePerson(parser);
                        }
                    }
                }
            }
            parser.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void parsePerson(JsonParser parser) throws IOException {
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            String token = parser.getCurrentName();
            if ("firstname".equals(token)) {
                parser.nextToken();
                String fname = parser.getText();
                out.println("firstname : " + fname);
            }
            if ("lastname".equals(token)) {
                parser.nextToken();
                String lname = parser.getText();
                out.println("lastname : " + lname);
            }
            if ("phone".equals(token)) {
                parser.nextToken();
                long phone = parser.getLongValue();
                out.println("phone : " + phone);
            }
            if ("address".equals(token)) {
                out.println("address :");
                parser.nextToken();
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    out.println(parser.getText());
                }
            }
        }
    }

    public static void main(String[] args) {
        new JSONExamples();
    }
}
