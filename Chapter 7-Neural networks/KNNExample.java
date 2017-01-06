package packt.knnexamples;

import java.io.FileReader;
import static java.lang.System.out;
import weka.classifiers.lazy.IBk;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class KNNExample {

    public KNNExample() {
        // dermatology.arff
        // Training set is first 80% - ends with line 456 of orginal set
        // Testing set is last 20% - Starts with line 457 of original set (lines 457-530)
        String trainingFileName = "dermatologyTrainingSet.arff";
        String testingFileName = "dermatologyTestingSet.arff";

        try (FileReader trainingReader = new FileReader(trainingFileName);
                FileReader testingReader = new FileReader(testingFileName)) {
            Instances trainingInstances = new Instances(trainingReader);
            trainingInstances.setClassIndex(trainingInstances.numAttributes() - 1);

            Instances testingInstances = new Instances(testingReader);
            testingInstances.setClassIndex(testingInstances.numAttributes() - 1);

            IBk ibk = new IBk();
            ibk.buildClassifier(trainingInstances);
            SerializationHelper.write("knnModel", ibk);
            ibk = null;
            ibk = (IBk) SerializationHelper.read("knnModel");

            // Evaluate
            Evaluation evaluation = new Evaluation(trainingInstances);
            evaluation.evaluateModel(ibk, testingInstances);
            System.out.println(evaluation.toSummaryString());

            // Predict
            for (int i = 0; i < testingInstances.numInstances(); i++) {
                double result = ibk.classifyInstance(testingInstances.instance(i));
                // Use for incorrect results
                if (result != testingInstances
                        .instance(i)
                        .value(testingInstances.numAttributes() - 1)) {
                    out.println("Classify result: " + result
                            + " Correct: " + testingInstances.instance(i)
                            .value(testingInstances.numAttributes() - 1));
                    Instance incorrectInstance = testingInstances.instance(i);
                    incorrectInstance.setDataset(trainingInstances);
                    double[] distribution = ibk.distributionForInstance(incorrectInstance);
                    out.println("Probability of being positive: " + distribution[0]);
                    out.println("Probability of being negative: " + distribution[1]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new KNNExample();
    }
}
