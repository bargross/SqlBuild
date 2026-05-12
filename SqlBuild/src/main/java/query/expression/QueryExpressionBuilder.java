package query.expression;

import util.format.SQLStringFormatter;
import util.guard.ReservedKeywordGuard;
import util.guard.SQLStringGuard;
import util.guard.StringGuard;
import util.iterator.GenericIterator;
import util.mapper.Mapper;

import java.util.function.Consumer;

public class QueryExpressionBuilder implements IQueryExpressionBuilder {

    private String fieldName;
    private StringBuffer builder;

    public QueryExpressionBuilder(StringBuffer builder) {
        this(null, builder, false);
    }

    public QueryExpressionBuilder(String field, StringBuffer builder) {
        this(field, builder, true);
    }

    private QueryExpressionBuilder(String fieldName, StringBuffer builder, boolean validateField) {

        if (validateField) {
            defaultValidation(fieldName);
        }

        if (builder == null) {
            throw new NullPointerException("Buffer missing");
        }

        this.builder = builder;
    }

    /**
     *
     @param column field/column
     */
    public void setColumn(String column) {
        defaultValidation(column);

        fieldName = formatTableOrField(column);
    }

    /**
     *
     @param builder string buffer
     */
    public void setBuilder(StringBuffer builder) {
        if (builder == null) {
            throw new NullPointerException("Builder is null");
        }

        this.builder = builder;
    }

    /**
     *
     @param fieldValue column value
     @return void
     */
    public IQueryExpressionBuilder equals(String fieldValue) {
        defaultValidation(fieldValue);

        var formattedField = formatTableOrField(fieldValue);

        this.builder.append(String.format("%s = %s%s", fieldName, formattedField, System.lineSeparator()));

        return this;
    }

//    /**
//     *
//     @param fieldValue column value
//     @throws NullPointerException description...
//     */
//    public IQueryExpressionBuilder equals(String fieldValue) {
//        defaultValidation(fieldValue);
//
//        var expression = equals(fieldValue);
//
//        if(builder == null) {
//            throw new NullPointerException("Builder (Buffer) is null");
//        }
//
//        this.builder.append(expression);
//
//        return this;
//    }

    /**
     *
     @param fieldValue field name
     @return string value containing LIKE query expression
     @throws IllegalArgumentException description
     */
    public IQueryExpressionBuilder like(String fieldValue) {
        defaultValidation(fieldValue);

        var formattedField = formatTableOrField(fieldValue);

        var expression = String.format("%s LIKE %s%s", fieldName, formattedField, System.lineSeparator());

        this.builder.append(expression);

        return this;
    }

    /**
     *
     @param fieldValue fieldValue
     @throws NullPointerException description...
     */
    public IQueryExpressionBuilder isLike(String fieldValue) throws NullPointerException {
        defaultValidation(fieldValue);

        var expression = like(fieldValue);

        if (builder == null) {
            throw new NullPointerException("Builder (Buffer) is null");
        }

        this.builder.append(expression);

        return this;
    }

    /**
     *
     @param fieldValues column names
     @return string containing IN SQL query expression
     */
    public IQueryExpressionBuilder in(String... fieldValues) {
        GenericIterator.each(fieldValues, value -> defaultValidation(value));

        var formattedFieldValues = Mapper.map(fieldValues, value -> formatTableOrField(value));

        var expression = String.format("%s IN (%s)%s", fieldName, String.join(",", formattedFieldValues), System.lineSeparator());

        this.builder.append(expression);

        return this;
    }

    /**
     *
     @param fieldValues column names
     @throws NullPointerException description...
     */
    public IQueryExpressionBuilder isIn(String... fieldValues)  {

        GenericIterator.each(fieldValues, value -> defaultValidation(value));

        var expression = in(fieldValues);

        if (builder == null) {
            throw new NullPointerException("Builder (Buffer) is null");
        }

        this.builder.append(expression);

        return this;
    }

    /**
     *
     @param queryExpansionDelegate function that accepts the query builder to expand the query behind
     @throws NullPointerException description...
     */
    public IQueryExpressionBuilder or(Consumer<IQueryExpressionBuilder> queryExpansionDelegate)  {
        if (builder == null) {
            throw new NullPointerException("Builder (Buffer) is null");
        }

        this.builder = this.builder.replace(0, this.builder.lastIndexOf(System.lineSeparator()), "");

        this.builder.append(" OR ");

        queryExpansionDelegate.accept(this);

        this.builder.append(System.lineSeparator());

        return this;
    }

    private void defaultValidation(String value) {
        if (StringGuard.isEmptyOrWhiteSpace(value)) {
            throw new IllegalArgumentException("Invalid field");
        }

        if (SQLStringGuard.isNameInValid(value)) {
            throw new IllegalArgumentException("Invalid table name, only characters a-zA-Z 0-9 & _ are allowed.");
        }

        if (SQLStringGuard.hasQuotes(value)) {
            throw new IllegalArgumentException("Invalid table name, single quotes not allowed.");
        }

        if (ReservedKeywordGuard.hasReservedKeywords(value)) {
            throw new IllegalArgumentException();
        }
    }

    private String formatTableOrField(String value) {
        var nameWithEscapedQuotes = SQLStringFormatter.escapeQuotes(value);
        return SQLStringFormatter.addQuotes(nameWithEscapedQuotes);
    }
}
