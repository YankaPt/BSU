package sample.model;

public class HyphenChecker {
    private static StringBuilder stringBuilder;
    private static final String REGEX = "(([aeiouAEIOU])([qwrtypsdfghjklzxcvbnmQWRTYPSDFGHJKLZXCVBNM]))|\\3\\2";

    public static HyphenCheckerPair checkHyphenPosition(String string) {
        if(string.matches(REGEX))
            return null;
        stringBuilder = new StringBuilder(string);

    }
}
