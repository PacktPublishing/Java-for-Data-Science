package packt.dl4jexamples;

import java.io.File;
import java.io.IOException;
import org.deeplearning4j.datasets.fetchers.MnistDataFetcher;
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.RBM;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.api.IterationListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.Collections;
import org.deeplearning4j.util.ModelSerializer;

public class DeepAutoEncoderExample {
    private MultiLayerNetwork model;
    private File modelFile;
    private DataSetIterator iterator;
    private final int numberOfRows = 28;
    private final int numberOfColumns = 28;

    public DeepAutoEncoderExample() {
        try {
            int seed = 123;
            int numberOfIterations = 1;
            iterator = new MnistDataSetIterator(1000, MnistDataFetcher.NUM_EXAMPLES, true);
            
            MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                    .seed(seed)
                    .iterations(numberOfIterations)
                    .optimizationAlgo(OptimizationAlgorithm.LINE_GRADIENT_DESCENT)
                    .list()
                    .layer(0, new RBM.Builder().nIn(numberOfRows * numberOfColumns)
                            .nOut(1000)
                            .lossFunction(LossFunctions.LossFunction.RMSE_XENT).build())
                    .layer(1, new RBM.Builder().nIn(1000).nOut(500)
                            .lossFunction(LossFunctions.LossFunction.RMSE_XENT).build())
                    .layer(2, new RBM.Builder().nIn(500).nOut(250)
                            .lossFunction(LossFunctions.LossFunction.RMSE_XENT).build())
                    .layer(3, new RBM.Builder().nIn(250).nOut(100)
                            .lossFunction(LossFunctions.LossFunction.RMSE_XENT).build())
                    .layer(4, new RBM.Builder().nIn(100).nOut(30)
                            .lossFunction(LossFunctions.LossFunction.RMSE_XENT).build()) //encoding stops
                    .layer(5, new RBM.Builder().nIn(30).nOut(100)
                            .lossFunction(LossFunctions.LossFunction.RMSE_XENT).build()) //decoding starts
                    .layer(6, new RBM.Builder().nIn(100).nOut(250)
                            .lossFunction(LossFunctions.LossFunction.RMSE_XENT).build())
                    .layer(7, new RBM.Builder().nIn(250).nOut(500)
                            .lossFunction(LossFunctions.LossFunction.RMSE_XENT).build())
                    .layer(8, new RBM.Builder().nIn(500).nOut(1000)
                            .lossFunction(LossFunctions.LossFunction.RMSE_XENT).build())
                    .layer(9, new OutputLayer.Builder(
                                    LossFunctions.LossFunction.RMSE_XENT).nIn(1000)
                            .nOut(numberOfRows * numberOfColumns).build())
                    .pretrain(true).backprop(true)
                    .build();

            model = new MultiLayerNetwork(conf);
            model.init();

            model.setListeners(Collections.singletonList(
                    (IterationListener) new ScoreIterationListener()));

            while (iterator.hasNext()) {
                DataSet dataSet = iterator.next();
                model.fit(new DataSet(dataSet.getFeatureMatrix(),
                        dataSet.getFeatureMatrix()));
            }

            modelFile = new File("savedModel");
            ModelSerializer.writeModel(model, modelFile, true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void retrieveModel() {
        try {
            modelFile = new File("savedModel");
            MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(modelFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new DeepAutoEncoderExample();
    }
}
