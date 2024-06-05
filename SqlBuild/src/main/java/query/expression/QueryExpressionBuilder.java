package query.expression;

import util.guard.ReservedKeywordGuard;
import util.guard.StringGuard;

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

        if(validateField) {
            defaultValidation(fieldName);
        }

        if(builder == null) {
            throw new NullPointerException("Buffer missing");
        }

        this.builder = builder;
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
     @return String
     */
    public String equals(String fieldValue) {
        defaultValidation(fieldValue);

        return String.format("%s = %s%s", fieldName, fieldValue, System.lineSeparator());
    }

    /**
     *
     @param fieldValue column value
     @throws NullPointerException description...
     */
    public void isEquals(String fieldValue) {
        var expression = equals(fieldValue);

        if(builder == null) {
            throw new NullPointerException("Builder (Buffer) is null");
        }

        this.builder.append(expression);
    }

    /**
     *
     @param fieldValue field name
     @return string value containing LIKE query expression
     @throws IllegalArgumentException description
     */
    public String like(String fieldValue) {
        defaultValidation(fieldValue);

        return String.format("%s LIKE %s%s", fieldName, fieldValue, System.lineSeparator());
    }

    /**
     *
     @param fieldValue fieldValue
     @throws NullPointerException description...
     */
    public void isLike(String fieldValue) throws NullPointerException {
        var expression = like(fieldValue);

        if (builder == null) {
            throw new NullPointerException("Builder (Buffer) is null");
        }

        this.builder.append(expression);
    }

    /**
     *
     @param fieldValues column names
     @return string containing IN SQL query expression
     */
    public String in(String... fieldValues) {
        if (ReservedKeywordGuard.hasReservedKeywords(fieldValues)) {
            throw new IllegalArgumentException("Forbidden word found in sequence");
        }

        return String.format("%s IN (%s)%s", fieldName, String.join(",", fieldValues), System.lineSeparator());
    }

    /**
     *
     @param fieldValues column names
     @throws NullPointerException description...
     */
    public void isIn(String... fieldValues)  {

        var expression = in(fieldValues);

        if (builder == null) {
            throw new NullPointerException("Builder (Buffer) is null");
        }

        this.builder.append(expression);
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
