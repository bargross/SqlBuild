package query.join;

import client.enums.SQLJoin;
import query.build.simple.IQuerySimpleBuilder;
import util.guard.ReservedKeywordGuard;
import util.guard.StringGuard;
import java.util.function.Consumer;
import query.expression.*;

public class JoinExpressionBuilder implements IJoinExpressionBuilder {
    private final StringBuffer builder;
    private final QueryExpressionBuilder expressionBuilder;
    private IQuerySimpleBuilder querySimpleBuilder;

    public JoinExpressionBuilder(StringBuffer builder) {
        this(builder, null, null, false);
    }

    public JoinExpressionBuilder(QueryExpressionBuilder expressionBuilder) {
        this(null, null, expressionBuilder, false);
    }

    public JoinExpressionBuilder(StringBuffer builder, String column) {
        this(builder, column, null, true);
    }

    private JoinExpressionBuilder(StringBuffer builder, String column, QueryExpressionBuilder expBuilder, boolean validateField) {

        if(validateField && StringGuard.isEmptyOrWhiteSpace(column)) {
            throw new IllegalArgumentException();
        }

        if(validateField && ReservedKeywordGuard.hasReservedKeywords(column)) {
            throw new IllegalArgumentException();
        }

        if(expBuilder == null) {
            if(validateField) {
                this.expressionBuilder = new QueryExpressionBuilder(column, builder);
            } else {
                this.expressionBuilder = new QueryExpressionBuilder(builder);
            }
        } else {
            this.expressionBuilder = expBuilder;
        }

        this.builder = builder;
    }

    /**
     *
     @param field table name
     @throws IllegalArgumentException invalid field
     */
    public void setColumn(String field) {
        if(StringGuard.isEmptyOrWhiteSpace(field)) {
           throw new IllegalArgumentException("Invalid field");
        }

        expressionBuilder.setColumn(field);
    }

    public void setQueryBuilderRef(IQuerySimpleBuilder querySimpleBuilder) {
        this.querySimpleBuilder = querySimpleBuilder;
    }

    public void clearQueryBuilderRef() {
        this.querySimpleBuilder = null;
    }

    /**
     *
     @param builder sets builder (buffer)
     @throws NullPointerException builder not set
     */
    public void setBuilder(StringBuffer builder) {
        if (builder == null) {
            throw new NullPointerException("Builder is null");
        }

        this.expressionBuilder.setBuilder(builder);
    }

    /**
     *
     @param field table name
     @param type join type
     @return QuerySimpleBuilder
     @throws IllegalArgumentException forbidden keyword used.
     */
    public IQuerySimpleBuilder with(String field, SQLJoin type) {
        if (StringGuard.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException("Invalid field name");
        }

        if (ReservedKeywordGuard.hasReservedKeywords(field)) {
            throw new IllegalArgumentException();
        }

        var join = SQLJoin.getJoin(type);

        var expression = String.format("%s %s%s", join, field, System.lineSeparator());

        builder.append(expression);

        return querySimpleBuilder;
    }

    /**
     *
     @param field table name
     @param delegate delegate expression builder
     @return QuerySimpleBuilder
     @throws IllegalArgumentException forbidden keyword used.
     */
    public IQuerySimpleBuilder on(String field, Consumer<IQueryExpressionBuilder> delegate) {
        if(StringGuard.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException("Invalid field/table");
        }

        delegate.accept(expressionBuilder);

        return querySimpleBuilder;
    }



}
