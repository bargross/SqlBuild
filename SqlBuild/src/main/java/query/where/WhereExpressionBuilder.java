package query.where;

import query.build.simpleQuery.IQuerySimpleBuilder;
import util.guard.StringGuard;
import query.expression.IQueryExpressionBuilder;
import query.expression.QueryExpressionBuilder;
import query.join.IJoinExpressionBuilder;
import query.join.JoinExpressionBuilder;

import java.util.function.Consumer;

public class WhereExpressionBuilder implements IWhereExpressionBuilder {
    private final QueryExpressionBuilder _expressionBuilder;
    private JoinExpressionBuilder _joinBuilder;
    private IQuerySimpleBuilder _querySimpleBuilderRef;
    private final StringBuffer _rootBuilder;
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

        if (rootBuilder == null) {
            throw new NullPointerException("Root is null");
        }

        if (validateField && StringGuard.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException("Invalid field name");
        }

        if (validateField && StringGuard.isForbiddenKeyword(field)) {
            throw new IllegalArgumentException("Field name has or is a reserved sql keyword");
        }

        _rootBuilder = rootBuilder;

        if (validateField) {
            _expressionBuilder = new QueryExpressionBuilder(field, rootBuilder);
        } else {
            _expressionBuilder = new QueryExpressionBuilder(rootBuilder);
        }

        if (joinBuilder == null) {
            if (validateField) {
                this._joinBuilder = new JoinExpressionBuilder(rootBuilder, field);
            } else {
                this._joinBuilder = new JoinExpressionBuilder(rootBuilder);
            }
        } else {
            joinBuilder.setBuilder(rootBuilder);

            this._joinBuilder = joinBuilder;
        }

        this.field = field;
    }

    /**
     * Sets the field/property/column we're querying
     @param field the column within sql the table
     @throws IllegalArgumentException if the field is empty or if is a forbidden sql keyword
     */
    public void setField(String field) {
        if (StringGuard.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException("Invalid field");
        }

        if (StringGuard.isForbiddenKeyword(field)) {
            throw new IllegalArgumentException("Forbidden field name");
        }

        this._expressionBuilder.setField(field);
        this._joinBuilder.setField(field);

        this.field = field;
    }

    /**
     * Sets the internal JoinExpressionBuilder
     @param joinBuilder the join builder used internally
     @throws NullPointerException if the parameter is null
     */
    public void setJoinBuilder(JoinExpressionBuilder joinBuilder) {
        if (joinBuilder == null) {
            throw new NullPointerException("Builder is null");
        }

        this._joinBuilder = joinBuilder;
        this._joinBuilder.setField(this.field);
    }

    public void setQueryBuilderRef(IQuerySimpleBuilder querySimpleBuilder) {
        this._querySimpleBuilderRef = querySimpleBuilder;
    }
    public void clearQueryBuilderRef() {
        this._querySimpleBuilderRef = null;
    }

    /**
     * Sets the internal JoinExpressionBuilder
     @param delegate delegate which passes the builder to generate custom expressions
     @return WExpressionBuilder
     */
    public IQuerySimpleBuilder join(Consumer<IJoinExpressionBuilder> delegate) {
        delegate.accept(_joinBuilder);

        return _querySimpleBuilderRef;
    }

    /**
     * Sets the internal JoinExpressionBuilder
     @param delegate delegate which passes the builder to generate custom expression
     @return WExpressionBuilder
     */
    public IQuerySimpleBuilder with(Consumer<IQueryExpressionBuilder> delegate) {
        delegate.accept(_expressionBuilder);

        return _querySimpleBuilderRef;
    }

    public IQuerySimpleBuilder getQueryBuilderRef() {
        if (_querySimpleBuilderRef == null) {
            throw new NullPointerException(String.format("%s ref is null", IQuerySimpleBuilder.class.getName()));
        }

        return _querySimpleBuilderRef;
    }

    public StringBuffer getRootBuilder() {
        return _rootBuilder;
    }
}
