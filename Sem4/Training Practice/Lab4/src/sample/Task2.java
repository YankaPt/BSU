package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {
    public static void main(String[] args) {
        String test = "sfafsa24.06.1999fdsfsdf12.09.20174r34gfdgd13.06.3456\n" +
                "gdfg23.07.5432";
        List<String> dates = new ArrayList<>();
        String regex = "(0[1-9]|[12]\\d|3[01])[./](0[1-9]|1[0-2])[./]\\d{4}";
        Matcher m = Pattern.compile(regex).matcher(test);
        while (m.find()) {
            dates.add(m.group(1));
        }
        System.out.println(dates);
    }
}
