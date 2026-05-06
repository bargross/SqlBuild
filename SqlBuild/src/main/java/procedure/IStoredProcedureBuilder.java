package procedure;

import query.build.queryComplex.IQueryExtendedBuilder;

import java.util.function.Consumer;

public interface IStoredProcedureBuilder {
    IStoredProcedureBuilder createProcedure(String name);
    IStoredProcedureBuilder go(Consumer<IQueryExtendedBuilder> builder);
}
