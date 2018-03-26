import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Task2 {
    public static void main(String[] args) {
        long n=0;
        try {
            FileReader fileReader = new FileReader("input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            n = Long.parseLong(bufferedReader.readLine());
            bufferedReader.close();
            fileReader.close();
        } catch(Exception ex) {}
        try {
            FileWriter fileWriter = new FileWriter("output.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String string =Long.toBinaryString(n);
            int stringLength = string.length();
            for(int i=stringLength-1;i>=0;i--)
                if(string.charAt(i)=='1')
                    bufferedWriter.write(stringLength-i-1+"\n");
            bufferedWriter.flush();
            fileWriter.close();
        } catch(Exception ex) {}
    }
}
