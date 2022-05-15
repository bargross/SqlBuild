package query.join;

import client.enums.SQLJoin;

public enum JoinType {
    JOIN("JOIN"),
    INNER("INNER JOIN"),
    OUTER("OUTER JOIN"),
    LEFT("LEFT JOIN"),
    RIGHT("RIGHT JOIN"),
    FULLOUTER("FULL OUTER JOIN");

    String joinType;

    JoinType(String joinType) {
        this.joinType = joinType;
    }

    public String getValue() { return this.joinType; }

    public static String getJoin(SQLJoin type) {
        var typeIndex = type.ordinal();

        for(JoinType join: JoinType.values()) {
            if(join.ordinal() == typeIndex) {
                return join.joinType;
            }
        }

        return null;
    }
}
