package packt.somexamples;

import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import weka.core.Instance;
import weka.core.Instances;

/*
    <dependencies>
        <!-- https://mvnrepository.com/artifact/nz.ac.waikato.cms.weka/weka-dev -->
        <dependency>
            <groupId>nz.ac.waikato.cms.weka</groupId>
            <artifactId>weka-dev</artifactId>
            <version>3.7.5</version>
        </dependency>
        <dependency>
            <groupId>weka.plugin.lvq</groupId>
            <artifactId>lvq-bundle</artifactId>
            <version>1.0</version>
        </dependency>   
        <dependency>
            <groupId>weka.plugin.som</groupId>
            <artifactId>som-bundle</artifactId>
            <version>1.0</version>
            <type>jar</type>
        </dependency>             
    </dependencies>
*/
public class SOMExample {

    public SOMExample() {
        SelfOrganizingMap som = new SelfOrganizingMap();
        String trainingFileName = "iris.arff";
        try (FileReader trainingReader = new FileReader(trainingFileName)) {
            Instances trainingInstances = new Instances(trainingReader);
            // The following linehas been commented out to avoid
            // the error:Cannot handle any class attribute!
            // This is because SOM uses unsupervised training
//            trainingInstances.setClassIndex(trainingInstances.numAttributes() - 1);

            som.buildClusterer(trainingInstances);
            out.println(som);

            Instances[] clusters = som.getClusterInstances();
            out.println();
            out.println(clusters.length);
            int index = 0;
            for (Instances instances : clusters) {
                out.println("-------Custer " + index);
                for (Instance instance : instances) {
                    out.println(instance);
                }
                out.println();
                index++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SOMExample();
    }
}
