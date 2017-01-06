package packt.twitterdatascienceproject;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.tokenizer.EnglishStopTokenizerFactory;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Files;
import java.io.File;
import java.io.IOException;
import static java.lang.System.out;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class TweetHandler {

    Stream<String> data;
    private String jsonText;
    private String text;
    private Date date;
    private String language;
    private String category;
    private String userName;
    
    private static String[] labels = {"neg", "pos"};
    private static int nGramSize = 8;
    
    private static DynamicLMClassifier<NGramProcessLM> classifier = 
            DynamicLMClassifier.createNGramProcess(labels, nGramSize);
    
    public TweetHandler() {
        this.jsonText = "";
    }

    public TweetHandler(String jsonText) {
        this.jsonText = jsonText;
    }

    public TweetHandler processJSON() {
        try {
            JSONObject jsonObject = new JSONObject(this.jsonText);
            this.text = jsonObject.getString("text");
            // "EEE MMM d HH:mm:ss Z yyyy"    
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "EEE MMM d HH:mm:ss Z yyyy");
            try {
                this.date = sdf.parse(jsonObject.getString("created_at"));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            this.language = jsonObject.getString("lang");
            JSONObject user = jsonObject.getJSONObject("user");
            this.userName = user.getString("name");

//            this.place = jsonObject.getString("place");
//            System.out.println("Text: " + jsonObject.getString("text"));
//            System.out.println("Created_at: " + jsonObject.getString("created_at"));
//            System.out.println("lang: " + jsonObject.getString("lang"));
//            System.out.println("id_str: " + jsonObject.getString("id_str"));
//            System.out.println("place: " + jsonObject.getString("place"));
//            System.out.println("user name: " + user.getString("name"));
//            System.out.println("user profile_image_url: " + user.getString("profile_image_url"));
//            out.println();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public TweetHandler toLowerCase() {
        this.text = this.text.toLowerCase().trim();
        return this;
    }

    public TweetHandler removeStopWords() {
        TokenizerFactory tokenizerFactory
                = IndoEuropeanTokenizerFactory.INSTANCE;
        tokenizerFactory = new EnglishStopTokenizerFactory(tokenizerFactory);
        Tokenizer tokens = tokenizerFactory.tokenizer(
                this.text.toCharArray(), 0, this.text.length());
        StringBuilder buffer = new StringBuilder();
        for (String word : tokens) {
            buffer.append(word + " ");
        }
        this.text = buffer.toString();
        return this;
    }
    
    public boolean isEnglish() {
        return this.language.equalsIgnoreCase("en");
    }
    
    public boolean containsCharacter(String character) {
        return this.text.contains(character);
    }
    
    private static int numberOfPositiveReviews = 0;
    private static int numberOfNegativeReviews = 0;

    public static int getNumberOfPositiveReviews() {
        return numberOfPositiveReviews;
    }

    public static int getNumberOfNegativeReviews() {
        return numberOfNegativeReviews;
    }
    
    public void computeStats() {
        if(this.category.equalsIgnoreCase("pos")) {
            numberOfPositiveReviews++;
        } else {
            numberOfNegativeReviews++;
        }
    }

    public void buildSentimentAnalysisModel() {
        out.println("Building Sentiment Model");
        
        File trainingDir = new File("C:\\Jenn Personal\\Packt Data Science\\Chapter 12\\review_polarity\\txt_sentoken");
        for (int i = 0; i < labels.length; i++) {
            Classification classification = new Classification(labels[i]);
            File file = new File(trainingDir, labels[i]);
            File[] trainingFiles = file.listFiles();
            for (int j = 0; j < trainingFiles.length; j++) {
                try {
                    String review = Files.readFromFile(trainingFiles[j], "ISO-8859-1");
                    Classified<CharSequence> classified = new Classified<>(review, classification);
                    classifier.handle(classified);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

//        out.println("---saving model");
//        try {
//            AbstractExternalizable
//                    .compileTo((Compilable) classifier, 
//                            new File("classifier.model"));
//            out.println("---classifer model saved");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        out.println("---buildSentimentAnalysisModel terminated");
    }
    
    public void retreiveSentimentModel() {
        try {
            classifier = (DynamicLMClassifier<NGramProcessLM>) AbstractExternalizable.readObject(new File("classifier.model"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public TweetHandler performSentimentAnalysis() {
        Classification classification = classifier.classify(this.text);
        String bestCategory = classification.bestCategory();
        this.category = bestCategory;
        return this;
    }
    
    @Override
    public String toString() {
        return "\nText: " + this.text
                + "\nDate: " + this.date
                + "\nCategory: " + this.category;
    }

}
