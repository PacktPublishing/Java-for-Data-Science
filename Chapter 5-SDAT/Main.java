/*
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>
    <repositories>
        <repository>
            <id>jboss-3rd-party-releases</id>
            <url>https://repository.jboss.org/nexus/content/repositories/thirdparty-releases/</url>
        </repository>
    </repositories>
    <dependencies>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-math3</artifactId>
            <version>3.6.1</version>
        </dependency>
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>20.0-hal</version>
        </dependency>
    </dependencies>
 */

package packt.basicstatisticexamples;

import com.google.common.math.Stats;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

/**
 *
 * @author Richard
 */
public class Main {

    double[] testData = {12.5, 18.7, 11.2, 19.0, 22.1, 14.3, 16.9, 12.5, 17.8, 16.9};

    public Main() {
        simpleJava();
        usingJava8();
        usingGuava();
        usingApacheCommons();
        calculatngMedian();
        calculatingTheMode();
        calculatingDStandardDeviation();
    }

    public void simpleJava() {
        // Using simple Java techniques to find mean
        double total = 0;
        for (double element : testData) {

        }
        for (double element : testData) {
            total += element;
        }
        double mean = total / testData.length;
        out.println("The mean is " + mean);
    }

    public void usingJava8() {
        // Using Java 8 techniques to find mean
        OptionalDouble mean = Arrays.stream(testData).average();
        if (mean.isPresent()) {
            out.println("The mean is " + mean.getAsDouble());
        } else {
            out.println("The stream was empty");
        }

        mean = Arrays.stream(testData).average();
        mean.ifPresent(x -> out.println("The mean is " + x));

        mean = Arrays.stream(testData).average();
        out.println("The mean is " + mean.orElse(0));

    }

    public void usingGuava() {
        // Using Google Guava to find mean
        Stats testStat = Stats.of(testData);
        double mean = testStat.mean();
        out.println("The mean is " + mean);

    }

    public void usingApacheCommons() {
        // Using Apache Commons to find mean
        Mean mean = new Mean();
        double average = mean.evaluate(testData);
        out.println("The mean is " + average);

        DescriptiveStatistics statTest
                = new SynchronizedDescriptiveStatistics();
        for (double num : testData) {
            statTest.addValue(num);
        }
        out.println("The mean is " + statTest.getMean());

    }

    public void calculatngMedian() {
        // Calculating the median
        double[] testData = {12.5, 18.3, 11.2, 19.0, 22.1, 14.3, 16.2, 12.5, 17.8, 16.5};
        Arrays.sort(testData);
        if (testData.length == 0) {    // Empty list
            out.println("No median. Length is 0");
        } else if (testData.length % 2 == 0) {    // Even number of elements
            double mid1 = testData[(testData.length / 2) - 1];
            double mid2 = testData[testData.length / 2];
            double med = (mid1 + mid2) / 2;
            out.println("The median is " + med);
        } else {   // Odd number of elements
            double mid = testData[(testData.length / 2) + 1];
            out.println("The median is " + mid);
        }

        usingApacaheCommonstoCalculateMedian();

    }

    public void usingApacaheCommonstoCalculateMedian() {
        // Using Apache Commons to find median
        double[] testData = {12.5, 18.3, 11.2, 19.0, 22.1, 14.3, 16.2, 12.5, 17.8, 16.5, 12.5};
        DescriptiveStatistics statTest
                = new SynchronizedDescriptiveStatistics();
        for (double num : testData) {
            statTest.addValue(num);
        }
        out.println("The median is " + statTest.getPercentile(50));
    }

    public void calculatingTheMode() {
        double[] testData = {12.5, 18.3, 11.2, 19.0, 22.1, 14.3, 16.2, 12.5, 17.8, 16.5, 12.5};
        int modeCount = 0;
        double mode = 0;
        int tempCnt = 0;

        for (double testValue : testData) {
            tempCnt = 0;
            for (double value : testData) {
                if (testValue == value) {
                    tempCnt++;
                }
            }

            if (tempCnt > modeCount) {
                modeCount = tempCnt;
                mode = testValue;
            }
        }
        out.println("Mode" + mode + " appears " + modeCount + " times.");

        usingArrayLists();
        usingHashMap();
        usingApacheCommonsToFindMultipleModes();
    }

