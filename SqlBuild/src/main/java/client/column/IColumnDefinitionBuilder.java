package client.column;

import client.enums.SQLFunction;
import client.models.FieldDefinition;
import exception.EmptyQueryException;
import subquery.SubQuerySimpleBuilder;

import java.util.List;
import java.util.function.Consumer;

public interface IColumnDefinitionBuilder {
    IColumnDefinitionBuilder setColumn(String field, SQLFunction function);
    IColumnDefinitionBuilder setColumn(String field);
    IColumnDefinitionBuilder setColumnAsQuery(Consumer<SubQuerySimpleBuilder> subQueryBuilder, String asFieldName) throws EmptyQueryException;
    List<FieldDefinition> toList();
}
