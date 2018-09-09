package sample.model;

class HyphenCheckerPair {
    String initial;
    String replacement;

    public HyphenCheckerPair(String initial, String replacement) {
        this.initial = initial;
        this.replacement = replacement;
    }

    public String getInitial() {
        return initial;
    }

    public String getReplacement() {
        return replacement;
    }
}
