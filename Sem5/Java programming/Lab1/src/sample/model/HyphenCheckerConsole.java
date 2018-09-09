package sample.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HyphenCheckerConsole {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("file.txt"));
            String currentWord;
            while (scanner.hasNext("([A-Z]|[a-z])*")) {
                currentWord = scanner.next("([A-Z]|[a-z])*");

                System.out.println(currentWord);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException was thrown");
        }
    }
}
