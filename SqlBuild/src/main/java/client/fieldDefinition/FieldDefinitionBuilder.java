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

        setField(queryBuilder.as(asFieldName).get());

        return this;
    }

    private FieldDefinitionBuilder setColumnField(String field, SQLFunction function) {
        if (StringGuard.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException("");
        }

        if (StringGuard.isForbiddenKeyword(field)) {
            throw new IllegalArgumentException("");
        }

        if (function == null) {
            throw new NullPointerException("");
        }

        if (!fieldsInstantiated) {
            fields = new ArrayList<>();
            fieldsInstantiated = true;
        }

        fields.add(new FieldDefinition(field, function));

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
