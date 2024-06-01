package util.guard.storedProcedureReserved;

import util.mapper.Mapper;

import java.util.Arrays;

public enum RESERVED {
    ADD("ADD"),
    ALTER("ALTER"),
    AND("AND"),
    ANY("ANY"),
    AS("AS"),
    ASC("ASC"),
    BACKUP("BACKUP"),
    BETWEEN("BETWEEN"),
    CASE("CASE"),
    CHECK("CHECK"),
    CREATE("CREATE"),
    REPLACE("REPLACE"),
    PROCEDURE("PROCEDURE"),
    DEFAULT("DEFAULT"),
    DELETE("DELETE"),
    DESC("DESC"),
    DROP("DROP"),
    COLUMN("COLUMN"),
    CONSTRAINT("CONSTRAINT"),
    DATABASE("DATABASE"),
    EXEC("EXEC"),
    EXISTS("EXISTS"),
    FOREIGN("FOREIGN"),
    KEY("KEY"),
    HAVING("HAVING"),
    IN("IN"),
    INDEX("INDEX"),
    INSERT("INSERT"),
    IS("IS"),
    NULL("NULL"),
    NOT("NOT"),
    JOIN("JOIN"),
    LIKE("LIKE"),
    LIMIT("LIMIT"),
    OR("OR"),
    PRIMARY("PRIMARY"),
    ROWNUM("ROWNUM"),
    SELECT("SELECT"),
    SET("SET"),
    TABLE("TABLE"),
    TOP("TOP"),
    TRUNCATE("TRUNCATE"),
    UNION("UNION"),
    ALL("ALL"),
    UNIQUE("UNIQUE"),
    UPDATE("UPDATE"),
    VALUES("VALUES"),
    INTO("INTO"),
    VIEW("VIEW");


    String keyword;

    RESERVED(String keyword) {
        this.keyword = keyword;
    }

    public static String[] getAllKeywords() {
        return Mapper.mapToArray(Mapper.toList(Arrays.stream(RESERVED.values())), x -> x.keyword);
    }

    public String getKeyword() {
        return this.keyword;
    }

    public String getKeywordWithPrefix(RESERVED value) {
        return String.format("%s %s", value.getKeyword(), this.keyword);
    }

    public String getKeywordWithPrefixAndSuffix(RESERVED prefix, String value, RESERVED suffix) {
        return String.format("%s %s %s", getKeywordWithPrefix(prefix), value, suffix.getKeyword());
    }
}
