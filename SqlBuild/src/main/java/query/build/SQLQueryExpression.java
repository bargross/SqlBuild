package query.build;


public enum SQLQueryExpression {
    SELECT("SELECT"),
    SELECTTOP("SELECT TOP"),
    SELECTDISTINCT("SELECT DISTINCT"),
    FROM("FROM"),
    WHERE("WHERE"),
    ORDERBY("ORDER BY"),
    GROUPBY("GROUP BY"),
    JOIN("JOIN"),
    INNERJOIN("INNER JOIN"),
    OUTERJOIN("OUTER JOIN"),
    LEFTJOIN("LEFT JOIN"),
    RIGHTJOIN("RIGHT JOIN"),
    FULLOUTERJOIN("FULL OUTER JOIN"),
    LIKE("LIKE"),
    IN("IN"),
    GO("GO;");

    private final String key;

    SQLQueryExpression(String keyword) {
        this.key = keyword;
    }

    public String getKeyword() { return key; }

    public String getKeywordWithPostFix(String postFix) { return String.format("%s %s", key, postFix); }
    public String getKeywordWithPreFix(String preFix) { return String.format("%s %s", preFix, key); }
}
