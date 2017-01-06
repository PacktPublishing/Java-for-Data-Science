package packt.svmexamples;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.PrecomputedKernelMatrixKernel;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class Main {

    public BufferedReader readDataFile(String filename) {
        BufferedReader inputReader = null;
        try {
            inputReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            out.println("File not found: " + filename);
        }
        return inputReader;
    }


    public Main() {
        try {
            BufferedReader datafile;
            datafile = readDataFile("camping.txt");
            Instances data = new Instances(datafile);
            data.setClassIndex(data.numAttributes() - 1);

            Instances trainingData = new Instances(data, 0, 14);
            Instances testingData = new Instances(data, 14, 5);
            Evaluation evaluation = new Evaluation(trainingData);

            SMO smo = new SMO();
            smo.buildClassifier(data);

            evaluation.evaluateModel(smo, testingData);
            System.out.println(evaluation.toSummaryString());

            // Test instance 
            Instance instance = new DenseInstance(3);
            instance.setValue(data.attribute("age"), 78);
            instance.setValue(data.attribute("income"), 125700);
            instance.setValue(data.attribute("camps"), 1);            
            instance.setDataset(data);
            System.out.println("The instance: " + instance);
            System.out.println(smo.classifyInstance(instance));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new Main();
    }
}
