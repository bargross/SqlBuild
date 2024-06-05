package query.expression;

import client.enums.SQLFunction;
import client.models.FieldDefinition;

import java.util.List;

public interface IQueryFieldExpressionBuilder {
    IQueryFieldExpressionBuilder set(String field, SQLFunction sqlFunc);
    @SuppressWarnings("return")
    IQueryFieldExpressionBuilder setMany(List<FieldDefinition> fields);
    IQueryFieldExpressionBuilder setMany(FieldDefinition[] fields);
}
