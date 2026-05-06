package procedure;

import procedure.queryextended.QueryComplexBuilderExtended;
import query.build.queryComplex.IQueryComplexBuilder;

import java.util.function.Consumer;

public interface IStoredProcedureBuilder {
    IStoredProcedureBuilder createProcedure(String name);
    IStoredProcedureBuilder go(Consumer<QueryComplexBuilderExtended> builder);
}
