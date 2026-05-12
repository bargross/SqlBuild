package query.expression;

import java.util.function.Consumer;
import java.util.function.Function;

public interface IQueryExpressionBuilder {
    IQueryExpressionBuilder equals(String fieldValue);
    ///IQueryExpressionBuilder isEquals(String fieldValue);
    IQueryExpressionBuilder like(String fieldValue);
    IQueryExpressionBuilder isLike(String fieldValue);
    IQueryExpressionBuilder in(String... fieldValues);
    IQueryExpressionBuilder isIn(String... fieldValues);
    IQueryExpressionBuilder or(Consumer<IQueryExpressionBuilder> queryExpansionDelegate);
    void setColumn(String column);
}
