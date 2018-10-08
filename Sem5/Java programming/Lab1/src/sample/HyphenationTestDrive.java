package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HyphenationTestDrive {
    public static void main(String[] args) {
        HyphenationChecker checker = null;
        try {
            checker = new HyphenationChecker("hyphenation.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        StringBuilder data = new StringBuilder();
        try {
            FileReader fileReader = new FileReader("test.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();;
            while (line != null) {
                data.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String text = data.toString();
        System.out.println(text);
        String[] words = text.split(" ");
        List<WordPair> list = new ArrayList<>();
        for (String word : words) {
            list.add(checker.checkWordHyphen(word));
        }

        for (WordPair pair : list) {
            text =text.replaceAll(pair.initialWord, pair.replaceWord);
        }
        System.out.println(text);
    }
}
