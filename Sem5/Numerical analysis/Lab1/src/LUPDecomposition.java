public class LUPDecomposition {
    private double[][] LU;
    private int m, n, pivsign;
    private int[] piv;

    public LUPDecomposition (Matrix A) {
        long startTime = System.currentTimeMillis();
        LU = A.getArrayCopy();
        m = A.getRowDimension();
        n = A.getColumnDimension();
        piv = new int[m];
        for (int i = 0; i < m; i++) {
            piv[i] = i;
        }
        pivsign = 1;
        double[] LUrowi;
        double[] LUcolj = new double[m];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                LUcolj[i] = LU[i][j];
            }

            for (int i = 0; i < m; i++) {
                LUrowi = LU[i];

                int kmax = Math.min(i,j);
                double s = 0.0;
                for (int k = 0; k < kmax; k++) {
                    s += LUrowi[k]*LUcolj[k];
                }

                LUrowi[j] = LUcolj[i] -= s;
            }

            int p = j;
            for (int i = j+1; i < m; i++) {
                if (Math.abs(LUcolj[i]) > Math.abs(LUcolj[p])) {
                    p = i;
                }
            }
            if (p != j) {
                for (int k = 0; k < n; k++) {
                    double t = LU[p][k]; LU[p][k] = LU[j][k]; LU[j][k] = t;
                }
                int k = piv[p]; piv[p] = piv[j]; piv[j] = k;
                pivsign = -pivsign;
            }

            if (j < m & LU[j][j] != 0.0) {
                for (int i = j+1; i < m; i++) {
                    LU[i][j] /= LU[j][j];
                }
            }
        }
        long finishTime = System.currentTimeMillis();
        System.out.println("time for LUP decomposition: "+(finishTime-startTime));
    }

    public boolean isNonsingular () {
        for (int j = 0; j < n; j++) {
            if (LU[j][j] == 0)
                return false;
        }
        return true;
    }

    public Matrix getL () {
        Matrix X = new Matrix(m,n);
        double[][] L = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > j) {
                    L[i][j] = LU[i][j];
                } else if (i == j) {
                    L[i][j] = 1.0;
                } else {
                    L[i][j] = 0.0;
                }
            }
        }
        return X;
    }

    public Matrix getU () {
        Matrix X = new Matrix(n,n);
        double[][] U = X.getArray();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i <= j) {
                    U[i][j] = LU[i][j];
                } else {
                    U[i][j] = 0.0;
                }
            }
        }
        return X;
    }

    public int[] getPivot () {
        int[] p = new int[m];
        for (int i = 0; i < m; i++) {
            p[i] = piv[i];
        }
        return p;
    }

    public Matrix getP() {
        double[][] matrixP = new double[m][m];
        int[] p = getPivot();
        for (int i = 0; i < m; i++) {
            matrixP[i][p[i]] = 1;
        }
        return new Matrix(matrixP);
    }

    public Matrix solve (Matrix B) {
        long startTime = System.currentTimeMillis();
        if (B.getRowDimension() != m) {
            throw new IllegalArgumentException("Matrix row dimensions must agree.");
        }
        if (!this.isNonsingular()) {
            throw new RuntimeException("Matrix is singular.");
        }

        // Copy right hand side with pivoting
        int nx = B.getColumnDimension();
        Matrix Xmat = B.getMatrix(piv,0,nx-1);
        double[][] X = Xmat.getArray();

        // Solve L*Y = B(piv,:)
        for (int k = 0; k < n; k++) {
            for (int i = k+1; i < n; i++) {
                for (int j = 0; j < nx; j++) {
                    X[i][j] -= X[k][j]*LU[i][k];
                }
            }
        }
        // Solve U*X = Y;
        for (int k = n-1; k >= 0; k--) {
            for (int j = 0; j < nx; j++) {
                X[k][j] /= LU[k][k];
            }
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < nx; j++) {
                    X[i][j] -= X[k][j]*LU[i][k];
                }
            }
        }
        long finishTime = System.currentTimeMillis();
        System.out.println("time for solve LUx = Pb: "+(finishTime-startTime));
        return Xmat;
    }
}