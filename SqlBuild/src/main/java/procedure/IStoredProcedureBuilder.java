package procedure;

import query.IQueryExtendedBuilder;
import query.IQuerySimpleBuilder;
import query.expression.IQueryFieldExpressionBuilder;

import java.util.function.Consumer;

public interface IStoredProcedureBuilder {
    IStoredProcedureBuilder createProcedure(String name);
    IStoredProcedureBuilder go(Consumer<IQueryExtendedBuilder> builder);
}
