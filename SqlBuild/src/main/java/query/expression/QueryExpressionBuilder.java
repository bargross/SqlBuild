package query.expression;

import validator.ReservedKeywordValidator;
import validator.StringValidator;

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
     @param String field/column
     @return void
     */
    public void setField(String field) {
        if(StringValidator.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException("Invalid field");
        }

        fieldName = field;
    }

    /**
     *
     @param StringBuffer builder
     @return void
     */
    public void setBuilder(StringBuffer builder) {
        if(builder == null) {
            throw new NullPointerException(builder.getClass().getName());
        }

        this.builder = builder;
    }

    /**
     *
     @param String fieldValue
     @return String
     @throws IllegalArgumentException
     */
    public String equals(String fieldValue) {
        defaultValidation(fieldValue);

        var expression = String.format("%s = %s%s", fieldName, fieldValue, System.lineSeparator());

        return expression;
    }

    /**
     *
     @param String fieldValue
     @return void
     @throws NullPointerException
     */
    public void isEquals(String fieldValue) {
        var expression = equals(fieldValue);

        if(builder != null) {
            throw new NullPointerException("Builder (Buffer) is null");
        }

        this.builder.append(expression);
    }

    /**
     *
     @param String fieldValue
     @return String
     @throws IllegalArgumentException
     */
    public String like(String fieldValue) {
        defaultValidation(fieldValue);

        var expression = String.format("%s LIKE %s%s", fieldName, fieldValue, System.lineSeparator());

        return expression;
    }

    /**
     *
     @param String fieldValue
     @return void
     @throws NullPointerException
     */
    public void isLike(String fieldValue) {
        var expression = like(fieldValue);

        if(builder != null) {
            throw new NullPointerException("Builder (Buffer) is null");
        }

        this.builder.append(expression);
    }

    /**
     *
     @param String[] fieldValues
     @return String
     */
    public String in(String... fieldValues) {
        if(ReservedKeywordValidator.hasReservedKeywords(fieldValues)) {
            throw new IllegalArgumentException("Forbidden word found in sequence");
        }

        var expression = String.format("%s IN (%s)%s", fieldName, String.join(",", fieldValues), System.lineSeparator());

        return expression;
    }

    /**
     *
     @param String[] fieldValues
     @return void
     */
    public void isIn(String... fieldValues)  {

        var expression = in(fieldValues);

        if(builder != null) {
            throw new NullPointerException("Builder (Buffer) is null");
        }

        this.builder.append(expression);
    }

    private void defaultValidation(String value) {
        if(StringValidator.isEmptyOrWhiteSpace(value)) {
            throw new IllegalArgumentException();
        }

        if(StringValidator.isForbiddenKeyword(value)) {
            throw new IllegalArgumentException();
        }
    }
}
