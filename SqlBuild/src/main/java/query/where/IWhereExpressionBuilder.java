package query.where;

import query.build.simple.IQuerySimpleBuilder;
import query.expression.IQueryExpressionBuilder;
import query.join.IJoinExpressionBuilder;
import java.util.function.Consumer;

public interface IWhereExpressionBuilder {
    IQuerySimpleBuilder join(Consumer<IJoinExpressionBuilder> delegate);
    IQuerySimpleBuilder with(Consumer<IQueryExpressionBuilder> delegate);
    IQuerySimpleBuilder orderBy(String column);
    IQuerySimpleBuilder groupBy(String column);
    IQuerySimpleBuilder getQueryBuilderRef();

}
