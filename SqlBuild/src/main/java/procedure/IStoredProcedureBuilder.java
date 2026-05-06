package procedure;

import procedure.queryextended.QueryComplexBuilderExtended;

import java.util.function.Consumer;

public interface IStoredProcedureBuilder {
    IStoredProcedureBuilder createProcedure(String name);
    IStoredProcedureBuilder go(Consumer<QueryComplexBuilderExtended> builder);
}
