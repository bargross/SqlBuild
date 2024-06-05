package query.wildcard;

import query.build.SQLQueryExpression;
import query.where.IWhereExpressionBuilder;
import query.where.WhereExpressionBuilder;
import util.guard.StringGuard;
import java.util.function.Consumer;

public class WildCardExpressionBuilder implements IWildCardExpressionBuilder
{
    private final WhereExpressionBuilder _whereExpressionBuilderRef;
    private final StringBuffer _builder;

    public WildCardExpressionBuilder(WhereExpressionBuilder whereExpressionBuilderRef) {
        _whereExpressionBuilderRef = whereExpressionBuilderRef;
        _builder = _whereExpressionBuilderRef.getRootBuilder();
    }

    public IWhereExpressionBuilder like(String fieldName, Consumer<WhereExpressionBuilder> action) {

        if (StringGuard.isEmptyOrWhiteSpace(fieldName)) {
            throw new IllegalArgumentException("Invalid field name");
        }

        _builder.append(String.format("%s %s", SQLQueryExpression.LIKE.getKeywordWithPostFix(System.lineSeparator()), fieldName));

        if (action != null) {
            action.accept(_whereExpressionBuilderRef);
        }

        return _whereExpressionBuilderRef;
    }

    public IWhereExpressionBuilder go() {
        _builder.append(SQLQueryExpression.GO);

        return _whereExpressionBuilderRef;
    }
}
