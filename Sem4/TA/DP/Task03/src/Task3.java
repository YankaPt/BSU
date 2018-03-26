import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Task3 {
    private static int s;
    public static void main(String[] args) {
        int[] a=null;
        try {
            FileReader fileReader = new FileReader("input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            s = Integer.parseInt(line);
            String[] elements;
            a = new int[s+1];
            int i=0;
            line = bufferedReader.readLine();
            elements = line.split(" ");
            a[i++]=Integer.parseInt(elements[0]);
            a[i++]=Integer.parseInt(elements[1]);
            while(true) {
                line = bufferedReader.readLine();
                elements = line.split(" ");
                a[i++]=Integer.parseInt(elements[1]);
            }
        } catch(Exception ex) {}
        try {
            FileWriter fileWriter = new FileWriter("output.txt");
            fileWriter.write(getMinNumOfOperations(a)+"");
            fileWriter.close();
        } catch(Exception ex) {}
    }
    private static int getMinNumOfOperations(int[] a) {
        int[][] matrix = new int[s+1][s+1];

        for (int i = 1; i <= s; i++) {
            matrix[i][i] = 0;
        }

        for (int m = 2; m <= s; m++) {
            for (int i = 1; i <= s-m+1; i++) {
                int j = i+m-1;
                matrix[i][j] = Integer.MAX_VALUE;
                for (int n = i; n <= j-1; n++) {
                    matrix[i][j] = Math.min(matrix[i][j],matrix[n+1][j]+matrix[i][n]+a[i-1]*a[n]*a[j]);
                }
            }
        }
        return matrix[1][s];
    }

}