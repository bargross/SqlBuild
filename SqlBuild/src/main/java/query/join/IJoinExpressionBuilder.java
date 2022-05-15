package query.join;

import client.enums.SQLJoin;
import query.IQuerySimpleBuilder;
import query.expression.IQueryExpressionBuilder;

import java.util.function.Consumer;

public interface IJoinExpressionBuilder {
    IQuerySimpleBuilder with(String field, SQLJoin type);
    IQuerySimpleBuilder on(String field, Consumer<IQueryExpressionBuilder> delegate);
}
