package query.expression;

import client.enums.SQLFunction;
import client.models.FieldDefinition;
import util.guard.ReservedKeywordGuard;
import util.mapper.Mapper;
import util.models.Tuple;
import util.guard.StringGuard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class QueryFieldExpressionBuilder implements IQueryFieldExpressionBuilder {
    private final List<FieldDefinition> columns;

    public QueryFieldExpressionBuilder() {
        columns = new ArrayList<>();
    }

    /**
     *
     * @param column field definition
     * @return IQueryFieldExpressionBuilder
     * @throws IllegalArgumentException description...
     */
    public IQueryFieldExpressionBuilder setColumn(String column, SQLFunction sqlFunc) {
        var fieldDefinition = setTableColumn(column, sqlFunc);

        columns.add(fieldDefinition);

        return this;
    }

    /**
     *
     * @param column field definition
     * @return IQueryFieldExpressionBuilder
     * @throws IllegalArgumentException description...
     */
    public IQueryFieldExpressionBuilder setColumn(String column) {
        var fieldDefinition = setTableColumn(column, SQLFunction.NOOP);

        columns.add(fieldDefinition);

        return this;
    }

    /**
     *
     * @param tableColumns field definition
     * @return IQueryFieldExpressionBuilder
     * @throws IllegalArgumentException description...
     */
    public IQueryFieldExpressionBuilder setColumns(FieldDefinition[] tableColumns) throws IllegalArgumentException{

        Arrays.stream(tableColumns).forEach(fieldDefinition -> {
            var field = fieldDefinition.getField();
            if(StringGuard.isEmptyOrWhiteSpace(field)) {
                throw new IllegalArgumentException("Invalid field");
            }

            var function = fieldDefinition.getFunction();
            if (function == null) {
                throw new NullPointerException(String.format("Missing function on function definition for field %s", field));
            }
        });

        columns.addAll(Mapper.toList(tableColumns));

        return this;
    }

    public IQueryFieldExpressionBuilder setColumns(List<FieldDefinition> tableColumns) {
        tableColumns.forEach(fieldDefinition -> {
            var field = fieldDefinition.getField();
            if(StringGuard.isEmptyOrWhiteSpace(field)) {
                throw new IllegalArgumentException("Invalid field");
            }

            var function = fieldDefinition.getFunction();
            if(fieldDefinition.getFunction() == null) {
                throw new NullPointerException(String.format("Missing function on function definition for field %s", field));
            }
        });

        columns.addAll(tableColumns);

        return this;
    }

    private FieldDefinition setTableColumn(String column, SQLFunction sqlFunc) {
        if(StringGuard.isEmptyOrWhiteSpace(column)) {
            throw new IllegalArgumentException("Field cannot be null or empty");
        }

        if(ReservedKeywordGuard.hasReservedKeywords(column)) {
            throw new IllegalArgumentException("Field cannot be or contain a reserved sql keyword");
        }

        if(sqlFunc == null) {
            throw new NullPointerException("Invalid function selected");
        }

        return new FieldDefinition(column, sqlFunc);
    }

    public Tuple<String, String>[] getFields() {
        var mappedModels = mapClientModelsToTuples(columns);

        columns.clear();

        return mappedModels;
    }

    private  Tuple<String, String>[] mapClientModelsToTuples(List<FieldDefinition> fields) {
        if (fields.isEmpty()) {
            throw new IllegalArgumentException("Collection is empty.");
        }

        if(fields.stream().anyMatch(Objects::isNull)) {
            throw new NullPointerException("Invalid field mapping found in sequence");
        }

        return Mapper.toArray(fields, fieldMapping -> {
            var field = fieldMapping.getField();

            var function = fieldMapping.getFunction();
            if (function == SQLFunction.NOOP) {
                return new Tuple<>(field, null);
            }

            return new Tuple<>(field, SQLFunctions.getKeyWithValue(field, function));
        });
    }
}
