package packt.jblasexamples;

import static java.lang.System.out;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.jblas.DoubleMatrix;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/*
 //    <dependencies>
 //        <dependency>
 //            <groupId>org.jblas</groupId>
 //            <artifactId>jblas</artifactId>
 //            <version>1.2.4</version>
 //        </dependency>
 //        <!-- https://mvnrepository.com/artifact/org.apache.commons/commons-exec -->
 //        <dependency>
 //            <groupId>org.apache.commons</groupId>
 //            <artifactId>commons-exec</artifactId>
 //            <version>1.3</version>
 //        </dependency>
 //        <dependency>
 //            <groupId>org.apache.commons</groupId>
 //            <artifactId>commons-math3</artifactId>
 //            <version>3.6.1</version>
 //            <type>jar</type>
 //        </dependency>
 //        <!-- https://mvnrepository.com/artifact/org.nd4j/nd4j-native -->
 //        <dependency>
 //            <groupId>org.nd4j</groupId>
 //            <artifactId>nd4j-native</artifactId>
 //            <version>0.6.0</version>
 //        </dependency>       
 //    </dependencies>
 */
public class MathExamples {

    public static void main(String[] args) {
        new MathExamples();
    }

    public MathExamples() {
        JBLASExample();
        apacheCommonsExample();
        nd4JExample();
    }

    public void JBLASExample() {
        DoubleMatrix A = new DoubleMatrix(new double[][]{
            {0.1950, 0.0311},
            {0.3588, 0.2203},
            {0.1716, 0.5931},
            {0.2105, 0.3242}});

        DoubleMatrix B = new DoubleMatrix(new double[][]{
            {0.0502, 0.9823, 0.9472},
            {0.5732, 0.2694, 0.916}});
        DoubleMatrix C;

        C = A.mmul(B);

        for (int i = 0; i < C.getRows(); i++) {
            out.println(C.getRow(i));
        }
    }

    public void apacheCommonsExample() {
        double[][] A = {
            {0.1950, 0.0311},
            {0.3588, 0.2203},
            {0.1716, 0.5931},
            {0.2105, 0.3242}};

        double[][] B = {
            {0.0502, 0.9823, 0.9472},
            {0.5732, 0.2694, 0.916}};

        RealMatrix aRealMatrix = new Array2DRowRealMatrix(A);
        RealMatrix bRealMatrix = new Array2DRowRealMatrix(B);

        RealMatrix cRealMatrix = aRealMatrix.multiply(bRealMatrix);
        System.out.println();
        for (int i = 0; i < cRealMatrix.getRowDimension(); i++) {
            System.out.println(cRealMatrix.getRowVector(i));
        }
    }

    public void nd4JExample() {
        double[] A = {
            0.1950, 0.0311,
            0.3588, 0.2203,
            0.1716, 0.5931,
            0.2105, 0.3242};

        double[] B = {
            0.0502, 0.9823, 0.9472,
            0.5732, 0.2694, 0.916};

        
        INDArray aINDArray = Nd4j.create(A,new int[]{4,2},'c');
        INDArray bINDArray = Nd4j.create(B,new int[]{2,3},'c');
        
        INDArray cINDArray;
        cINDArray = aINDArray.mmul(bINDArray);
        for(int i=0; i<cINDArray.rows(); i++) {
            System.out.println(cINDArray.getRow(i));
        }
    }
}
