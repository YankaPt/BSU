import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Task1 {
    public static void main(String[] args) {
        int[][] matrix=null;
        int n=0;
        int m=0;
        String[] lines = null;
        try {
            FileReader fileReader = new FileReader("input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] firstLine;
            firstLine = bufferedReader.readLine().split(" ");
            n = Integer.parseInt(firstLine[0]);
            m = Integer.parseInt(firstLine[1]);
            lines = new String[m];
            for (int i=0; i<m;i++)
                lines[i] = bufferedReader.readLine();
        } catch(Exception ex) {}
        try {
            FileWriter fileWriter = new FileWriter("output.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            matrix = getAdjacencyMatrix(n, lines);
            for (int i=0; i<n; i++) {
                for (int j = 0; j < n; j++) {
                    bufferedWriter.write(matrix[i][j] + "");
                    if(j!=n-1)
                        bufferedWriter.write(" ");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        } catch(Exception ex) {}
    }
    public static int[][] getAdjacencyMatrix(int n, String[] lines) {
        int[][] matrix = new int[n][n];
        int i=0, j=0;
        for (int k=0; k<lines.length; k++) {
            i = Integer.parseInt(lines[k].split(" ")[0])-1;
            j = Integer.parseInt(lines[k].split(" ")[1])-1;
            matrix[i][j] = matrix[j][i] = 1;
        }
        return matrix;
    }
}
