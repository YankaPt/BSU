/**
 * Created by jan on 20.10.17.
 */
package com.jankothebest;

import java.io.*;
import java.util.ArrayList;

public class Matrix {
    protected int sizeM;
    protected int sizeN;
    protected int[][] elements;

    protected Matrix(){}

    public Matrix(int userSizeM,  int userSizeN) {
        sizeM = userSizeM;
        sizeN = userSizeN;
        elements= new int [sizeM][sizeN];
    }

    public int getSizeM() {
        return sizeM;
    }

    public int getSizeN() {
        return sizeN;
    }

    public void setMatrix(int [][] userElements) throws SetOfMatrixException {
        if (userElements.length*userElements[0].length != sizeM*sizeN)
            throw new SetOfMatrixException("InvalidSet");
        elements = userElements;
    }

    public Matrix multiply(Matrix B) throws MultiplyMatrixException {
        if(sizeN!=B.getSizeM())
            throw new MultiplyMatrixException("Matrixes cannot be multiplied");
        Matrix A = this;
        Matrix C = new Matrix(A.getSizeM(), B.getSizeN());
        int sum=0;
        for (int i=0; i<A.getSizeM(); i++) {
            for (int j = 0; j < B.getSizeN(); j++) {
                sum =0;
                for (int k=0; k< A.getSizeN(); k++) {
                    sum= sum + A.elements[i][k]*B.elements[k][j];
                }
                C.elements[i][j]= sum;
            }
        }
    return C;
    }
    public void print() {
        for (int i[]:elements) {
            System.out.println("");
            for (int j : i)
                System.out.print(j +" ");
        }
    }
    public static Matrix readMatrix(String filename) throws SetOfMatrixException {
        try {
            InputStream is = Matrix.class.getClassLoader().getResourceAsStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            ArrayList<String> arrayList = new ArrayList<String>();
            line = reader.readLine();
            String[] elements=line.split(" ");
            arrayList.add(line);
            int n=elements.length;
            while ((line = reader.readLine()) != null) {
                arrayList.add(line);
            }
            int m = arrayList.size();
            reader.close();
            int[][] matrixA= new int[m][n];
            for(int i=0; i<m; i++) {
                elements = arrayList.get(i).split(" ");
                if(n!=elements.length)
                    throw new SetOfMatrixException("Matrix is strange");
                for (int j=0; j<n; j++) {
                    matrixA[i][j] = Integer.parseInt(elements[j]);
                }
            }
            Matrix A = new Matrix(m, n);
            A.setMatrix(matrixA);
            return A;
        } catch (IOException ex) {
            System.out.println("Error of filereader");
            return null;
        }
    }
}


class SetOfMatrixException extends Exception {
    SetOfMatrixException(String message) {
        super(message);
    }
}
class MultiplyMatrixException extends Exception {
    MultiplyMatrixException(String message) {
        super(message);
    }
}