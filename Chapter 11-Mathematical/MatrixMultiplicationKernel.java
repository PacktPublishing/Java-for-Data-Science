package packt;

import com.amd.aparapi.Kernel;
import static java.lang.System.out;

// Use the following as the JVM option
// -Djava.library.path="C:\Downloads\Aparapi"
class MatrixMultiplicationKernel extends Kernel {
    float[] vectorA = {0.1950f, 0.0311f, 0.3588f, 0.2203f, 
        0.1716f, 0.5931f, 0.2105f, 0.3242f};
    float[] vectorB = {0.0502f, 0.9823f, 0.9472f, 0.5732f, 0.2694f, 0.916f};
    float[] vectorC;
    int n;
    int m;
    int p;

    @Override
    public void run() {
        int i = getGlobalId();
        int j = this.getPassId();
        float value = 0;
        for (int k = 0; k < p; k++) {
            value += vectorA[k + i * m] * vectorB[k * p + j];
        }
        vectorC[i * p + j] = value;
    }

    public MatrixMultiplicationKernel(int n, int m, int p) {
        this.n = n;
        this.p = p;
        this.m = m;

        vectorC = new float[n * p];
    }

    public void displayResults() {
        out.println("Result");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                System.out.printf("%.4f  ", vectorC[i * p + j]);
            }
            out.println();
        }
    }
}
