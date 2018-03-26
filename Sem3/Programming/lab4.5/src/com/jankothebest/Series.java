package com.jankothebest;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jan on 27.10.17.
 */
public abstract class Series
{
    protected double startElement;
    protected double q;
    protected int seriesLength =1;
    protected String initialData= "Abstract progression ";

    Series() {};
    Series(double startElement, double q) {
        this.startElement = startElement;
        this.q = q;
    }
    Series(double startElement,double q,int length){
        this(startElement, q);
        seriesLength = length;
    }

    abstract public double elementAt(int index);

    public void increaseQ(int number)
    {
        this.q +=number;
    }
    public void setLength(int length) {
        seriesLength = length;
    }

    public double getStartElement() {
        return startElement;
    }

    public double getQ() {
        return q;
    }

    public int getLength() {
        return seriesLength;
    }


    public double getSum(int length)
    {
        double sum = startElement;
        for(int i=0; i<length; i++)
            sum+=elementAt(i);
        return sum;
    }
    public String toString()
    {
        return toString(seriesLength);
    }
    public String toString(int length) {
        StringBuilder str = new StringBuilder();
        str.append(initialData);
        for (int i=0; i<length; i++) {
            str.append(this.elementAt(i));
            str.append(" ");
        }
        str.append("\n");
        return str.toString();
    }
    public void saveToFile(String filename) throws Exception {
        try(FileWriter writer = new FileWriter(filename, true);) {
            writer.write(this.toString());
        } catch (IOException ex) {
            throw new IOException();
        }
    }
    public static ArrayList<Series> loadFromFile (String filename) {
        ArrayList<Series> arrayList = new ArrayList<Series>();
        try(InputStream is = Series.class.getClassLoader().getResourceAsStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] stringElements = line.split(": ");
                if (stringElements[0].contains("Arifmetic")) {
                    String[] InitData = stringElements[0].split(" ");
                    arrayList.add(new Liner(Double.parseDouble(InitData[5]), Double.parseDouble(InitData[10]), stringElements.length));
                }
                else if (stringElements[0].contains("Geometric")) {
                    String[] InitData = stringElements[0].split(" ");
                    arrayList.add(new Exponential(Double.parseDouble(InitData[5]), Double.parseDouble(InitData[10]), stringElements.length));
                } //else
                    //System.out.println("Wrong data!");
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return arrayList;
        }
    }
}
