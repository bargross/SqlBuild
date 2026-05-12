package util.format;

import util.guard.StringGuard;

public final class StringFormatter {
    public static String singleWordToCamelCase(String word) {
        if (StringGuard.isEmptyOrWhiteSpace(word)) {
            throw new IllegalArgumentException("Empty string given.");
        }

        var splitWord = word.split(" ");
        if (splitWord.length > 1) {
            throw new IllegalArgumentException("More than one word detected.");
        }

        var startingChar = word.charAt(0);
        return Character.toUpperCase(startingChar) + word.substring(1);
    }
}
