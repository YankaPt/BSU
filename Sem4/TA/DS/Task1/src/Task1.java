import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Task1 {
    public static void main(String[] args) {
        long[] a=null;
        try {
            FileReader fileReader = new FileReader("input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            a = new long[Integer.parseInt(bufferedReader.readLine())];
            String[] elements = bufferedReader.readLine().split(" ");
            for(int i=0; i<a.length;i++)
                a[i]=Long.parseLong(elements[i]);
        } catch(Exception ex) {}
        try {
            FileWriter fileWriter = new FileWriter("output.txt");
            if(isBinaryHeap(a))
                fileWriter.write("Yes");
            else
                fileWriter.write("No");
            fileWriter.close();
        } catch(Exception ex) {}
    }
    public static boolean isBinaryHeap(long[] a) {
        int i;
        for(i=0; i*2+2<a.length; i++) {
            if(a[i]>a[2*i+1]||a[i]>a[2*i+2])
                return false;
        }
        if((i*2+1<a.length&&a[i]>a[2*i+1])||(i*2+2<a.length&&a[i]>a[2*i+2]))
            return false;
        return true;
    }
}
