package query.expression;

import client.enums.SQLFunction;
import client.models.FieldDefinition;

import java.util.List;

public interface IQueryFieldExpressionBuilder {
    IQueryFieldExpressionBuilder setColumn(String field, SQLFunction sqlFunc);
    IQueryFieldExpressionBuilder setColumn(String field);
    @SuppressWarnings("return")
    IQueryFieldExpressionBuilder setColumns(List<FieldDefinition> fields);
    IQueryFieldExpressionBuilder setColumns(FieldDefinition[] fields);
}