    public void usingArrayLists() {
        double[] testData = {12.5, 18.3, 11.2, 19.0, 22.1, 14.3, 16.2, 12.5, 17.8, 16.5, 11.2};
        // Using ArrayLists to find multiple modes
        ArrayList<Integer> modeCount = new ArrayList<>();
        ArrayList<Double> mode = new ArrayList<>();
        int tempMode = 0;

        for (double testValue : testData) {
            int loc = mode.indexOf(testValue);
            if (loc == -1) {
                mode.add(testValue);
                modeCount.add(1);
            } else {
                modeCount.set(loc, modeCount.get(loc) + 1);
            }
        }

        for (int cnt = 0; cnt < modeCount.size(); cnt++) {
            if (tempMode < modeCount.get(cnt)) {
                tempMode = modeCount.get(cnt);
            }
        }

        for (int cnt = 0; cnt < modeCount.size(); cnt++) {
            if (tempMode == modeCount.get(cnt)) {
                out.println(mode.get(cnt) + " is a mode and appears "
                        + modeCount.get(cnt) + " times.");
            }
        }
    }

    public void usingHashMap() {
        double[] testData = {12.5, 18.3, 11.2, 19.0, 22.1, 14.3, 16.2, 12.5, 17.8, 16.5, 11.2};
        // Using a HashMap to find multiple modes
        ArrayList<Double> modes = new ArrayList<Double>();
        HashMap<Double, Integer> modeMap = new HashMap<Double, Integer>();
        int maxMode = 0;

        for (double value : testData) {
            int modeCnt = 0;
            if (modeMap.containsKey(value)) {
                modeCnt = modeMap.get(value) + 1;
            } else {
                modeCnt = 1;
            }
            modeMap.put(value, modeCnt);
            if (modeCnt > maxMode) {
                maxMode = modeCnt;
            }
        }

        for (Map.Entry<Double, Integer> multiModes : modeMap.entrySet()) {
            if (multiModes.getValue() == maxMode) {
                modes.add(multiModes.getKey());
            }
        }
        for (double mode : modes) {
            out.println(mode + " is a mode and appears " + maxMode + " times.");

        }
    }

    public void usingApacheCommonsToFindMultipleModes() {
        double[] testData = {12.5, 18.3, 11.2, 19.0, 22.1, 14.3, 16.2, 12.5, 17.8, 16.5, 11.2};
        // Using a Apache Commons to find multiple modes
        double[] modes = StatUtils.mode(testData);
        for (double mode : modes) {
            out.println(mode + " is a mode.");
        }

    }

    public void calculatingDStandardDeviation() {
        // Standard deviation
        double[] testData = {12.5, 18.3, 11.2, 19.0, 22.1, 14.3, 16.2, 12.5, 17.8, 16.5, 11.2};
        int sum = 0;
        for (double value : testData) {
            sum += value;
        }
        double mean = sum / testData.length;

        int sdSum = 0;
        for (double value : testData) {
            sdSum += Math.pow((value - mean), 2);
        }
        out.println("The standard deviation is "
                + Math.sqrt(sdSum / (testData.length)));

        // Using Guava
        Stats testStats = Stats.of(testData);
        double sd = testStats.populationStandardDeviation();
        out.println("The standard deviation is " + sd);

        sd = testStats.sampleStandardDeviation();
        out.println("The standard deviation is " + sd);

        DescriptiveStatistics statTest
                = new SynchronizedDescriptiveStatistics();
        for (double num : testData) {
            statTest.addValue(num);
        }
        out.println("The standard deviation is "
                + statTest.getStandardDeviation());

        StandardDeviation sdSubset = new StandardDeviation(false);
        out.println("The population standard deviation is "
                + sdSubset.evaluate(testData));

        StandardDeviation sdPopulation = new StandardDeviation(true);
        out.println("The sample standard deviation is "
                + sdPopulation.evaluate(testData));
    }

    public static void main(String[] args) {
        new Main();
    }
}
