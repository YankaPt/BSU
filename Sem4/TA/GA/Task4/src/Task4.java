import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Task4 {
    public static void main(String[] args) {
        int n=0;
        String[] lines = null;
        try {
            FileReader fileReader = new FileReader("input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            n = Integer.parseInt(bufferedReader.readLine());
            lines = new String[n];
            for (int i=0; i<n;i++)
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
        String[] elements;
        for (int i=0; i<n; i++) {
            elements = lines[i].split(" ");
            for (int j=0; j<elements.length; j++)
                if (elements[j].equals("1"))
                a[j] = i+1;
        }
        return a;
    }
}
