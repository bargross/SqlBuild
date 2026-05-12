package query.where;

import query.build.SQLQueryExpression;
import query.build.simple.IQuerySimpleBuilder;
import util.format.StringFormatter;
import util.guard.ReservedKeywordGuard;
import util.format.SQLStringFormatter;
import util.guard.SQLStringGuard;
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

        if (validateField && ReservedKeywordGuard.hasReservedKeywords(field)) {
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
     @param column the column within sql the table
     @throws IllegalArgumentException if the field is empty or if is a forbidden sql keyword
     */
    public void setColumn(String column) {
        this._expressionBuilder.setColumn(column);
        this._joinBuilder.setColumn(column);

        this.field = column;
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

    public IQuerySimpleBuilder orderBy(String column) {
        defaultValidation(column, "column");

        var columnNameWithEscapedQuotes = SQLStringFormatter.escapeQuotes(column);
        var columnWithQuotes = SQLStringFormatter.addQuotes(columnNameWithEscapedQuotes);

        this._rootBuilder.append(SQLQueryExpression.ORDERBY.getKeywordWithPostFix(columnWithQuotes));

        return _querySimpleBuilderRef;
    }

    public IQuerySimpleBuilder groupBy(String column) {
        defaultValidation(column, "column");

        var columnNameWithEscapedQuotes = SQLStringFormatter.escapeQuotes(column);
        var columnWithQuotes = SQLStringFormatter.addQuotes(columnNameWithEscapedQuotes);

        this._rootBuilder.append(SQLQueryExpression.GROUPBY.getKeywordWithPostFix(columnWithQuotes));

        return _querySimpleBuilderRef;
    }

    /// INTERNAL METHODS
    ///

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
        this._joinBuilder.setColumn(this.field);
    }

    public void setQueryBuilderRef(IQuerySimpleBuilder querySimpleBuilder) {
        this._querySimpleBuilderRef = querySimpleBuilder;
    }
    public void clearQueryBuilderRef() {
        this._querySimpleBuilderRef = null;
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

    private void defaultValidation(String value, String name) {
        if (StringGuard.isEmptyOrWhiteSpace(value)) {
            throw new IllegalArgumentException(String.format("%s name cannot be null, empty or white space.", StringFormatter.singleWordToCamelCase(name)));
        }

        if (!SQLStringGuard.isNameInValid(value)) {
            throw new IllegalArgumentException(String.format("%s name is not valid, only letters, numbers and characters such as _ are allowed.", StringFormatter.singleWordToCamelCase(name)));
        }

        if (SQLStringGuard.hasQuotes(value)) {
            throw new IllegalArgumentException(String.format("Quotes are not allowed in %s.", name));
        }

        if (ReservedKeywordGuard.hasReservedKeywords(value)) {
            throw new IllegalArgumentException(String.format("%s name contains forbidden SQL keyword/s.", StringFormatter.singleWordToCamelCase(name)));
        }
    }
}
