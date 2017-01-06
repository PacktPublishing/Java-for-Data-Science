package packt;

import com.amd.aparapi.Kernel;
import static java.lang.System.out;

public class ScalarMultiplicationKernel extends Kernel {
    float[] inputMatrix;
    float[] outputMatrix;

    public ScalarMultiplicationKernel(float inputMatrix[]) {
        this.inputMatrix = inputMatrix;
        outputMatrix = new float[this.inputMatrix.length];
    }

    @Override
    public void run() {
        int globalID = this.getGlobalId();
        outputMatrix[globalID] = 2.0f * inputMatrix[globalID];
    }

    public void displayResult() {
        out.println("Result");
        for (float element : outputMatrix) {
            out.printf("%.4f ", element);
        }
        out.println();
    }

}
