package com.jankothebest;

/**
 * Created by jan on 30.10.17.
 */
public class UndefinedProgression extends Series {
    private UndefinedProgression() {};
    public UndefinedProgression(String input) {
        this.makeFromString(input);
        initialData="Undefined Progression: ";
    }

    public double elementAt(int index) {
        if (index>seriesLength)
            System.out.println("Unknowed element of undefined progression");
        return elements[index];
    }
    public String estimateTypeOfProgression() {
        String result = "Fail";
        return result;
    }
}
