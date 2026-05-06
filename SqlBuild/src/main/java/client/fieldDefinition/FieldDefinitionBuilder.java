package client.fieldDefinition;

import client.enums.SQLFunction;
import client.models.FieldDefinition;
import exception.EmptyQueryException;
import subquery.SubQuerySimpleBuilder;
import util.mapper.Mapper;
import util.guard.StringGuard;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FieldDefinitionBuilder implements IFieldDefinitionBuilder {
    private List<FieldDefinition> fields;
    private boolean fieldsInstantiated;

    public FieldDefinitionBuilder() {
        fieldsInstantiated = false;
    }

    public IFieldDefinitionBuilder setColumn(String field) {
        return setColumnField(field, SQLFunction.NOOP);
    }

    public  IFieldDefinitionBuilder setColumn(String field, SQLFunction function) {
        return setColumnField(field, function);
    }

    public IFieldDefinitionBuilder setColumnAsQuery(Consumer<SubQuerySimpleBuilder> subQueryBuilder, String asFieldName) throws EmptyQueryException {
        var queryBuilder = new SubQuerySimpleBuilder();

        if (subQueryBuilder == null) {
            throw new IllegalArgumentException("Query builder delegate is null");
        }

        if (StringGuard.isEmptyOrWhiteSpace(asFieldName)) {
            throw new IllegalArgumentException("field name cannot be empty, null or white space");
        }

        if (StringGuard.isForbiddenKeyword(asFieldName)) {
            throw new IllegalArgumentException(String.format("Forbidden keyword in as field name, invalid %s", asFieldName));
        }

        subQueryBuilder.accept(queryBuilder);

        return setColumnField(queryBuilder.as(asFieldName).getSqlString(), SQLFunction.NOOP);
    }

    private FieldDefinitionBuilder setColumnField(String column, SQLFunction function) {
        if (StringGuard.isEmptyOrWhiteSpace(column)) {
            throw new IllegalArgumentException("Column name cannot be null, empty or white space.");
        }

        if (StringGuard.isForbiddenKeyword(column)) {
            throw new IllegalArgumentException("Column name has forbidden SQL keyword.");
        }

        if (function == null) {
            throw new NullPointerException("Function not provided.");
        }

        if (!fieldsInstantiated) {
            fields = new ArrayList<>();
            fieldsInstantiated = true;
        }

        fields.add(new FieldDefinition(column, function));

        return this;
    }

    public List<FieldDefinition> toList() {
        return fields;
    }

    public FieldDefinition[] toArray() {
        return Mapper.toArray(fields);
    }

    public void clear() {
        this.fields.clear();
    }
}
