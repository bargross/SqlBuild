package client.enums;

public enum SQLJoin {
    JOIN("JOIN"),
    INNER("INNER JOIN"),
    OUTER("OUTER JOIN"),
    LEFT("LEFT JOIN"),
    RIGHT("RIGHT JOIN"),
    FULLOUTER("FULL OUTER JOIN");

    String sqlJoin;

    SQLJoin(String joinType) {
        this.sqlJoin = joinType;
    }

    public String getValue() { return this.sqlJoin; }

    public static String getJoin(SQLJoin type) {
        var typeIndex = type.ordinal();

        for(SQLJoin join: SQLJoin.values()) {
            if(join.ordinal() == typeIndex) {
                return join.sqlJoin;
            }
        }

        return null;
    }
}
