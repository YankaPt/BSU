/**
 * Created by jan on 20.10.17.
 */
package com.jankothebest;

public class SquareMatrix extends Matrix {
    public SquareMatrix() {super();}
    public SquareMatrix(int userSize) {
        sizeM=sizeN=userSize;
    }
    public Matrix pow(int degree) {
        Matrix resultMatrix = new Matrix(sizeM, sizeN);

        for (int i=0; i<sizeM; i++) {
            resultMatrix.elements[i][i]=1;
        }
        for (int i = 0; i<degree; i++) {
            try {
                resultMatrix=resultMatrix.multiply(this);
            } catch (MultiplyMatrixException ex) {};
        }
        return resultMatrix;
    }
    public static SquareMatrix readMatrix (String filename) throws NotSquareMatrixException, SetOfMatrixException{
        Matrix temp = Matrix.readMatrix(filename);
        if (temp.getSizeM() != temp.getSizeN())
            throw new NotSquareMatrixException("Matrix is not square");
        SquareMatrix result = new SquareMatrix(temp.getSizeM());
        result.elements = temp.elements;
        return result;
    }
}
class NotSquareMatrixException extends RuntimeException{
    NotSquareMatrixException(String message) {super(message);}
}