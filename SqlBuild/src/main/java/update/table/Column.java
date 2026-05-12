package update.table;

import util.guard.SQLStringGuard;
import util.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

public class Column {
    private String name;
    private List<String> tableValues;

    public Column(String name, List<String> values) {
        validate(name);

        if (values == null) {
            tableValues = new ArrayList<>();
        } else {
            tableValues = values;
        }
    }

    public Column() {
        name = "";
        tableValues = new ArrayList<>();
    }

    public void setColumnName(String column) {
        validate(column);

        name = column;
    }

    public void setValues(String... values) {
        setValues(Mapper.toList(values));
    }

    public void setValues(List<String> values) {
        if (values.size() == 0) {
            throw new IllegalArgumentException("No values provided.");
        }

        tableValues = values;
    }

    public String getName() {
        return name;
    }

    public List<String> getValues() {
        return tableValues;
    }

    private void validate(String value) {
        if (SQLStringGuard.isNameInValid(name)) {
            throw new IllegalArgumentException("Invalid Column name, only [A-Za-z0-9_] chars allowed.");
        }

        if (SQLStringGuard.hasQuotes(name)) {
            throw new IllegalArgumentException("Column name cannot have quotes.");
        }
    }
}
