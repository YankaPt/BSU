/**
 * Created by jan on 6.10.17.
 */
import java.io.*;

import static java.lang.Math.abs;

public class Lab1 {
    public static class InvalidAccuracy extends Exception {
        public InvalidAccuracy (String message) {
            super(message);
        }
    }
    public static class InvalidX extends Exception {
        public InvalidX (String message) {
            super(message);
        }
    }
    public static class InvalidEnterException extends RuntimeException {
        public InvalidEnterException(String message) {
            super(message);
        }
    }
    public static String getUserInput(String prompt) {
        String inputLine = null;
        System.out.print(prompt + "  ");
        try {
            BufferedReader is = new BufferedReader(
                    new InputStreamReader(System.in));
            inputLine = is.readLine();
            if (inputLine.length() == 0 )  return null;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return inputLine.toLowerCase();
    }
    public static double sum(double e, double x) throws InvalidAccuracy, InvalidX {
        if(e==0) {
            throw new InvalidAccuracy("You are invalid");
        }
        if(x==0) {
            throw new InvalidX("You are invalid");
        }
        int i=1;
        double a=1.0;
        double sum=0.0;
        while(abs(a)>=e) {
            a=a*(-1)*(i+1)*x/(3*i);
            sum+=a;
            i++;
        }
        return sum;
    }
    public static void main(String[] args){
        try {
            if (args.length != 2)
                throw new InvalidEnterException("Must be 2 ");
            //double e=Double.parseDouble(getUserInput("Enter accuracy"));
            //double x=Double.parseDouble(getUserInput("Enter x"));
            double e = Double.parseDouble(args[0]);
            double x = Double.parseDouble(args[1]);
            double sum = sum(e, x);
            System.out.print(sum);}
            catch (NumberFormatException cex) {
                System.out.print("Invalid Enter");
        } catch (InvalidAccuracy pex) {
            System.out.print("Accuracy can't be zero");
        } catch (InvalidX lex) {
            System.out.print("X can't be zero");
        } catch (InvalidEnterException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
