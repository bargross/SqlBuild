package sql;

import client.enums.SQLFunction;
import client.column.ColumnDefinitionBuilder;
import client.column.IColumnDefinitionBuilder;
import exception.EmptyQueryException;
import subquery.SubQuerySimpleBuilder;

import java.util.function.Consumer;

public final class SQLColumn {
    private SQLColumn() { }

    public static IColumnDefinitionBuilder setColumn(String column) {
        return new ColumnDefinitionBuilder().setColumn(column);
    }

    public static IColumnDefinitionBuilder setColumn(String column, SQLFunction function) {
        return new ColumnDefinitionBuilder().setColumn(column, function);
    }

    public static IColumnDefinitionBuilder setColumnAsQuery(Consumer<SubQuerySimpleBuilder> subQueryBuilder, String asFieldName) throws EmptyQueryException  {
        return new ColumnDefinitionBuilder().setColumnAsQuery(subQueryBuilder, asFieldName);
    }
}
