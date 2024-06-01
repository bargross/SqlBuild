package util.guard;

import util.search.ArrayGenericValueFinder;
import util.guard.queryReserved.RESERVED;

import java.util.Arrays;

public final class ReservedKeywordGuard {

    public static boolean hasReservedKeywords(String... fieldValues) {
        return hasReserved(fieldValues, null, false);
    }

    public static boolean hasReservedKeywordsExcept(String[] except, String... fieldValues) {
        return hasReserved(fieldValues, except, true);
    }

    private static boolean hasReserved(String[] values, String[] exclude, boolean excludeWords) {
        if(values.length == 0) {
            return false;
        }

        return Arrays
            .stream(values)
            .anyMatch(fieldValue -> {
                var wordIsReserved = ArrayGenericValueFinder.contains(RESERVED.Keywords, fieldValue.toUpperCase())
                        && ArrayGenericValueFinder.contains(util.guard.storedProcedureReserved.RESERVED.getAllKeywords(), fieldValue.toUpperCase());

                return excludeWords ? wordIsReserved && !ArrayGenericValueFinder.contains(exclude, fieldValue) : wordIsReserved;
            });
    }
}
