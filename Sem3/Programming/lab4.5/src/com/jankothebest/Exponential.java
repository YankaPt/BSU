package com.jankothebest;

/**
 * Created by jan on 27.10.17.
 */
public class Exponential extends Series
{
    public Exponential()
    {
        initialData="Geometric Progression with b0 = "+ startElement+ " , and q = " + q + " : ";
    }
    public Exponential(double startElement,double q)
    {
        super(startElement, q);
        initialData="Geometric Progression with b0 = "+ startElement+ " , and q = " + q + " : ";
    }
    public Exponential(double startElement,double q,int length)
    {
        super(startElement, q, length);
        initialData="Geometric Progression with b0 = "+ startElement+ " , and q = " + q + " : ";
    }
    public double elementAt(int index)
    {
        return startElement*Math.pow(q,index-1);
    }
    public double getSum(int length) {
        if(q==1)
            return startElement*length;
        else
            return startElement*(1-Math.pow(q,length))/(1-q);
    }
    public double getSum() {
        return this.getSum(seriesLength);
    }
}