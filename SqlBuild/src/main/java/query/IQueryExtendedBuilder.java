package query;

import query.expression.IQueryFieldExpressionBuilder;

import java.util.function.Consumer;

public interface IQueryExtendedBuilder extends IQuerySimpleBuilder {
    IQuerySimpleBuilder selectDistinct(Consumer<IQueryFieldExpressionBuilder> fieldBuilderPredicate);
    IQuerySimpleBuilder selectAllDistinct();
    IQuerySimpleBuilder selectTop(Integer maxRows, Consumer<IQueryFieldExpressionBuilder> fieldBuilderPredicate);
    IQuerySimpleBuilder selectAllTop(Integer maxRows);
}
