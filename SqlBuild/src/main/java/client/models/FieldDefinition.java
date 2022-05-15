package client.models;

import client.enums.SQLFunction;

public class FieldDefinition {
    public FieldDefinition() { }
    public FieldDefinition(String field, SQLFunction function) {
        this.field = field;
        this.function = function;
    }

    private String field;
    private SQLFunction function;

    public String getField() { return field; }
    public SQLFunction getFunction() { return function; }
    public void setField(String field) { this.field = field; }
    public void setSQLFunction(SQLFunction function) { this.function = function; }
}
