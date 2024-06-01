package query.expression;

import client.enums.SQLFunction;
import client.models.FieldDefinition;
import util.mapper.Mapper;
import util.models.Tuple;
import util.guard.StringGuard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryFieldExpressionBuilder implements IQueryFieldExpressionBuilder {
    private List<FieldDefinition> fields;

    public QueryFieldExpressionBuilder() {
        fields = new ArrayList<>();
    }

    public IQueryFieldExpressionBuilder set(String field, SQLFunction sqlFunc) {
        var fieldDefinition = setField(field, sqlFunc);

        fields.add(fieldDefinition);

        return this;
    }

    public IQueryFieldExpressionBuilder setMany(FieldDefinition[] fields) {

        Arrays.stream(fields).forEach(fieldDefinition -> {
            var field = fieldDefinition.getField();
            if(StringGuard.isEmptyOrWhiteSpace(field)) {
                throw new IllegalArgumentException("Invalid field");
            }

            var function = fieldDefinition.getFunction();
            if(fieldDefinition.getFunction() == null) {
                throw new NullPointerException(String.format("Missing function on function definition for field %s", field));
            }
        });

        this.fields.addAll(Mapper.toList(fields));

        return this;
    }

    public IQueryFieldExpressionBuilder setMany(List<FieldDefinition> fields) {
        fields.forEach(fieldDefinition -> {
            var field = fieldDefinition.getField();
            if(StringGuard.isEmptyOrWhiteSpace(field)) {
                throw new IllegalArgumentException("Invalid field");
            }

            var function = fieldDefinition.getFunction();
            if(fieldDefinition.getFunction() == null) {
                throw new NullPointerException(String.format("Missing function on function definition for field %s", field));
            }
        });

        this.fields.addAll(fields);

        return this;
    }

    private FieldDefinition setField(String field, SQLFunction sqlFunc) {
        if(StringGuard.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException("Field cannot be null or empty");
        }

        if(StringGuard.isForbiddenKeyword(field)) {
            throw new IllegalArgumentException("Field cannot be or contain a reserved sql keyword");
        }

        if(sqlFunc == null) {
            throw new NullPointerException("Invalid function selected");
        }

        return new FieldDefinition(field, sqlFunc);
    }

    public Tuple<String, String>[] getFields() {
        var mappedModels = mapClientModelsToTuples(fields);

        fields.clear();

        return mappedModels;
    }

    private  Tuple[] mapClientModelsToTuples(List<FieldDefinition> fields) {
        if(fields.size() == 0) {
            return new Tuple[0];
        }

        if(fields.stream().anyMatch(field -> field == null)) {
            throw new NullPointerException("Invalid field mapping found in sequence");
        }

        return Mapper.mapToArray(fields, fieldMapping -> {
            var field = fieldMapping.getField();

            var function = fieldMapping.getFunction();
            if(function == SQLFunction.NOOP) {
                return new Tuple<>(field, null);
            }

            return new Tuple<>(field, SQLFunctions.getKeyWithValue(field, function));
        });
    }
}
