package com.mycompany.sentimentanalysis;

import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.berkeley.Pair;
//import org.deeplearning4j.examples.nlp.paragraphvectors.tools.FileLabelAwareIterator;
//import org.deeplearning4j.examples.nlp.paragraphvectors.tools.LabelSeeker;
//import org.deeplearning4j.examples.nlp.paragraphvectors.tools.MeansBuilder;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;

import static java.lang.System.out;
import java.util.List;

/**
 *
 * @author raver119@gmail.com adapted by Jennifer Reese
 */
public class ParagraphVectorsClassifierExample {

    static ParagraphVectors pVect;
    static LabelAwareIterator iter;
    static TokenizerFactory tFact;


    public static void main(String[] args) throws Exception {

        ClassPathResource resource = new ClassPathResource("paravec/labeled");

        iter = new FileLabelAwareIterator.Builder()
                .addSourceFolder(resource.getFile())
                .build();

        tFact = new DefaultTokenizerFactory();
        tFact.setTokenPreProcessor(new CommonPreprocessor());

        pVect = new ParagraphVectors.Builder()
                .learningRate(0.025)
                .minLearningRate(0.001)
                .batchSize(1000)
                .epochs(20)
                .iterate(iter)
                .trainWordVectors(true)
                .tokenizerFactory(tFact)
                .build();

        pVect.fit();


        ClassPathResource unlabeledText = new ClassPathResource("paravec/unlabeled");
        FileLabelAwareIterator unlabeledIter = new FileLabelAwareIterator.Builder()
                .addSourceFolder(unlabeledText.getFile())
                .build();


        MeansBuilder mBuilder = new MeansBuilder(
                (InMemoryLookupTable<VocabWord>) pVect.getLookupTable(),
                tFact);
        LabelSeeker lSeeker = new LabelSeeker(iter.getLabelsSource().getLabels(),
                (InMemoryLookupTable<VocabWord>) pVect.getLookupTable());

        while (unlabeledIter.hasNextDocument()) {
            LabelledDocument doc = unlabeledIter.nextDocument();
            INDArray docCentroid = mBuilder.documentAsVector(doc);
            List<Pair<String, Double>> scores = lSeeker.getScores(docCentroid);

            out.println("Document '" + doc.getLabel() + "' falls into the following categories: ");
            for (Pair<String, Double> score : scores) {
                out.println("        " + score.getFirst() + ": " + score.getSecond());
            }

        }
    }
}