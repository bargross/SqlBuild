package query.build.simpleQuery;

import exception.EmptyQueryException;
import query.build.SQLQueryExpression;
import query.expression.IQueryFieldExpressionBuilder;
import query.expression.QueryFieldExpressionBuilder;
import query.parametizedQuery.IParameterizedQuery;
import query.parametizedQuery.ParameterizedQuery;
import util.guard.StringGuard;
import util.models.ITuple;
import query.join.*;
import query.where.*;
import util.mapper.Mapper;
import util.models.Tuple;

import java.util.Objects;
import java.util.function.Consumer;

public class QuerySimpleBuilder implements IQuerySimpleBuilder {
    protected StringBuffer queryBuilder;
    protected final WhereExpressionBuilder whereBuilder;
    protected final JoinExpressionBuilder joinBuilder;
    protected final QueryFieldExpressionBuilder fieldBuilder;

    protected boolean primarySQLFunctionDeclared;
    protected boolean tableDeclared;


    public QuerySimpleBuilder() {
        queryBuilder = new StringBuffer();
        whereBuilder = new WhereExpressionBuilder(queryBuilder);
        joinBuilder = new JoinExpressionBuilder(queryBuilder);
        fieldBuilder = new QueryFieldExpressionBuilder();

        primarySQLFunctionDeclared = false;
        tableDeclared = false;
    }

    // TODO: think about this constructors usage as it can mess with the query building process, might allow query to be build internally only.
//    public QuerySimpleBuilder(StringBuffer builder) {
//        queryBuilder = builder;
//        whereBuilder = new WhereExpressionBuilder(queryBuilder);
//        joinBuilder = new JoinExpressionBuilder(queryBuilder);
//        fieldBuilder = new QueryFieldExpressionBuilder();
//
//        primarySQLFunctionDeclared = false;
//        tableDeclared = false;
//    }

    /**
     * Sets the internal JoinExpressionBuilder
     @value QueryFieldExpressionBuilder the expression builder for table fields
     @return QuerySimpleBuilder
     @throws IllegalArgumentException thrown when no fields provided
     @throws IllegalCallerException thrown when primary function or table is declared
     */
    public IQuerySimpleBuilder select(Consumer<IQueryFieldExpressionBuilder> predicate) throws IllegalArgumentException, IllegalCallerException {
        if (primarySQLFunctionDeclared || tableDeclared) {
            throw new IllegalCallerException("Primary SQL function declaration not permitted more than once");
        }

        predicate.accept(fieldBuilder);

        var mappedFields = fieldBuilder.getFields();

        if(mappedFields.length == 0) {
            throw new IllegalArgumentException("No fields selected");
        }

        var columns = getFields(mappedFields);

        var fields = String.format(" %s%s", String.join(",", columns), System.lineSeparator());
        queryBuilder.append(SQLQueryExpression.SELECT.getKeywordWithPostFix(fields));

        primarySQLFunctionDeclared = true;

        return this;
    }

    /**
     *
     @return QuerySimpleBuilder
     @throws IllegalCallerException thrown when primary function or table is declared
     */
    public IQuerySimpleBuilder selectAll() throws IllegalCallerException {
        if(primarySQLFunctionDeclared || tableDeclared) {
            throw new IllegalCallerException("Primary SQL function declaration not permitted more than once");
        }

        var expression = SQLQueryExpression.SELECT.getKeywordWithPostFix("*");

        queryBuilder.append(expression);

        primarySQLFunctionDeclared = true;

        return this;
    }

    /**
     *
     @value String table name
     @return QuerySimpleBuilder
     @throws IllegalArgumentException thrown when using forbidden words such as SELECT, FROM, etc...
     @throws IllegalCallerException thrown when primary function or table is declared
     */
    public IQuerySimpleBuilder from(String tableName) throws IllegalArgumentException, IllegalCallerException {
        if(!primarySQLFunctionDeclared || tableDeclared) {
            throw new IllegalCallerException("Columns or table not defined");
        }

        if(StringGuard.isForbiddenKeyword(tableName)) {
            throw new IllegalArgumentException("Forbidden sql keyword found in sequence");
        }

        var table = String.format(" %s%s", tableName, System.lineSeparator());
        queryBuilder.append(SQLQueryExpression.FROM.getKeywordWithPostFix(table));

        tableDeclared = true;

        return this;
    }

    /**
     *
     @value String field/column
     @value Consumer<JExpressionBuilder>
     @return IWhereExpressionBuilder
     @throws  IllegalCallerException description...
     */
    public IWhereExpressionBuilder where(String field) throws IllegalCallerException {
        if (!primarySQLFunctionDeclared || !tableDeclared) {
            throw new IllegalCallerException("Columns or table not defined");
        }

        whereBuilder.setField(field);

        whereBuilder.setJoinBuilder(joinBuilder);

        whereBuilder.setQueryBuilderRef(this);

        return whereBuilder;
    }

    /**
     *
     @value Consumer<JExpressionBuilder>
     @return QuerySimpleBuilder
     @throws IllegalCallerException description...
     */
    public IJoinExpressionBuilder join(String table) throws IllegalCallerException {
        if(!primarySQLFunctionDeclared || !tableDeclared) {
            throw new IllegalCallerException("Columns or table not defined");
        }

        joinBuilder.setColumn(table);

        joinBuilder.setQueryBuilderRef(this);

        return joinBuilder;
    }

    /**
     * builds the query or expression
     @return String
     @throws NullPointerException if the internal builder is not set
     */
    public IParameterizedQuery build() throws EmptyQueryException {
        clearRefs();

        return new ParameterizedQuery(queryBuilder);
    }

    private void clearRefs() {
        joinBuilder.clearQueryBuilderRef();
        whereBuilder.clearQueryBuilderRef();
    }

    private String[] getFields(Tuple<String, String>[] fields) {
        return Mapper.filter(Mapper.map(fields, ITuple::getItemTwo), Objects::nonNull);
    }
}
