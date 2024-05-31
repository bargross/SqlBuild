package query.join;

import client.enums.SQLJoin;
import query.IQuerySimpleBuilder;
import validator.StringValidator;
import java.util.function.Consumer;
import query.expression.*;

public class JoinExpressionBuilder implements IJoinExpressionBuilder {
    private final StringBuffer builder;
    private QueryExpressionBuilder expressionBuilder;
    private IQuerySimpleBuilder querySimpleBuilder;

    public JoinExpressionBuilder(StringBuffer builder) {
        this(builder, null, null, false);
    }

    public JoinExpressionBuilder(QueryExpressionBuilder expressionBuilder) {
        this(null, null, expressionBuilder, false);
    }

    public JoinExpressionBuilder(StringBuffer builder, String field) {
        this(builder, field, null, true);
    }

    private JoinExpressionBuilder(StringBuffer builder, String field, QueryExpressionBuilder expBuilder, boolean validateField) {

        if(validateField && StringValidator.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException();
        }

        if(validateField && StringValidator.isForbiddenKeyword(field)) {
            throw new IllegalArgumentException();
        }

        if(expBuilder == null) {
            if(validateField) {
                this.expressionBuilder = new QueryExpressionBuilder(field, builder);
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
     @param String table name
     @return QuerySimpleBuilder
     */
    public void setField(String field) {
        if(StringValidator.isEmptyOrWhiteSpace(field)) {
           throw new IllegalArgumentException("Invalid field");
        }

        expressionBuilder.setField(field);
    }

    public void setQueryBuilderRef(IQuerySimpleBuilder querySimpleBuilder) {
        this.querySimpleBuilder = querySimpleBuilder;
    }
    public void clearQueryBuilderRef() {
        this.querySimpleBuilder = null;
    }

    /**
     *
     @param String table name
     @return QuerySimpleBuilder
     */
    public void setBuilder(StringBuffer builder) {
        if(builder == null) {
            throw new NullPointerException(builder.getClass().getName());
        }

        this.expressionBuilder.setBuilder(builder);
    }

    /**
     *
     @param String table name
     @return QuerySimpleBuilder
     */
    public IQuerySimpleBuilder with(String field, SQLJoin type) {
        if(StringValidator.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException("Invalid field name");
        }

        if(StringValidator.isForbiddenKeyword(field)) {
            throw new IllegalArgumentException();
        }

        var join = SQLJoin.getJoin(type);

        var expression = String.format("%s %s%s", join, field, System.lineSeparator());

        builder.append(expression);

        return querySimpleBuilder;
    }

    public IQuerySimpleBuilder on(String field, Consumer<IQueryExpressionBuilder> delegate) {
        if(StringValidator.isEmptyOrWhiteSpace(field)) {
            throw new IllegalArgumentException("Invalid field/table");
        }

        delegate.accept(expressionBuilder);

        return querySimpleBuilder;
    }
}
