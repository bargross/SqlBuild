package query.where;

import query.IQuerySimpleBuilder;
import validator.StringValidator;
import query.expression.IQueryExpressionBuilder;
import query.expression.QueryExpressionBuilder;
import query.join.IJoinExpressionBuilder;
import query.join.JoinExpressionBuilder;

import java.util.function.Consumer;

public class WhereExpressionBuilder implements IWhereExpressionBuilder {
    private final QueryExpressionBuilder expressionBuilder;
    private JoinExpressionBuilder joinBuilder;
    private IQuerySimpleBuilder querySimpleBuilderRef;
    private String field;

    public WhereExpressionBuilder(StringBuffer rootBuilder) {
        this(rootBuilder, null, null, false);
    }

    public WhereExpressionBuilder(StringBuffer rootBuilder, String field) {
        this(rootBuilder, null, field, true);
    }

    public WhereExpressionBuilder(StringBuffer rootBuilder, JoinExpressionBuilder joinBuilder)  {
        this(rootBuilder, joinBuilder, null, false);
    }

    private WhereExpressionBuilder(StringBuffer rootBuilder, JoinExpressionBuilder joinBuilder, String field, boolean validateField) throws NullPointerException {

        if(rootBuilder == null) {
            throw new NullPointerException("Root is null");
        }

        if(validateField && StringValidator.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException("Invalid field name");
        }

        if(validateField && StringValidator.isForbiddenKeyword(field)) {
            throw new IllegalArgumentException("Field name has or is a reserved sql keyword");
        }

        if(validateField) {
            expressionBuilder = new QueryExpressionBuilder(field, rootBuilder);
        } else {
            expressionBuilder = new QueryExpressionBuilder(rootBuilder);
        }

        if(joinBuilder == null) {
            if(validateField) {
                this.joinBuilder = new JoinExpressionBuilder(rootBuilder, field);
            } else {
                this.joinBuilder = new JoinExpressionBuilder(rootBuilder);
            }
        } else {
            joinBuilder.setBuilder(rootBuilder);

            this.joinBuilder = joinBuilder;
        }

        this.field = field;
    }

    /**
     * Sets the field/property/column we're querying
     @param String the column within sql the table
     @return void
     @throws IllegalArgumentException if the field is empty or if is a forbidden sql keyword
     */
    public void setField(String field) {
        if(StringValidator.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException("Invalid field");
        }

        if(StringValidator.isForbiddenKeyword(field)) {
            throw new IllegalArgumentException("Forbidden field name");
        }

        this.expressionBuilder.setField(field);
        this.joinBuilder.setField(field);

        this.field = field;
    }

    /**
     * Sets the internal JoinExpressionBuilder
     @param JoinExpressionBuilder the join builder used internally
     @return void
     @throws NullPointerException if the parameter is null
     */
    public void setJoinBuilder(JoinExpressionBuilder joinBuilder) {
        if(joinBuilder == null) {
            throw new NullPointerException(joinBuilder.getClass().getName());
        }

        this.joinBuilder = joinBuilder;
        this.joinBuilder.setField(this.field);
    }

    public void setQueryBuilderRef(IQuerySimpleBuilder querySimpleBuilder) {
        this.querySimpleBuilderRef = querySimpleBuilder;
    }
    public void clearQueryBuilderRef() {
        this.querySimpleBuilderRef = null;
    }

    /**
     * Sets the internal JoinExpressionBuilder
     @param Consumer<JExpressionBuilder> delegate which passes the builder to generate custom expressions
     @return WExpressionBuilder
     */
    public IQuerySimpleBuilder join(Consumer<IJoinExpressionBuilder> delegate) {
        delegate.accept(joinBuilder);

        return querySimpleBuilderRef;
    }

    /**
     * Sets the internal JoinExpressionBuilder
     @param Consumer<QExpressionBuilder> delegate which passes the builder to generate custom expression
     @return WExpressionBuilder
     */
    public IQuerySimpleBuilder with(Consumer<IQueryExpressionBuilder> delegate) {
        delegate.accept(expressionBuilder);

        return querySimpleBuilderRef;
    }

    private IQuerySimpleBuilder getQueryBuilderRef() {
        if(querySimpleBuilderRef == null) {
            throw new NullPointerException(String.format("% ref is null", IQuerySimpleBuilder.class.getName()));
        }

        return querySimpleBuilderRef;
    }
}
