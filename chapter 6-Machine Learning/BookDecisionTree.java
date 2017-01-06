package packt.decisiontreeexamples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import java.util.Enumeration;

import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/*
    <repositories>
        <repository>
            <id>jboss-3rd-party-releases</id>
            <url>https://repository.jboss.org/nexus/content/repositories/thirdparty-releases/</url>
        </repository>
    </repositories>
    <dependencies>
        <!-- https://mvnrepository.com/artifact/nz.ac.waikato.cms.weka/weka-dev -->
        <dependency>
            <groupId>nz.ac.waikato.cms.weka</groupId>
            <artifactId>weka-dev</artifactId>
            <version>3.7.5</version>
        </dependency>
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>20.0-hal</version>
        </dependency>
    </dependencies>
 */
public class BookDecisionTree {

    private Instances trainingData;

    public static void main(String[] args) {
        try {
            BookDecisionTree decisionTree = new BookDecisionTree("books.arff");
            J48 tree = decisionTree.performTraining();
            System.out.println(tree.toString());
            
            Instance testInstance = decisionTree.
                    getTestInstance("Leather", "yes", "historical");
            int result = (int) tree.classifyInstance(testInstance);
            String results = decisionTree.trainingData.attribute(3).value(result);
            System.out.println(
                    "Test with: " + testInstance + "  Result: " + results);

            testInstance = decisionTree.
                    getTestInstance("Paperback", "no", "historical");
            result = (int) tree.classifyInstance(testInstance);
            results = decisionTree.trainingData.attribute(3).value(result);
            System.out.println(
                    "Test with: " + testInstance + "  Result: " + results);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public BookDecisionTree(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            trainingData = new Instances(reader);
            trainingData.setClassIndex(trainingData.numAttributes() - 1);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private J48 performTraining() {
        J48 j48 = new J48();
        String[] options = {"-U"};
//        Use unpruned tree. -U
        try {
            j48.setOptions(options);
            j48.buildClassifier(trainingData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return j48;
    }

    private Instance getTestInstance(
            String binding, String multicolor, String genre) {
        Instance instance = new DenseInstance(3);
        instance.setDataset(trainingData);
        instance.setValue(trainingData.attribute(0), binding);
        instance.setValue(trainingData.attribute(1), multicolor);
        instance.setValue(trainingData.attribute(2), genre);
        return instance;
    }
}
