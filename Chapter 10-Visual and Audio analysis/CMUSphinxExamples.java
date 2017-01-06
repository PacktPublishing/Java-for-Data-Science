package packt;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.result.WordResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.out;
import java.util.Collection;
import java.util.List;

/*
    <dependencies>
        <dependency>
            <groupId>edu.cmu.sphinx</groupId>
            <artifactId>sphinx4-core</artifactId>
            <version>5prealpha-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>edu.cmu.sphinx</groupId>
            <artifactId>sphinx4-data</artifactId>
            <version>5prealpha-SNAPSHOT</version>
        </dependency>
    </dependencies>
*/

public class CMUSphinxExamples {

    public CMUSphinxExamples() {
        simpleSpeechExample();
    }

    public static void main(String[] args) {
        new CMUSphinxExamples();
    }

    public void simpleSpeechExample() {
        try {
            Configuration configuration = new Configuration();

            String prefix = "resource:/edu/cmu/sphinx/models/en-us/";
            configuration
                    .setAcousticModelPath(prefix + "en-us");
            configuration
                    .setDictionaryPath(prefix + "cmudict-en-us.dict");
            configuration
                    .setLanguageModelPath(prefix + "en-us.lm.bin");

            StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(
                    configuration);
            InputStream stream = new FileInputStream(new File("Original5.wav"));

            recognizer.startRecognition(stream);
            SpeechResult result;
            while ((result = recognizer.getResult()) != null) {
                out.println("Hypothesis: " + result.getHypothesis());
                
                out.println();
                Collection<String> results = result.getNbest(3);
                for (String sentence : results) {
                    out.println(sentence);
                }
                out.println("-----");
                List<WordResult> words = result.getWords();
                for (WordResult wordResult : words) {
                    out.print(wordResult.getWord() + " ");
                }
                out.println();
                out.println("-----");
                for (WordResult wordResult : words) {
                    out.printf("%s\n\tConfidence: %.3f\n\tTime Frame: %s\n",
                            wordResult.getWord(), result
                                    .getResult()
                                    .getLogMath()
                                    .logToLinear((float)wordResult
                                            .getConfidence()),
                            wordResult.getTimeFrame());
                }
                out.println();
            }

            recognizer.stopRecognition();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
