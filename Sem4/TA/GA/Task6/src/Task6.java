import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class Task6 {
    public static void main(String[] args) {
        int n=0;
        ArrayList<String> lines = null;
        try {
            FileReader fileReader = new FileReader("input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            n = Integer.parseInt(bufferedReader.readLine());
            lines = new ArrayList<>();
            for (int i=0; i<n;i++)
                lines.add(bufferedReader.readLine());
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
    public static int[] getCanonical(int n, ArrayList<String> lines) {
        int[] a = new int[n];
        String[] elements;
        Deque<Integer> stack = new ArrayDeque<>();
        int k=1;
        int current;
        for(int i=0; i<a.length; i++) {
            if (a[i] == 0) {
                a[i] = k++;
                stack.addLast(i);
                while(!stack.isEmpty()) {
                    current = stack.pollLast();
                    elements = lines.get(current).split(" ");
                    for (int j = 0; j < elements.length; j++)
                        if (elements[j].equals("1") && a[j] == 0) {
                            a[j] = k++;
                            stack.addLast(j);
                            break;
                        }
                }
            }
        }
        return a;
    }
}