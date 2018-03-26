package com.jankothebest;

/**
 * Created by jan on 27.10.17.
 */
public class Liner extends Series {
    private double a;
    private double d;

    private Liner() {};
    public Liner(double a0, double b) {
        a=a0; d=b; elements[0]=a;
        initialData="Arifmetic Progression with a0 = "+ a+ " , and d = " + d + " : ";
    }
    public Liner(double a0, double b, String input) {
        this.makeFromString(input);
        a=a0; d=b;
        initialData="Arifmetic Progression with a0 = "+ a+ " , and d = " + d + " : ";
    }

    public double elementAt(int index) {
        if (index>seriesLength)
            this.calculateSequence(index);
        return elements[index];
    }

    protected void calculateSequence(int length) {
        for (int i=seriesLength; i<=length; i++)
            elements[i]=elements[i-1]+d;
        seriesLength = length;
    }

}
