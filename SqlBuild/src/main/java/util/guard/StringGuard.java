package util.guard;

import util.iterator.GenericIterator;
import util.mapper.Mapper;

public final class StringGuard {

    public static boolean isEmptyOrWhiteSpace(String value) {
        return value == null || value.isEmpty() || isWhiteSpace(value);
    }

    public static boolean isForbiddenKeyword(String value) {
        defaultValidation(value, true);

        return ReservedKeywordGuard.hasReservedKeywords(value.toUpperCase());
    }

    public static boolean containsExcept(String value, String... except) {
        defaultValidation(value, false);

        return ReservedKeywordGuard.hasReservedKeywordsExcept(except, value.toUpperCase());
    }

    private static void defaultValidation(String value, boolean lookupSpaces) {
        if (lookupSpaces && hasSpaces(value)) {
            throw new IllegalArgumentException("Value cannot have spaces, it must be 1 word");
        }

        if (isEmptyOrWhiteSpace(value)) {
            throw new IllegalArgumentException("Value cannot be null, empty or white space");
        }
    }


    private static boolean isWhiteSpace(String value) {
        if (value == null) {
            throw new NullPointerException("Value is null");
        }

        String whiteSpace = " ".repeat(value.length());

        return value.equals(whiteSpace);
    }

    private static boolean hasSpaces(String value) {
        if (value == null) {
            throw new NullPointerException("Value is null");
        }

        if (value.isEmpty()) {
            throw new IllegalArgumentException("Value is empty");
        }

        var length = value.length();
        return GenericIterator.contains(Mapper.toCharacterArray(value), (character, i) -> i < length && value.charAt(i) == ' ');
    }
}
