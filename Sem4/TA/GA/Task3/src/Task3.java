import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Task3 {
    public static void main(String[] args) {
        int n=0;
        String[] lines = null;
        try {
            FileReader fileReader = new FileReader("input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            n = Integer.parseInt(bufferedReader.readLine());
            lines = new String[n-1];
            for (int i=0; i<n-1;i++)
                lines[i] = bufferedReader.readLine();
        } catch(Exception ex) {}
        try {
            FileWriter fileWriter = new FileWriter("output.txt");
            StringBuilder builder = new StringBuilder();
            for (int i:getCanonical(n, lines))
                builder.append(i + " ");
            fileWriter.write(builder.toString());
            fileWriter.close();
        } catch(Exception ex) {}
    }
    public static int[] getCanonical(int n, String[] lines) {
        int[] a = new int[n];
        int u, v;
        String[] elements;
        for (int i=0; i<n-1; i++) {
            elements = lines[i].split(" ");
            u = Integer.parseInt(elements[0]);
            v = Integer.parseInt(elements[1]);
            a[v-1] = u;
        }
        return a;
    }
}
