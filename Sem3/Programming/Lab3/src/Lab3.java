/**
 * Created by jan on 20.10.17.
 */
import com.jankothebest.*;

public class Lab3 {
    public static void main(String[] args) {
        SquareMatrix A = new SquareMatrix(2);
        Matrix C;
        try {
            SquareMatrix B = SquareMatrix.readMatrix("com/input.txt");
            C=B.pow(10);
            C.print();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        };
    }
}
