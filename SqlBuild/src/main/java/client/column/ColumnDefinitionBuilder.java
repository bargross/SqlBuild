package client.column;

import client.enums.SQLFunction;
import client.models.FieldDefinition;
import exception.EmptyQueryException;
import subquery.SubQuerySimpleBuilder;
import util.guard.ReservedKeywordGuard;
import util.format.SQLStringFormatter;
import util.guard.SQLStringGuard;
import util.mapper.Mapper;
import util.guard.StringGuard;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ColumnDefinitionBuilder implements IColumnDefinitionBuilder {
    private List<FieldDefinition> fields;
    private boolean fieldsInstantiated;

    public ColumnDefinitionBuilder() {
        fieldsInstantiated = false;
    }

    public IColumnDefinitionBuilder setColumn(String field) {
        return setColumnField(field, SQLFunction.NOOP);
    }

    public IColumnDefinitionBuilder setColumn(String field, SQLFunction function) {
        return setColumnField(SQLStringFormatter.addQuotes(field), function);
    }

    public IColumnDefinitionBuilder setColumnAsQuery(Consumer<SubQuerySimpleBuilder> subQueryBuilder, String asFieldName) throws EmptyQueryException {
        if (subQueryBuilder == null) {
            throw new IllegalArgumentException("Query builder delegate is null");
        }

        if (StringGuard.isEmptyOrWhiteSpace(asFieldName)) {
            throw new IllegalArgumentException("field name cannot be empty, null or white space");
        }

        if (ReservedKeywordGuard.hasReservedKeywords(asFieldName)) {
            throw new IllegalArgumentException(String.format("Forbidden keyword in as field name, invalid %s", asFieldName));
        }

        var queryBuilder = new SubQuerySimpleBuilder();
        subQueryBuilder.accept(queryBuilder);

        return setColumnField(queryBuilder.as(asFieldName).getSqlString(), SQLFunction.NOOP);
    }

    private ColumnDefinitionBuilder setColumnField(String column, SQLFunction function) {

        if (StringGuard.isEmptyOrWhiteSpace(column)) {
            throw new IllegalArgumentException("Column name cannot be null, empty or white space.");
        }

        if (!SQLStringGuard.isNameInValid(column)) {
            throw new IllegalArgumentException("Column name is not valid, only letters, numbers and characters such as _ are allowed.");
        }

        if (SQLStringGuard.hasQuotes(column)) {
            throw new IllegalArgumentException("Quotes are not allowed in column names.");
        }

        if (ReservedKeywordGuard.hasReservedKeywords(column)) {
            throw new IllegalArgumentException("Column name contains forbidden SQL keyword/s.");
        }

        if (function == null) {
            throw new NullPointerException("SQL Function not provided.");
        }

        if (!fieldsInstantiated) {
            fields = new ArrayList<>();
            fieldsInstantiated = true;
        }

        var fieldWithQuotesEscaped = SQLStringFormatter.escapeQuotes(column); // internally such as O'Reilly
        var fieldNameWithQuotes = SQLStringFormatter.addQuotes(fieldWithQuotesEscaped);

        fields.add(new FieldDefinition(fieldNameWithQuotes, function));

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
