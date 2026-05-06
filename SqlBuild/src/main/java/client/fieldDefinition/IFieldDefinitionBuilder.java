package client.fieldDefinition;

import client.enums.SQLFunction;
import client.models.FieldDefinition;
import exception.EmptyQueryException;
import subquery.SubQuerySimpleBuilder;

import java.util.List;
import java.util.function.Consumer;

public interface IFieldDefinitionBuilder {
    IFieldDefinitionBuilder setColumn(String field, SQLFunction function);
    IFieldDefinitionBuilder setColumn(String field);
    IFieldDefinitionBuilder setColumnAsQuery(Consumer<SubQuerySimpleBuilder> subQueryBuilder, String asFieldName) throws EmptyQueryException;
    List<FieldDefinition> toList();
}
