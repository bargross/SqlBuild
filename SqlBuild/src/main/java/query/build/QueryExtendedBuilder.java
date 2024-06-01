package query.build;

import query.expression.IQueryFieldExpressionBuilder;
import util.iterator.GenericIterator;
import util.mapper.Mapper;
import java.util.function.Consumer;

public class QueryExtendedBuilder extends QuerySimpleBuilder implements IQueryExtendedBuilder {

    public QueryExtendedBuilder() {
        super();
    }

    public QueryExtendedBuilder(StringBuffer builder) {
        super(builder);
    }

    /**
     *
     @param String table name
     @return QuerySimpleBuilder
     */
    public IQuerySimpleBuilder selectDistinct(Consumer<IQueryFieldExpressionBuilder> fieldBuilderPredicate) {

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
     @param String table name
     @return QuerySimpleBuilder
     */
    public IQuerySimpleBuilder selectAllDistinct() {
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
     @param String table name
     @return QuerySimpleBuilder
     */
    public IQuerySimpleBuilder selectTop(Integer maxRows, Consumer<IQueryFieldExpressionBuilder> fieldBuilderPredicate) {

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
        var expression = String.format("%s %s", SQLQueryExpression.SELECTTOP.getKeywordWithPostFix(maxRows), columns);

        queryBuilder.append(expression);

        primarySQLFunctionDeclared = true;

        return this;
    }

    /**
     *
     @param String table name
     @return QuerySimpleBuilder
     */
    public IQuerySimpleBuilder selectAllTop(Integer maxRows) {
        if(primarySQLFunctionDeclared) {
            throw new IllegalCallerException("Primary SQL function declaration not permitted more than once");
        }

        var expression = String.format("%s %s", SQLQueryExpression.SELECTTOP.getKeywordWithPostFix(maxRows), "*");

        this.queryBuilder.append(expression);

        primarySQLFunctionDeclared = true;

        return this;
    }
}
