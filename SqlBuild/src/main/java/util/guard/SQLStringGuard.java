package util.guard;

import java.util.regex.Pattern;

public final class SQLStringGuard {

    public static boolean isNameInValid(String tableName) {
        var pattern = Pattern.compile(tableName, Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher("[A-Za-z0-9_]");

        return !matcher.find();
    }

    public static boolean hasQuotes(String value) {
        var firstChar = "" + value.charAt(0);
        var lastChar = "" + value.charAt(value.length() - 1);

        return firstChar == "'" || lastChar == "'";
    }
}
