/**
 * Created by jan on 13.10.17.
 */
import com.sun.deploy.util.StringUtils;
import java.io.*;
import java.util.*;
public class lab2 {
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
    public static void main(String[] args) {
        String userInput = getUserInput("Enter string");
        StringBuilder str = new StringBuilder(userInput);
        int i=0;
        int numberOfSameChars = 1;
        char currentChar = str.charAt(i);
        while (i<str.length()-1) {
            if(currentChar == str.charAt(i+1)) {
                numberOfSameChars++;
                str.deleteCharAt(i+1);
            }
            else {
                if (numberOfSameChars!=1) {
                    str.insert(i + 1, numberOfSameChars);
                    numberOfSameChars = 1;
                    i++;
                }
                currentChar= str.charAt(i+1);
                i++;
            }
        }
        if (numberOfSameChars!=1)
            str.insert(i+1, numberOfSameChars);
        String result = str.toString();
        System.out.println(result);
    }
}
