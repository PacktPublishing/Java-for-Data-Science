package packt;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.internal.exception.ClassParseException;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class AparapiExamples {    
    int n = 4;
    int m = 2;
    int p = 3;
    
    double A[][] = {
        {0.1950, 0.0311},
        {0.3588, 0.2203},
        {0.1716, 0.5931},
        {0.2105, 0.3242}};
    double B[][] = {
        {0.0502, 0.9823, 0.9472},
        {0.5732, 0.2694, 0.916}};
    double C[][] = new double[n][p];
    
    public AparapiExamples() {
        simpleMatrixMultiplication();
        matrixMulitplication();
        scalarMatrixMultiplication();
    }
    
    public void scalarMatrixMultiplication() {
        float inputMatrix[] = {3, 4, 5, 6, 7, 8, 9};
        int size = inputMatrix.length;
        
        ScalarMultiplicationKernel kernel
                = new ScalarMultiplicationKernel(inputMatrix);
        kernel.setExecutionMode(Kernel.EXECUTION_MODE.NONE);
        kernel.execute(size);
        kernel.displayResult();
        kernel.dispose();
    }
    
    public void matrixMulitplication() {
        MatrixMultiplicationKernel kernel
                = new MatrixMultiplicationKernel(n, m, p);
        kernel.execute(6, 3);
        kernel.displayResults();
        kernel.dispose();
    }
    
    public void simpleMatrixMultiplication() {
        System.out.println();
        System.out.println("Simple Matrix Multiplication");
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < m; k++) {
                for (int j = 0; j < p; j++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        displayResult();
    }
    
    public void displayResult() {
        out.println("Result");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                System.out.printf("%.4f  ", C[i][j]);
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) throws Exception {
        new AparapiExamples();
    }
}
