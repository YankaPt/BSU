public class QRDecomposition {
    private double[][] QR;
    private int m, n;
    private double[] Rdiag;

    public QRDecomposition (Matrix A) {
        // Initialize.
        QR = A.getArrayCopy();
        m = A.getRowDimension();
        n = A.getColumnDimension();
        Rdiag = new double[n];

        // Main loop.
        for (int k = 0; k < n; k++) {
            // Compute 2-norm of k-th column without under/overflow.
            double nrm = 0;
            for (int i = k; i < m; i++) {
                nrm = hypot(nrm,QR[i][k]);
            }

            if (nrm != 0.0) {
                // Form k-th Householder vector.
                if (QR[k][k] < 0) {
                    nrm = -nrm;
                }
                for (int i = k; i < m; i++) {
                    QR[i][k] /= nrm;
                }
                QR[k][k] += 1.0;

                // Apply transformation to remaining columns.
                for (int j = k+1; j < n; j++) {
                    double s = 0.0;
                    for (int i = k; i < m; i++) {
                        s += QR[i][k]*QR[i][j];
                    }
                    s = -s/QR[k][k];
                    for (int i = k; i < m; i++) {
                        QR[i][j] += s*QR[i][k];
                    }
                }
            }
            Rdiag[k] = -nrm;
        }
    }

    public Matrix getQ () {
        Matrix X = new Matrix(m,n);
        double[][] Q = X.getArray();
        for (int k = n-1; k >= 0; k--) {
            for (int i = 0; i < m; i++) {
                Q[i][k] = 0.0;
            }
            Q[k][k] = 1.0;
            for (int j = k; j < n; j++) {
                if (QR[k][k] != 0) {
                    double s = 0.0;
                    for (int i = k; i < m; i++) {
                        s += QR[i][k]*Q[i][j];
                    }
                    s = -s/QR[k][k];
                    for (int i = k; i < m; i++) {
                        Q[i][j] += s*QR[i][k];
                    }
                }
            }
        }
        return X;
    }

    public static double hypot(double a, double b) {
        double r;
        if (Math.abs(a) > Math.abs(b)) {
            r = b/a;
            r = Math.abs(a)*Math.sqrt(1+r*r);
        } else if (b != 0) {
            r = a/b;
            r = Math.abs(b)*Math.sqrt(1+r*r);
        } else {
            r = 0.0;
        }
        return r;
    }
}