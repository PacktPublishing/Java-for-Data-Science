package packt.wekanueralnetworkexamples;

import java.io.FileReader;
import static java.lang.System.out;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

/*
    <dependencies>
        <!-- https://mvnrepository.com/artifact/nz.ac.waikato.cms.weka/weka-dev -->
        <dependency>
            <groupId>nz.ac.waikato.cms.weka</groupId>
            <artifactId>weka-dev</artifactId>
            <version>3.7.5</version>
        </dependency>
    </dependencies>
*/
public class MultilayerPerceptronExample {
    
    public MultilayerPerceptronExample() {
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

            MultilayerPerceptron mlp = new MultilayerPerceptron();
            mlp.setLearningRate(0.1);
            mlp.setMomentum(0.2);
            mlp.setTrainingTime(2000);
            mlp.setHiddenLayers("3");
            mlp.buildClassifier(trainingInstances);
            SerializationHelper.write("mlpModel", mlp);
            out.println(mlp.getTrainingTime());
            mlp = (MultilayerPerceptron)SerializationHelper.read("mlpModel");


            // Evaluate
            System.out.println("Starting evaluation");
            Evaluation evaluation = new Evaluation(trainingInstances);
            evaluation.evaluateModel(mlp, testingInstances);
            System.out.println(evaluation.toSummaryString());

            // Predict
            System.out.println("Starting Predicting");
            for (int i = 0; i < testingInstances.numInstances(); i++) {
                double result = mlp.classifyInstance(testingInstances.instance(i));
                // Use for incorrect results
                if (result != testingInstances
                        .instance(i)
                        .value(testingInstances.numAttributes() - 1)) {
                    out.println("Classify result: " + result
                            + " Correct: " + testingInstances.instance(i)
                            .value(testingInstances.numAttributes() - 1));
                    Instance incorrectInstance = testingInstances.instance(i);
                    incorrectInstance.setDataset(trainingInstances);
                    double[] distribution = mlp.distributionForInstance(incorrectInstance);
                    out.println("Probability of being positive: " + distribution[0]);
                    out.println("Probability of being negative: " + distribution[1]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MultilayerPerceptronExample();
    }
}
