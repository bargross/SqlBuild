package query.build;

import query.expression.IQueryFieldExpressionBuilder;
import query.join.IJoinExpressionBuilder;
import query.where.IWhereExpressionBuilder;
import java.util.function.Consumer;

public interface IQuerySimpleBuilder {
    IQuerySimpleBuilder select(Consumer<IQueryFieldExpressionBuilder> predicate);
    IQuerySimpleBuilder selectAll();
    IQuerySimpleBuilder from(String tableName);
    IWhereExpressionBuilder where(String field);
    IJoinExpressionBuilder join(String table);
    String toString();
}
