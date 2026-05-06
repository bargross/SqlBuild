package query.build.queryComplex;

import query.build.SQLQueryExpression;
import query.build.simpleQuery.QuerySimpleBuilder;
import query.expression.IQueryFieldExpressionBuilder;
import util.iterator.GenericIterator;
import util.mapper.Mapper;
import java.util.function.Consumer;

public class QueryComplexBuilder extends QuerySimpleBuilder implements IQueryComplexBuilder {

    public QueryComplexBuilder() {
        super();
    }

    // TODO: same as QuerySimpleBuilder constructor with buffer
    // public QueryExtendedBuilder(StringBuffer builder) {
    //    super(builder);
    // }

    /**
     *
     @value String table name
     @return QuerySimpleBuilder
     @throws IllegalCallerException description...
     @throws IllegalArgumentException description...
     */
    public IQueryComplexBuilder selectDistinct(Consumer<IQueryFieldExpressionBuilder> fieldBuilderPredicate) throws IllegalCallerException, IllegalArgumentException {

        if(primarySQLFunctionDeclared) {
            throw new IllegalCallerException("Primary SQL function declaration not permitted more than once");
        }

        fieldBuilderPredicate.accept(this.fieldBuilder);

        var fields = Mapper.map(this.fieldBuilder.getFields(), field -> field.itemTwoIsPresent() ? field.getItemOne() : field.getItemTwo());

        GenericIterator.each(fields, field -> {
            if(field == null) {
                throw new IllegalArgumentException("Invalid field in sequence");
            }
        });

        var columns = String.join(",", fields);
        var expression = String.format("%s %s", SQLQueryExpression.SELECTDISTINCT.getKeyword(), columns);

        queryBuilder.append(expression);

        primarySQLFunctionDeclared = true;

        return this;
    }

    /**
     *
     @value String table name
     @return QuerySimpleBuilder
     @throws IllegalCallerException description...
     */
    public IQueryComplexBuilder selectAllDistinct() throws IllegalCallerException {
        if(primarySQLFunctionDeclared) {
            throw new IllegalCallerException("Primary SQL function declaration not permitted more than once");
        }

        var expression = SQLQueryExpression.SELECTDISTINCT.getKeywordWithPostFix("*");

        queryBuilder.append(expression);

        primarySQLFunctionDeclared = true;

        return this;
    }

    /**
     *
     @value String table name
     @return QuerySimpleBuilder
     @throws IllegalCallerException description...
     @throws IllegalArgumentException description...
     */
    public IQueryComplexBuilder selectTop(Integer maxRows, Consumer<IQueryFieldExpressionBuilder> fieldBuilderPredicate) throws IllegalCallerException, IllegalArgumentException {

        if(primarySQLFunctionDeclared) {
            throw new IllegalCallerException("Primary SQL function declaration not permitted more than once");
        }

        if(maxRows < 1) {
            throw new IllegalArgumentException("max rows must be 1 or greater");
        }

        fieldBuilderPredicate.accept(this.fieldBuilder);

        var fields = Mapper.map(this.fieldBuilder.getFields(), field -> field.itemTwoIsPresent() ? field.getItemOne() : field.getItemTwo());

        GenericIterator.each(fields, field -> {
            if(field == null) {
                throw new IllegalArgumentException("Invalid field in sequence");
            }
        });

        var columns = String.join(",", fields);
        var expression = String.format("%s %s", SQLQueryExpression.SELECTTOP.getKeywordWithPostFix(maxRows.toString()), columns);

        queryBuilder.append(expression);

        primarySQLFunctionDeclared = true;

        return this;
    }

    /**
     *
     @value String table name
     @return QuerySimpleBuilder
     @throws IllegalCallerException description...
     */
    public IQueryComplexBuilder selectAllTop(Integer maxRows) throws IllegalCallerException {
        if (primarySQLFunctionDeclared) {
            throw new IllegalCallerException("Primary SQL function declaration not permitted more than once");
        }

        var expression = String.format("%s %s", SQLQueryExpression.SELECTTOP.getKeywordWithPostFix(maxRows.toString()), "*");

        this.queryBuilder.append(expression);

        primarySQLFunctionDeclared = true;

        return this;
    }
}
