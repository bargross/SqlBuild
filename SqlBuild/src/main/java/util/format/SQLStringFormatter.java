package util.format;

public final class SQLStringFormatter {
    public static String escapeQuotes(String value) {
        return value.replace("'", "''");
    }

    public static String addQuotes(String value) {
        return String.format("'%s'", value);
    }
}
