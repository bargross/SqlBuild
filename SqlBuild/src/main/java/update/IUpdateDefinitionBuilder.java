package update;

import query.where.IWhereExpressionBuilder;
import update.set.ISetExpressionBuilder;

import java.util.function.Consumer;

public interface IUpdateDefinitionBuilder {
    IUpdateDefinitionBuilder update(String tableName);
    IUpdateDefinitionBuilder set(Consumer<ISetExpressionBuilder> setPredicate);
    IUpdateDefinitionBuilder where(Consumer<IWhereExpressionBuilder> wherePredicate);
}
