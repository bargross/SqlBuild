package query;


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
    IN("IN");

    private String key;

    SQLQueryExpression(String keyword) {
        this.key = keyword;
    }

    public String getKeyword() { return key; }

    public <TValue extends Object> String getKeywordWithPostFix(TValue postFix) { return key + postFix.toString(); }
    public <TValue extends Object> String getKeywordWithPreFix(TValue preFix) { return preFix.toString() + key; }
}
