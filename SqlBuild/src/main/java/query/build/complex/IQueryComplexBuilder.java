package query.build.complex;

import query.build.simple.IQuerySimpleBuilder;
import query.expression.IQueryFieldExpressionBuilder;

import java.util.function.Consumer;

public interface IQueryComplexBuilder extends IQuerySimpleBuilder {
    IQueryComplexBuilder selectDistinct(Consumer<IQueryFieldExpressionBuilder> fieldBuilderPredicate);
    IQueryComplexBuilder selectAllDistinct();
    IQueryComplexBuilder selectTop(Integer maxRows, Consumer<IQueryFieldExpressionBuilder> fieldBuilderPredicate);
    IQueryComplexBuilder selectAllTop(Integer maxRows);
}
