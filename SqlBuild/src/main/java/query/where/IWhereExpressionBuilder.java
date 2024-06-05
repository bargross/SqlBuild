package query.where;

import query.build.IQuerySimpleBuilder;
import query.expression.IQueryExpressionBuilder;
import query.join.IJoinExpressionBuilder;
import java.util.function.Consumer;

public interface IWhereExpressionBuilder {
    IQuerySimpleBuilder join(Consumer<IJoinExpressionBuilder> delegate);
    IQuerySimpleBuilder with(Consumer<IQueryExpressionBuilder> delegate);
    IQuerySimpleBuilder getQueryBuilderRef();

}
