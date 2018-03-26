package com.jankothebest;

/**
 * Created by jan on 27.10.17.
 */
public class Exponential extends Series{
    private double b;
    private double q;

    private Exponential() {};
    public Exponential(double b0, double d) {
        b=b0; q=d; elements[0]=b;
        initialData="Geometric Progression with b0 = "+ b+ " , and q = " + q + " : ";
    }
    public Exponential(double b0, double d, String input) {
        this.makeFromString(input);
        b=b0; q=d;
        initialData="Geometric Progression with b0 = "+ b+ " , and q = " + q + " : ";
    }

    public double elementAt(int index) {
        if (index>seriesLength)
            this.calculateSequence(index);
        return elements[index];
    }

    protected void calculateSequence(int length) {
        for (int i=seriesLength; i<=length; i++)
            elements[i]=elements[i-1]*q;
        seriesLength = length;
    }
}
