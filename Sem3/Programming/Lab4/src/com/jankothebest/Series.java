package com.jankothebest;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by jan on 27.10.17.
 */
abstract public class Series {
    protected double [] elements = new double[100];
    protected int seriesLength =1;
    protected String initialData= "Abstract progression ";

    protected void calculateSequence(int length) {
        System.out.print("It's parent method");
        seriesLength = length;
    }

    public abstract double elementAt(int index);

    public double sum() {
        double sum=0;
        for (double d:elements)
            sum+=d;
        return sum;
    }

    public String toString(int length) {
        if (seriesLength<length)
            this.calculateSequence(length);
        StringBuilder str = new StringBuilder();
        str.append(initialData);
        for (int i=0; i<length; i++) {
            str.append(elements[i]);
            str.append(" ");
        }
        str.append("\n");
        return str.toString();
    }

    public void saveToFile(String filename) {
        try(FileWriter writer = new FileWriter(filename, true);) {
            writer.write(this.toString(seriesLength));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void makeFromString(String input) {
        String[] tokens=input.split(" ");
        seriesLength = tokens.length;
        for (int i=0; i<tokens.length; i++) {
            elements[i] = Double.parseDouble(tokens[i]);
        }
    }
    public static Series polymorfh(String promt) {
        if(promt.equals("Liner"))
            return new Liner(3,3);
        if(promt.equals("Exponential"))
            return new Exponential(3,3);
        else
            return new UndefinedProgression("0");
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
                    arrayList.add(new Liner(Double.parseDouble(InitData[5]), Double.parseDouble(InitData[10]), stringElements[1]));
                }
                if (stringElements[0].contains("Geometric")) {
                    String[] InitData = stringElements[0].split(" ");
                    arrayList.add(new Exponential(Double.parseDouble(InitData[5]), Double.parseDouble(InitData[10]), stringElements[1]));
                } else
                    arrayList.add(new UndefinedProgression(stringElements[1]));
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return arrayList;
        }
    }
}
