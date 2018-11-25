import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final double N = 5;
    private static final int VECTOR_SIZE = 256;
    private static double[][] matrix;
    private static double[] matrixAsVector;
    private static double[] y;

    public static void main(String[] args) {
        y = getRandomizeVector();
        matrix = getRandomizeMatrix();
        matrixAsVector = getRandomizeMatrixAsVector();

        Matrix matrixA = new Matrix(matrixAsVector, VECTOR_SIZE);
        Matrix vectorY = new Matrix(y, VECTOR_SIZE);
        Matrix vectorB = matrixA.times(vectorY);
        Matrix submatrixB = matrixA.getMatrix(0, 10, 0, 10);

        long startTime = System.currentTimeMillis();
        Matrix invertedMatrix = matrixA.inverse();
        long finishTime = System.currentTimeMillis();
        System.out.println("time for inverse: "+(finishTime-startTime));

        double conditionNumber = matrixA.normInf()*invertedMatrix.normInf();

        System.out.println(conditionNumber);

        startTime = System.currentTimeMillis();
        Matrix x = Gauss.solve(matrixA, vectorB);
        finishTime = System.currentTimeMillis();
        System.out.println("time for Gauss: "+(finishTime-startTime));
        double normForGauss = x.minus(vectorY).normInf();
        System.out.println("norm for Gauss"+normForGauss);

        LUPDecomposition lupDecomposition = new LUPDecomposition(matrixA);
        Matrix xByLUP = lupDecomposition.solve(vectorB);
        System.out.println("\n");
        double normForLUP = xByLUP.minus(vectorY).normInf();
        System.out.println("norm for LUP"+normForLUP);
        System.out.println("\n_______________________________________________________________");

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            QRDecomposition qrDecomposition = new QRDecomposition(submatrixB);
            Matrix matrixQ = qrDecomposition.getQ();
            submatrixB = matrixQ.transpose().times(submatrixB).times(matrixQ);
        }
        finishTime = System.currentTimeMillis();
        System.out.println("time for QR: "+(finishTime-startTime));

        Arrays.stream(submatrixB.getArray()).forEach((row) -> {
            Arrays.stream(row).forEach((e) -> System.out.print(e + " "));
            System.out.println("\n");
        });
    }

    private static double[] getRandomizeVector() {
        Random random = new Random();
        return random.doubles(VECTOR_SIZE, -Math.pow(2, N/5), Math.pow(2, N/5)).toArray();
    }

    private static double[] getRandomizeMatrixAsVector() {
        Random random = new Random();
        return random.doubles(VECTOR_SIZE*VECTOR_SIZE, -Math.pow(2, N/5), Math.pow(2, N/5)).toArray();
    }

    private static double[][] getRandomizeMatrix() {
        double[][] resultMatrix = new double[VECTOR_SIZE][VECTOR_SIZE];
        for (int i = 0; i < VECTOR_SIZE; i++) {
            resultMatrix[i] = getRandomizeVector();
        }
        return resultMatrix;
    }
}
