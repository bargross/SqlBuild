package validator;

import util.GenericIterator;

public final class StringValidator {

    public static boolean isEmptyOrWhiteSpace(String value) {
        return value == null || value.length() == 0 || isWhiteSpace(value);
    }

    public static boolean isForbiddenKeyword(String value) {
        defaultValidation(value, true);

        return ReservedKeywordValidator.hasReservedKeywords(value.toUpperCase());
    }

    public static boolean containsExcept(String value, String... except) {
        defaultValidation(value, false);

        return ReservedKeywordValidator.hasReservedKeywordsExcept(except, value.toUpperCase());
    }

    private static void defaultValidation(String value, boolean lookupSpaces) {
        if(lookupSpaces && hasSpaces(value)) {
            throw new IllegalArgumentException("Value cannot have spaces, it must be 1 word");
        }

        if(isEmptyOrWhiteSpace(value)) {
            throw new IllegalArgumentException("Value cannot be null, empty or white space");
        }
    }


    private static boolean isWhiteSpace(String value) {
        if (value == null) {
            throw new NullPointerException("");
        }

        String whiteSpace = " ".repeat(value.length());

        return value.equals(whiteSpace);
    }

    private static boolean hasSpaces(String value) {
        if (value == null) {
            throw new NullPointerException("");
        }

        var length = value.length();
        return GenericIterator.contains(GenericIterator.toCharArray(value), (character, i) -> i < length && value.charAt(i) == ' ');
    }
}
