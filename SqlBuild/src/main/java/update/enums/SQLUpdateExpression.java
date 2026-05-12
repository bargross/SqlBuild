package update.enums;

import query.expression.SQLFunctions;
import util.guard.StringGuard;

public enum SQLUpdateExpression {
    UPDATE("UPDATE"),
    SET("SET"),
    WHERE("WHERE");

    String sqlKeyword;

    SQLUpdateExpression(String keyword) {
        sqlKeyword = keyword;
    }

    public String getKeywordWithPostFix(String postFix) { return String.format("%s %s", sqlKeyword, postFix); }
}
