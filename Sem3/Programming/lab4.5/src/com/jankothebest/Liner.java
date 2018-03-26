package com.jankothebest;

/**
 * Created by jan on 27.10.17.
 */
public class Liner extends Series
{
    public Liner() {initialData="Arifmetic Progression with a0 = "+ startElement+ " , and d = " + q + " : ";}
    public Liner(double startElement, double q) {
        super(startElement, q);
        initialData="Arifmetic Progression with a0 = "+ startElement+ " , and d = " + q + " : ";
    }
    public Liner(double startElement,double q,int length)
    {
        super(startElement, q, length);
        initialData="Arifmetic Progression with a0 = "+ startElement+ " , and d = " + q + " : ";
    }
    public double elementAt(int index)
    {
        return this.startElement +index*this.q;
    }
    public double getSum(int length) {
        return (2*startElement+q*(length-1))*length/2;
    }
    public double getSum() {
        return this.getSum(seriesLength);
    }
}