package query.build;

import query.expression.IQueryFieldExpressionBuilder;
import query.expression.QueryFieldExpressionBuilder;
import util.guard.StringGuard;
import util.models.ITuple;
import guard.*;
import query.join.*;
import query.where.*;
import util.mapper.Mapper;
import util.models.Tuple;

import java.util.function.Consumer;

public class QuerySimpleBuilder implements IQuerySimpleBuilder {
    protected final StringBuffer queryBuilder;
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

    public QuerySimpleBuilder(StringBuffer queryBuilder) {
        this.queryBuilder = queryBuilder;
        whereBuilder = new WhereExpressionBuilder(queryBuilder);
        joinBuilder = new JoinExpressionBuilder(queryBuilder);
        fieldBuilder = new QueryFieldExpressionBuilder();

        primarySQLFunctionDeclared = false;
        tableDeclared = false;
    }

    /**
     * Sets the internal JoinExpressionBuilder
     @param QueryFieldExpressionBuilder the expression builder for table fields
     @return QuerySimpleBuilder
     @throws IllegalArgumentException thrown when no fields provided
     @throws IllegalCallerException thrown when primary function or table is declared
     */
    public IQuerySimpleBuilder select(Consumer<IQueryFieldExpressionBuilder> predicate) {
        if(primarySQLFunctionDeclared || tableDeclared) {
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
    public IQuerySimpleBuilder selectAll() {
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
     @param String table name
     @return QuerySimpleBuilder
     @throws IllegalArgumentException thrown when using forbidden words such as SELECT, FROM, etc...
     @throws IllegalCallerException thrown when primary function or table is declared
     */
    public IQuerySimpleBuilder from(String tableName) {
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
     @param String field/column
     @param Consumer<JExpressionBuilder>
     @return QuerySimpleBuilder
     @throws IllegalCallerException
     */
    public IWhereExpressionBuilder where(String field) {
        if(!primarySQLFunctionDeclared || !tableDeclared) {
            throw new IllegalCallerException("Columns or table not defined");
        }

        whereBuilder.setField(field);

        whereBuilder.setJoinBuilder(joinBuilder);

        whereBuilder.setQueryBuilderRef(this);

        return whereBuilder;
    }

    /**
     *
     @param Consumer<JExpressionBuilder>
     @return QuerySimpleBuilder
     @throws IllegalCallerException
     */
    public IJoinExpressionBuilder join(String table) {
        if(!primarySQLFunctionDeclared || !tableDeclared) {
            throw new IllegalCallerException("Columns or table not defined");
        }

        joinBuilder.setField(table);

        joinBuilder.setQueryBuilderRef(this);

        return joinBuilder;
    }

    /**
     * builds the query or expression
     @return String
     @throws NullPointerException if the internal builder is not set
     */
    @Override
    public String toString() {
        clearRefs();

        return queryBuilder.toString();
    }

    private void clearRefs() {
        joinBuilder.clearQueryBuilderRef();
        whereBuilder.clearQueryBuilderRef();
    }

    private String[] getFields(Tuple<String, String>[] fields) {
        return Mapper.filter(Mapper.map(fields, ITuple::getItemTwo),
                val -> val != null);
    }
}
