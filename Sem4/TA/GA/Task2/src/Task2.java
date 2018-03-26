import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        int n;
        int m;
        int tempV1, tempV2;
        ArrayList <Integer> arr;
        ArrayList<ArrayList<Integer>> a = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("input.txt"));
            n = scanner.nextInt();
            m = scanner.nextInt();
            for(int i=0; i<n; i++){
                arr=new ArrayList<>();
                a.add(arr);
            }

            for (int i=0; i<m;i++) {
                tempV1 = scanner.nextInt();
                tempV2 = scanner.nextInt();

                a.get(tempV1-1).add(tempV2);
                a.get(tempV2-1).add(tempV1);

            }
        } catch(Exception ex) {}
        try {
            PrintWriter printWriter = new PrintWriter(new File("output.txt"));
            for (ArrayList list : a) {
                printWriter.print(list.size()+" ");
                for (int i = 0; i<list.size(); i ++) {
                    printWriter.print(list.get(i)+" ");
                }
                printWriter.println();
            }
            printWriter.close();
        } catch(Exception ex) {}
    }
}
