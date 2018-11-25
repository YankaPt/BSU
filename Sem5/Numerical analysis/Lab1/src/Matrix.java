import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Matrix implements Cloneable, java.io.Serializable {
    private double[][] A;
    private int m, n;

    public Matrix(int m, int n) {
        this.m = m;
        this.n = n;
        A = new double[m][n];
    }

    public Matrix(double[][] A) {
        m = A.length;
        n = A[0].length;
        for (int i = 0; i < m; i++) {
            if (A[i].length != n) {
                throw new IllegalArgumentException("All rows must have the same length.");
            }
        }
        this.A = A;
    }

    public Matrix(double vals[], int m) {
        this.m = m;
        n = (m != 0 ? vals.length / m : 0);
        if (m * n != vals.length) {
            throw new IllegalArgumentException("Array length must be a multiple of m.");
        }
        A = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = vals[i + j * m];
            }
        }
    }

    public Matrix copy() {
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j];
            }
        }
        return X;
    }

    public Object clone() {
        return this.copy();
    }

    public double[][] getArray() {
        return A;
    }

    public double[][] getArrayCopy() {
        double[][] C = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j];
            }
        }
        return C;
    }

    public int getRowDimension() {
        return m;
    }

    public int getColumnDimension() {
        return n;
    }

    public double get(int i, int j) {
        return A[i][j];
    }

    public Matrix getMatrix(int i0, int i1, int j0, int j1) {
        Matrix X = new Matrix(i1 - i0 + 1, j1 - j0 + 1);
        double[][] B = X.getArray();
        try {
            for (int i = i0; i <= i1; i++) {
                for (int j = j0; j <= j1; j++) {
                    B[i - i0][j - j0] = A[i][j];
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }

    public Matrix getMatrix(int[] r, int j0, int j1) {
        Matrix X = new Matrix(r.length, j1 - j0 + 1);
        double[][] B = X.getArray();
        try {
            for (int i = 0; i < r.length; i++) {
                for (int j = j0; j <= j1; j++) {
                    B[i][j - j0] = A[r[i]][j];
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }

    public Matrix transpose() {
        Matrix X = new Matrix(n, m);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[j][i] = A[i][j];
            }
        }
        return X;
    }

    public double normInf() {
        double f = 0;
        for (int i = 0; i < m; i++) {
            double s = 0;
            for (int j = 0; j < n; j++) {
                s += Math.abs(A[i][j]);
            }
            f = Math.max(f, s);
        }
        return f;
    }

    /**
     * Unary minus
     *
     * @return -A
     */

    public Matrix uminus() {
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = -A[i][j];
            }
        }
        return X;
    }

    /**
     * C = A + B
     *
     * @param B another matrix
     * @return A + B
     */

    public Matrix plus(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B.A[i][j];
            }
        }
        return X;
    }

    public Matrix minus(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B.A[i][j];
            }
        }
        return X;
    }

    public Matrix times(double s) {
        Matrix X = new Matrix(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = s * A[i][j];
            }
        }
        return X;
    }

    /**
     * Linear algebraic matrix multiplication, A * B
     *
     * @param B another matrix
     * @return Matrix product, A * B
     * @throws IllegalArgumentException Matrix inner dimensions must agree.
     */

    public Matrix times(Matrix B) {
        if (B.m != n) {
            throw new IllegalArgumentException("Matrix inner dimensions must agree.");
        }
        Matrix X = new Matrix(m, B.n);
        double[][] C = X.getArray();
        double[] Bcolj = new double[n];
        for (int j = 0; j < B.n; j++) {
            for (int k = 0; k < n; k++) {
                Bcolj[k] = B.A[k][j];
            }
            for (int i = 0; i < m; i++) {
                double[] Arowi = A[i];
                double s = 0;
                for (int k = 0; k < n; k++) {
                    s += Arowi[k] * Bcolj[k];
                }
                C[i][j] = s;
            }
        }
        return X;
    }

    public static Matrix identity(int m, int n) {
        Matrix A = new Matrix(m, n);
        double[][] X = A.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                X[i][j] = (i == j ? 1.0 : 0.0);
            }
        }
        return A;
    }

    public Matrix inverse() {
        int row = A.length;
        int col = A[0].length;
        Matrix mat;
        double[][] matrix = new double[row][2 * col];
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++)
                matrix[r][c] = A[r][c];
        }

        for (int r = 0; r < row; r++)
            matrix[r][col + r] = 1;

        mat = new Matrix(matrix);

        for (int r = 0; r < mat.m; r++) {
            double mx = 0;
            int mr = r;
            for (int c = r; c < mat.m; c++) {
                if (Math.abs(mat.A[c][r]) > mx) {
                    mx = Math.abs(mat.A[c][r]);
                    mr = c;
                }
            }
            if (mr != r) {
                double[] temp = mat.A[r];
                mat.A[r] = mat.A[mr];
                mat.A[mr] = temp;
            }
        }

        for (int r = 0; r < mat.m; r++) {
            for (int r2 = 0; r2 < mat.m; r2++) {
                if (r != r2) {
                    double ratio = mat.A[r2][r] / mat.A[r][r];
                    mat.add(r2, r, -ratio);
                }
            }
        }

        for (int r = 0; r < mat.m; r++) {
            double idia = mat.A[r][r];
            for (int c = mat.m; c < mat.n; c++) {
                mat.setElement(r, c, mat.A[r][c] / idia);
            }
            mat.setElement(r, r, mat.A[r][r] / idia);
        }

        Matrix imat = new Matrix(mat.m, mat.m);
        for (int r = 0; r < mat.m; r++) {
            for (int c = 0; c < mat.m; c++)
                imat.setElement(r, c, mat.A[r][mat.m + c]);
        }
        return imat;

    }

    private void add(int r2, int r, double ratio) {
        for (int j = 0; j < n; j++) {
            A[r2][j] += ratio*A[r][j];
        }
    }

    private void setElement(int r, int c, double d) {
        A[r][c] = d;
    }

    private void checkMatrixDimensions(Matrix B) {
        if (B.m != m || B.n != n) {
            throw new IllegalArgumentException("Matrix dimensions must agree.");
        }
    }
}