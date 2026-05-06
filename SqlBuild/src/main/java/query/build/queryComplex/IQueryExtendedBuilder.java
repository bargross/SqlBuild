package query.build.queryComplex;

import query.build.simpleQuery.IQuerySimpleBuilder;
import query.expression.IQueryFieldExpressionBuilder;

import java.util.function.Consumer;

public interface IQueryExtendedBuilder extends IQuerySimpleBuilder {
    IQueryExtendedBuilder selectDistinct(Consumer<IQueryFieldExpressionBuilder> fieldBuilderPredicate);
    IQueryExtendedBuilder selectAllDistinct();
    IQueryExtendedBuilder selectTop(Integer maxRows, Consumer<IQueryFieldExpressionBuilder> fieldBuilderPredicate);
    IQueryExtendedBuilder selectAllTop(Integer maxRows);
}
