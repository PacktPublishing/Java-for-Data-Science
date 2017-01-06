package com.mycompany.sentimentanalysis;


import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.documentiterator.LabelsSource;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;


import java.io.File;
import static java.lang.System.out;

/**
 *
 * @author raver119@gmail.com
 * adapted by Jennifer Reese
 */
public class ClassifyBySimilarity {


    public static void main(String[] args) throws Exception {
        ClassPathResource srcFile = new ClassPathResource("/raw_sentences.txt");
        File file = srcFile.getFile();
        SentenceIterator iter = new BasicLineIterator(file);
        
        TokenizerFactory tFact = new DefaultTokenizerFactory();
        tFact.setTokenPreProcessor(new CommonPreprocessor());

        LabelsSource labelFormat = new LabelsSource("LINE_");

        ParagraphVectors vec = new ParagraphVectors.Builder()
                .minWordFrequency(1)
                .iterations(5)
                .epochs(1)
                .layerSize(100)
                .learningRate(0.025)
                .labelsSource(labelFormat)
                .windowSize(5)
                .iterate(iter)
                .trainWordVectors(false)
                .tokenizerFactory(tFact)
                .sampling(0)
                .build();

        vec.fit();

        double similar1 = vec.similarity("LINE_9835", "LINE_12492");
        out.println("Comparing lines 9836 & 12493 ('This is my house .'/'This is my world .') Similarity = " + similar1);


        double similar2 = vec.similarity("LINE_3720", "LINE_16392");
        out.println("Comparing lines 3721 & 16393 ('This is my way .'/'This is my work .') Similarity = " + similar2);

        double similar3 = vec.similarity("LINE_6347", "LINE_3720");
        out.println("Comparing lines 6348 & 3721 ('This is my case .'/'This is my way .') Similarity = " + similar3);

        double dissimilar1 = vec.similarity("LINE_3720", "LINE_9852");
        out.println("Comparing lines 3721 & 9853 ('This is my way .'/'We now have one .') Similarity = " + dissimilar1);
        
        double dissimilar2 = vec.similarity("LINE_3720", "LINE_3719");
        out.println("Comparing lines 3721 & 3720 ('This is my way .'/'At first he says no .') Similarity = " + dissimilar2);
        
        
        
    }
}