package query.expression;

import client.enums.SQLFunction;
import validator.StringValidator;

public enum SQLFunctions {
    MAX("MAX"),
    MIN("MIN"),
    COUNT("COUNT"),
    AVG("AVG"),
    SUM("SUM");


    String key;

    SQLFunctions(String key){
        this.key = key;
    }

    public String getKey() { return key; }

    public static String getKeyWithValue(String field, SQLFunction type) {
        if(StringValidator.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException("field cannot be null or empty");
        }

        if(type == null) {
            return field;
        }

        for(SQLFunctions sqlFunction: SQLFunctions.values()) {
            if(sqlFunction.ordinal() == type.ordinal()) {
                return String.format("%s(%s)", sqlFunction.getKey(), field);
            }
        }

        return field;
    }
}
