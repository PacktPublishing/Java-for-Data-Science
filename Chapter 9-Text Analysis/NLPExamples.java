package packt.opennlpexamples;



import com.aliasi.tokenizer.EnglishStopTokenizerFactory;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.LowerCaseTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Sequence;
import opennlp.tools.util.Span;

public class NLPExamples {

    private String sentence = "Let's parse this sentence.";

    public void POSExample() {
        try (InputStream input = new FileInputStream(
                new File("en-pos-maxent.bin"));) {

            // To lower case example
            String lowerCaseVersion = sentence.toLowerCase();
            out.println(lowerCaseVersion);

            // Pull out tokens
            List<String> list = new ArrayList<>();
            Scanner scanner = new Scanner(sentence);
            while (scanner.hasNext()) {
                list.add(scanner.next());
            }
            // Convert list to an array
            String[] words = new String[1];
            words = list.toArray(words);

            // Build model
            POSModel posModel = new POSModel(input);
            POSTaggerME posTagger = new POSTaggerME(posModel);

            // Tag words
            String[] posTags = posTagger.tag(words);
            for (int i = 0; i < posTags.length; i++) {
                out.println(words[i] + " - " + posTags[i]);
            }

            // Find top sequences
            Sequence sequences[] = posTagger.topKSequences(words);
            for (Sequence sequence : sequences) {
                out.println(sequence);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void NERExample() {//Models found at http://opennlp.sourceforge.net/models-1.5/
        try (InputStream tokenStream = 
                    new FileInputStream(new File("en-token.bin"));
                InputStream personModelStream = new FileInputStream(
                    new File("en-ner-person.bin"));) {
            TokenizerModel tm = new TokenizerModel(tokenStream);
            TokenizerME tokenizer = new TokenizerME(tm);

            TokenNameFinderModel tnfm = new TokenNameFinderModel(personModelStream);
            NameFinderME nf = new NameFinderME(tnfm);

            String sentence = "Mrs. Wilson went to Mary's house for dinner.";
            String[] tokens = tokenizer.tokenize(sentence);
            
            Span[] spans = nf.find(tokens);

            for (int i = 0; i < spans.length; i++) {
                out.println(spans[i] + " - " + tokens[spans[i].getStart()]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try (InputStream tokenStream = 
                    new FileInputStream("en-token.bin");
                InputStream locationModelStream = new FileInputStream(
                    new File("en-ner-location.bin"));) {
            
            TokenizerModel tm = new TokenizerModel(tokenStream);
            TokenizerME tokenizer = new TokenizerME(tm);
            
            TokenNameFinderModel tnfm = new TokenNameFinderModel(locationModelStream);
            NameFinderME nf = new NameFinderME(tnfm);
            
            sentence = "Enid is located north of Oklahoma City.";
//            sentence = "Pond Creek is located north of Oklahoma City.";
            String tokens[] = tokenizer.tokenize(sentence);
            
            Span spans[] = nf.find(tokens);

            for (int i = 0; i < spans.length; i++) {
                out.println(spans[i] + " - " + tokens[spans[i].getStart()]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void extractReltionships() {
        try (InputStream modelInputStream = new FileInputStream(
                    new File("en-parser-chunking.bin"));) {
            
            ParserModel parserModel = new ParserModel(modelInputStream);
            Parser parser = ParserFactory.create(parserModel);
            
            String sentence = "Let's parse this sentence.";
            Parse[] parseTrees = ParserTool.parseLine(sentence, parser, 3);
            
            for(Parse tree : parseTrees) {
                tree.show();
                out.println("Probability: " + tree.getProb());
            }
            for(Parse tree : parseTrees) {
                out.println("Probability: " + tree.getProb());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }        
    }

    public NLPExamples() {
//        POSExample();
//        scannerClassExample();
//        lingPipeExamples();
//        NERExample();
//        extractReltionships();
    }

    public static void main(String[] args) {
        new NLPExamples();
    }
}
