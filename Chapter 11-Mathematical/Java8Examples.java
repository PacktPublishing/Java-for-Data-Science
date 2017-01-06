package java8examples;

import static java.lang.System.out;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Java8Examples {
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

    public void simpleStreams() {
        int numbers[] = {3, 54, 23, 87, 50, 21, 31};

        IntStream stream = Arrays.stream(numbers);
        stream.forEach(e -> out.printf("%d  ", e));
        out.println();

        stream = Arrays.stream(numbers);
        stream
                .mapToDouble((int e) -> {
                    return 2 * e;
                })
                .forEach(e -> out.printf("%.4f  ", e));
        out.println();

        stream = Arrays.stream(numbers);
        IntStream.range(0, 3).forEach(e -> out.printf("%d  ", e));
        out.println();

        out.println(Arrays.stream(numbers).sum());

        int nums[] = stream.toArray();
        for (int e : nums) {
            out.print(e + " ");
        }
        out.println();
    }

    public void matrixMultipliationSolution() {
        // Java 8 Stream solution
        out.println("\nJava 8 Stream Solution");
        C = Arrays.stream(A)
                //                .parallel()
                .map(AMatrixRow -> IntStream.range(0, B[0].length)
                        .mapToDouble(i -> IntStream.range(0, B.length)
                                .mapToDouble(j -> AMatrixRow[j] * B[j][i])
                                .sum()
                        ).toArray()).toArray(double[][]::new);
        displayResult();
        out.println();   
    }

    public Java8Examples() {
//        simpleStreams();
        matrixMultipliationSolution();
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

    public static void main(String[] args) {
        new Java8Examples();
    }

}
