import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Created by jan on 11.2.18.
 */
public class ExtraInput {
    public static void main(String[] args) {
        try {
            FileWriter fileWriter = new FileWriter("/home/jan/ClionProjects/3sem/test0/input.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(long i=0; i <= Integer.MAX_VALUE/50000; i++)
                bufferedWriter.write(i+"\n");
            bufferedWriter.flush();
            fileWriter.close();
        } catch(Exception ex) {}
    }
}
