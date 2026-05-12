package update.table;

import java.util.LinkedHashMap;
import java.util.List;

public class TableDefinitionBuilder {
    public LinkedHashMap<String, Column> table; // allows to preserve insertion order

    public TableDefinitionBuilder() {
        table = new LinkedHashMap<>();
    }

    public void addColumn(String name) {
        if (table.containsKey(name)) {
            throw new IllegalArgumentException("Column already exists");
        }

        table.put(name, new Column(name, null));
    }

    public void addValues(String name, List<String> values) {
        if (!table.containsKey(name)) {
            throw new IllegalArgumentException("Column does not exist.");
        }

        var column = table.get(name);

        column.setValues(values);
    }

    public void addValues(String name, String... values) {
        if (!table.containsKey(name)) {
            throw new IllegalArgumentException("Column does not exist.");
        }

        var column = table.get(name);

        column.setValues(values);
    }

    public List<Column> getByInsertionOrder() {
        return table.values().stream().toList();
    }
}
