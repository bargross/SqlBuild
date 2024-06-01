package client;

import client.enums.SQLFunction;
import client.models.FieldDefinition;
import util.mapper.Mapper;
import util.guard.StringGuard;

import java.util.ArrayList;
import java.util.List;

public class FieldDefinitionBuilder implements IFieldDefinitionBuilder {
    private List<FieldDefinition> fields;
    private boolean fieldsInstantiated;

    public FieldDefinitionBuilder() {
        fieldsInstantiated = false;
    }

    public FieldDefinitionBuilder setField(String field) {
        return setField(field, SQLFunction.NOOP);
    }

    public FieldDefinitionBuilder setField(String field, SQLFunction function) {
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
