import com.jankothebest.*;

import java.util.ArrayList;

/**
 * Created by jan on 27.10.17.
 */
public class SeriesTestDrive {
    public static void main(String[] args) {
        Liner liner = new Liner(1,1);
        Exponential exponential = new Exponential(2,2);
        UndefinedProgression undefinedProgression = new UndefinedProgression("1 2 3 4 5 6");
        Liner newLiner = new Liner(2,2);
        newLiner = (Liner) Series.polymorfh("Liner");
        System.out.println(liner.sum());
        System.out.println(liner.elementAt(10));
        System.out.println(liner.sum());
        System.out.println(liner.toString(10));
        liner.saveToFile("output.txt");
        System.out.println("_______________________________________________________");
        System.out.println(exponential.sum());
        System.out.println(exponential.elementAt(10));
        System.out.println(exponential.sum());
        System.out.println(exponential.toString(10));
        exponential.saveToFile("output.txt");
        System.out.println("_______________________________________________________");
        System.out.println(undefinedProgression.toString(5));
        System.out.println(undefinedProgression.elementAt(8));
        System.out.println("_______________________________________________________");
        System.out.println(newLiner.sum());
        System.out.println(newLiner.toString(10));
        System.out.println("_______________________________________________________");
        ArrayList<Series> arrayList = new ArrayList<Series>();
        arrayList.add(liner);
        arrayList.add(exponential);
        arrayList.add(undefinedProgression);
        arrayList = Series.loadFromFile("com/output.txt");
        System.out.println(arrayList.get(1).toString(5));
    }
}
