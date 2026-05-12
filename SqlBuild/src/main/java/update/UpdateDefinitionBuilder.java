package update;

import query.where.IWhereExpressionBuilder;
import query.where.WhereExpressionBuilder;
import update.enums.SQLUpdateExpression;
import update.set.ISetExpressionBuilder;
import update.set.SetExpressionBuilder;
import util.format.SQLStringFormatter;
import util.guard.ReservedKeywordGuard;
import util.guard.SQLStringGuard;
import util.guard.StringGuard;

import java.util.function.Consumer;

public class UpdateDefinitionBuilder implements IUpdateDefinitionBuilder {
    private StringBuffer _builderRef;

    public UpdateDefinitionBuilder() {
        _builderRef = new StringBuffer();
    }

    public IUpdateDefinitionBuilder update(String tableName) {
        defaultValidation(tableName);

        var formattedTableName = formatTableOrField(tableName);

        _builderRef.append(SQLUpdateExpression.UPDATE.getKeywordWithPostFix(formattedTableName));

        return this;
    }

    public IUpdateDefinitionBuilder set(Consumer<ISetExpressionBuilder> setPredicate) {
        if (setPredicate == null) {
            throw new IllegalArgumentException("SET function predicate is null");
        }

        setPredicate.accept(new SetExpressionBuilder(_builderRef));

        return this;
    }

    public IUpdateDefinitionBuilder where(Consumer<IWhereExpressionBuilder> wherePredicate) {
        if (wherePredicate == null) {
            throw new IllegalArgumentException("SET function predicate is null");
        }

        wherePredicate.accept(new WhereExpressionBuilder(_builderRef));

        return this;
    }

    private void defaultValidation(String value) {
        if (StringGuard.isEmptyOrWhiteSpace(value)) {
            throw new IllegalArgumentException("Invalid field");
        }

        if (SQLStringGuard.isNameInValid(value)) {
            throw new IllegalArgumentException("Invalid table name, only characters a-zA-Z 0-9 & _ are allowed.");
        }

        if (SQLStringGuard.hasQuotes(value)) {
            throw new IllegalArgumentException("Invalid table name, single quotes not allowed.");
        }

        if (ReservedKeywordGuard.hasReservedKeywords(value)) {
            throw new IllegalArgumentException();
        }
    }

    private String formatTableOrField(String value) {
        var nameWithEscapedQuotes = SQLStringFormatter.escapeQuotes(value);
        return SQLStringFormatter.addQuotes(nameWithEscapedQuotes);
    }
}
