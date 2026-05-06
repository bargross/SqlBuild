package query.expression;

import util.guard.ReservedKeywordGuard;
import util.guard.StringGuard;

import java.util.function.Consumer;

public class QueryExpressionBuilder implements IQueryExpressionBuilder {

    private String fieldName;
    private StringBuffer builder;
    private boolean isSubQuery;

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

    public QueryExpressionBuilder() {
        builder = new StringBuffer("( ");

        isSubQuery = true;
    }

    /**
     *
     @param field field/column
     @return void
     */
    public void setField(String field) {
        if(StringGuard.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException("Invalid field");
        }

        fieldName = field;
    }

    /**
     *
     @param builder string buffer
     @return void
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

        this.builder.append(String.format("%s = %s%s", fieldName, fieldValue, System.lineSeparator()));

        return this;
    }

    /**
     *
     @param fieldValue column value
     @throws NullPointerException description...
     */
    public IQueryExpressionBuilder isEquals(String fieldValue) {
        var expression = equals(fieldValue);

        if(builder == null) {
            throw new NullPointerException("Builder (Buffer) is null");
        }

        this.builder.append(expression);

        return this;
    }

    /**
     *
     @param fieldValue field name
     @return string value containing LIKE query expression
     @throws IllegalArgumentException description
     */
    public IQueryExpressionBuilder like(String fieldValue) {
        defaultValidation(fieldValue);

        var expression = String.format("%s LIKE %s%s", fieldName, fieldValue, System.lineSeparator());

        this.builder.append(expression);

        return this;
    }

    /**
     *
     @param fieldValue fieldValue
     @throws NullPointerException description...
     */
    public IQueryExpressionBuilder isLike(String fieldValue) throws NullPointerException {
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
        if (ReservedKeywordGuard.hasReservedKeywords(fieldValues)) {
            throw new IllegalArgumentException("Forbidden word found in sequence");
        }

        var expression = String.format("%s IN (%s)%s", fieldName, String.join(",", fieldValues), System.lineSeparator());

        this.builder.append(expression);

        return this;
    }

    /**
     *
     @param fieldValues column names
     @throws NullPointerException description...
     */
    public IQueryExpressionBuilder isIn(String... fieldValues)  {

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

        this.builder.append(String.format(" OR "));

        queryExpansionDelegate.accept(this);

        this.builder.append(System.lineSeparator());

        return this;
    }

    private void defaultValidation(String value) {
        if (StringGuard.isEmptyOrWhiteSpace(value)) {
            throw new IllegalArgumentException();
        }

        if (StringGuard.isForbiddenKeyword(value)) {
            throw new IllegalArgumentException();
        }
    }
}
