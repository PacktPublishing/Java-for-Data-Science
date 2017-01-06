package com.mycompany.sentimentanalysis;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.deeplearning4j.datasets.iterator.AsyncDataSetIterator;

import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.*;
import java.net.URL;

/**
 * @original author Alex Black Adapted by Jennifer Reese
 */
public class DL4JSentimentAnalysisExample {

    public static final String TRAINING_DATA_URL = "http://ai.stanford.edu/~amaas/data/sentiment/aclImdb_v1.tar.gz";
    public static final String EXTRACT_DATA_PATH = FilenameUtils.concat(System.getProperty("java.io.tmpdir"), "dl4j_w2vSentiment/");
    public static final String GNEWS_VECTORS_PATH = "C:/Jenn Personal/Packt Data Science/Chapter 9 Text Analysis/GoogleNews-vectors-negative300.bin/GoogleNews-vectors-negative300.bin";

    public static void main(String[] args) throws Exception {

        getModelData();
        
        System.out.println("Total memory = " + Runtime.getRuntime().totalMemory());

        int batchSize = 50;
        int vectorSize = 300;
        int nEpochs = 5;
        int truncateReviewsToLength = 300;

        MultiLayerConfiguration sentimentNN = new NeuralNetConfiguration.Builder()
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).iterations(1)
                .updater(Updater.RMSPROP)
                .regularization(true).l2(1e-5)
                .weightInit(WeightInit.XAVIER)
                .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue).gradientNormalizationThreshold(1.0)
                .learningRate(0.0018)
                .list()
                .layer(0, new GravesLSTM.Builder().nIn(vectorSize).nOut(200)
                        .activation("softsign").build())
                .layer(1, new RnnOutputLayer.Builder().activation("softmax")
                        .lossFunction(LossFunctions.LossFunction.MCXENT).nIn(200).nOut(2).build())
                .pretrain(false).backprop(true).build();

        MultiLayerNetwork net = new MultiLayerNetwork(sentimentNN);
        net.init();
        net.setListeners(new ScoreIterationListener(1));

        WordVectors wordVectors = WordVectorSerializer.loadGoogleModel(new File(GNEWS_VECTORS_PATH), true, false);
        DataSetIterator trainData = new AsyncDataSetIterator(new SentimentExampleIterator(EXTRACT_DATA_PATH, wordVectors, batchSize, truncateReviewsToLength, true), 1);
        DataSetIterator testData = new AsyncDataSetIterator(new SentimentExampleIterator(EXTRACT_DATA_PATH, wordVectors, 100, truncateReviewsToLength, false), 1);

        for (int i = 0; i < nEpochs; i++) {
            net.fit(trainData);
            trainData.reset();

            Evaluation evaluation = new Evaluation();
            while (testData.hasNext()) {
                DataSet t = testData.next();
                INDArray dataFeatures = t.getFeatureMatrix();
                INDArray dataLabels = t.getLabels();
                INDArray inMask = t.getFeaturesMaskArray();
                INDArray outMask = t.getLabelsMaskArray();
                INDArray predicted = net.output(dataFeatures, false, inMask, outMask);

                evaluation.evalTimeSeries(dataLabels, predicted, outMask);
            }
            testData.reset();

            System.out.println(evaluation.stats());
        }
    }

    private static void getModelData() throws Exception {
        File modelDir = new File(EXTRACT_DATA_PATH);
        if (!modelDir.exists()) {
            modelDir.mkdir();
        }
        String archivePath = EXTRACT_DATA_PATH + "aclImdb_v1.tar.gz";
        File archiveName = new File(archivePath);
        String extractPath = EXTRACT_DATA_PATH + "aclImdb";
        File extractName = new File(extractPath);
        if (!archiveName.exists()) {
            FileUtils.copyURLToFile(new URL(TRAINING_DATA_URL), archiveName);
            extractTar(archivePath, EXTRACT_DATA_PATH);
        } else if (!extractName.exists()) {
            extractTar(archivePath, EXTRACT_DATA_PATH);
        }
    }

    private static final int BUFFER_SIZE = 4096;

    private static void extractTar(String dataIn, String dataOut) throws IOException {

        try (TarArchiveInputStream inStream = new TarArchiveInputStream(
                new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(dataIn))))) {
            TarArchiveEntry tarFile;
            while ((tarFile = (TarArchiveEntry) inStream.getNextEntry()) != null) {
                if (tarFile.isDirectory()) {
                    new File(dataOut + tarFile.getName()).mkdirs();
                } else {
                    int count;
                    byte data[] = new byte[BUFFER_SIZE];

                    FileOutputStream fileInStream = new FileOutputStream(dataOut + tarFile.getName());
                    BufferedOutputStream outStream=  new BufferedOutputStream(fileInStream, BUFFER_SIZE);
                    while ((count = inStream.read(data, 0, BUFFER_SIZE)) != -1) {
                        outStream.write(data, 0, count);
                    }
                }
            }
        }
    }
}
