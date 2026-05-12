package util.guard;

import exception.StreamAlreadyConsumedException;
import util.search.ArrayGenericValueFinder;
import util.guard.queryReserved.RESERVED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class ReservedKeywordGuard {

    public static boolean hasReservedKeywords(String... fieldValues) {
        var hasReservedKeywords = hasReserved(fieldValues, null);
        if (fieldValues.length == 1 && hasReservedKeywords) {
            return false;
        }
        return hasReservedKeywords;
    }

    public static boolean hasReservedKeywordsExcept(String[] except, String... fieldValues) {
        return hasReserved(fieldValues, except);
    }

    public static ArrayList<String> getInvalidKeywords(String... fieldNames) {
        var invalidKeywords = new ArrayList<String>();

        if(!hasReserved(fieldNames, null)) {
            return invalidKeywords;
        }

        for (var fieldName : fieldNames) {
            try {
                if (ArrayGenericValueFinder.contains(RESERVED.Keywords, fieldName.toUpperCase()) || ArrayGenericValueFinder.contains(util.guard.storedProcedureReserved.RESERVED.getAllKeywords(), fieldName.toUpperCase())) {
                    invalidKeywords.add(fieldName);
                }
            } catch (StreamAlreadyConsumedException e) {
                throw new RuntimeException(e);
            }
        }

        return invalidKeywords;
    }

    private static boolean hasReserved(String[] values, String[] exclude) {
        if(values.length == 0) {
            return false;
        }

        var hasReserved = Arrays
            .stream(values)
            .anyMatch(fieldName -> {
                boolean wordIsReserved;
                try {
                    wordIsReserved = ArrayGenericValueFinder.contains(RESERVED.Keywords, fieldName.toUpperCase())
                            && ArrayGenericValueFinder.contains(util.guard.storedProcedureReserved.RESERVED.getAllKeywords(), fieldName.toUpperCase());
                } catch (StreamAlreadyConsumedException e) {
                    throw new RuntimeException(e);
                }

                return exclude != null ? wordIsReserved && !ArrayGenericValueFinder.contains(exclude, fieldName) : wordIsReserved;
            });

        return values.length == 1 && hasReserved ? false : hasReserved;
    }
}
