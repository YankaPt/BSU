import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Task03 {
    static ArrayList<Integer> a = new ArrayList<>();
    public static void main(String[] args) {
        int s=0;
        try {
            FileReader fileReader = new FileReader("input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            s = Integer.parseInt(line);
            String[] elements;
            while(line!=null) {
                line = bufferedReader.readLine();
                elements = line.split(" ");
                a.add(Integer.parseInt(elements[0]));
                a.add(Integer.parseInt(elements[1]));
            }
        } catch(Exception ex) {}
        try {
            FileWriter fileWriter = new FileWriter("output.txt");
            fileWriter.write(getMinNumOfOperations(0, s-1)+"");
            fileWriter.close();
        } catch(Exception ex) {}
    }
    private static long getMinNumOfOperations(int i, int j) {
        if(i==j)
            return 0;
        long result=Long.MAX_VALUE;
        long min;
        for(int k=i; k<j; k++) {
            min = getMinNumOfOperations(i,k)+getMinNumOfOperations(k+1, j) + a.get(i*2)*a.get(k*2+1)*a.get(j*2+1);
            if(result>min)
                result = min;
        }
        return result;
    }
}
