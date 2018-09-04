import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Task1 {
    public static void main(String[] args) {
        int n=-1;
        int m=-1;
        List<Integer> matching;
        int[][] a = null;
        int tempV1, tempV2;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
            String[] line = bufferedReader.readLine().split(" ");
            n = Integer.parseInt(line[0]);
            m = Integer.parseInt(line[1]);
            a = new int[m][2];

            for (int i=0; i<m; i++) {
                line = bufferedReader.readLine().split(" ");
                tempV1 = Integer.parseInt(line[0])-1;
                tempV2 = Integer.parseInt(line[1])-1;
                a[i][0] = tempV1;
                a[i][1] = tempV2;
            }
        } catch(Exception ex) {ex.printStackTrace();}
        matching = getMinimumMaximalMatching(n, m, a);
        try {
            PrintWriter printWriter = new PrintWriter(new File("output.txt"));
            printWriter.println(matching.size()+"");
            for (int i=0; i<matching.size(); i++)
                printWriter.print(matching.get(i)+" ");
            printWriter.close();
        } catch(Exception ex) {}
    }
    static List<Integer> getMinimumMaximalMatching(int n, int m, int[][] a) {
        //int[] matching = new int[m/2]; //check m/2
        boolean[] isBlock = new boolean[n];
        List<Integer> matching = new ArrayList<>();
        for (int i=0; i<m; i++) {
            if (!isBlock[a[i][0]] && !isBlock[a[i][1]]) {
                isBlock[a[i][0]] = isBlock[a[i][1]] = true;
                matching.add(i+1);
            }
        }
        return matching;
    }
}
