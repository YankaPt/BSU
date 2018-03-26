import com.jankothebest.*;

import java.util.ArrayList;

/**
 * Created by jan on 27.10.17.
 */
public class SeriesTestDrive {
    public static void main(String[] args) {
        Liner liner = new Liner(1,1);
        Exponential exponential = new Exponential(2,2);
        Liner newLiner = new Liner(2,2);
        System.out.println(liner.getSum());
        System.out.println(liner.elementAt(10));
        System.out.println(liner.getSum());
        System.out.println(liner.toString(10));
        //liner.saveToFile("output.txt");
        System.out.println("_______________________________________________________");
        System.out.println(exponential.getSum());
        System.out.println(exponential.elementAt(10));
        System.out.println(exponential.getSum());
        System.out.println(exponential.toString(10));
        //exponential.saveToFile("output.txt");
        //exponential.saveToFile("output.txt");
        System.out.println("_______________________________________________________");
        System.out.println(newLiner.getSum());
        System.out.println(newLiner.toString(10));
        System.out.println("_______________________________________________________");
        ArrayList<Series> arrayList = new ArrayList<Series>();
        //arrayList.add(liner);
        //arrayList.add(exponential);
        arrayList = Series.loadFromFile("com/output.txt");
        System.out.println(arrayList.get(2).toString(20));
    }
}
