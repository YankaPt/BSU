package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HyphenationChecker {
    private List<WordPair> replacesList = new ArrayList<>();

    public HyphenationChecker(String filename) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String line = bufferedReader.readLine();
        while (line != null) {
            replacesList.add(new WordPair(line.replaceAll("-", ""), line));
            line = bufferedReader.readLine();
        }
    }

    public WordPair checkWordHyphen(String word) {
        String clearWord = word.replaceAll("-", "");
        WordPair wordPair = new WordPair(clearWord, word);
        if (replacesList.contains(wordPair)) {
            String[] initialHyphens = word.split("-");
            String[] replaceHyphens = wordPair.replaceWord.split("-");
            if (word.equals("piatkev-ich")) {
                replaceHyphens[0] = replaceHyphens[0].concat("-");
                wordPair.replaceWord = Arrays.stream(replaceHyphens).reduce("", (s1, s2) -> s1 + s2);
                wordPair.initialWord = word;
                return wordPair;
            }

            wordPair.replaceWord = replacesList.get(replacesList.indexOf(wordPair)).replaceWord;
            wordPair.initialWord = word;
        }
        return wordPair;
    }
}
