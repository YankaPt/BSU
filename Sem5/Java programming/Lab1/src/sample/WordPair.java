package sample;

import java.util.Objects;

public class WordPair {
    String initialWord;
    String replaceWord;

    public WordPair(String initialWord, String replaceWord) {
        this.initialWord = initialWord;
        this.replaceWord = replaceWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordPair wordPair = (WordPair) o;
        return Objects.equals(initialWord, wordPair.initialWord);
    }

    @Override
    public int hashCode() {

        return Objects.hash(initialWord);
    }

    @Override
    public String toString() {
        return initialWord;
    }
}
