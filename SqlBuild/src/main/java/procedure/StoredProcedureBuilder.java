package procedure;

import procedure.queryextended.QueryComplexBuilderExtended;
import query.build.queryComplex.IQueryComplexBuilder;
import query.build.queryComplex.QueryComplexBuilder;
import util.guard.ReservedKeywordGuard;
import util.guard.storedProcedureReserved.RESERVED;

import java.util.function.Consumer;

public class StoredProcedureBuilder implements IStoredProcedureBuilder {

    private final StringBuffer builder;
    private final QueryComplexBuilderExtended queryBuilder;

    public StoredProcedureBuilder() {
        this.builder = new StringBuffer();
        this.queryBuilder = new QueryComplexBuilderExtended(this.builder);
    }

    public IStoredProcedureBuilder createProcedure(String name) {
        if (ReservedKeywordGuard.hasReservedKeywords(name)) {
            throw new IllegalArgumentException("Invalid keyword as name");
        }

        var procedure = RESERVED.CREATE.getKeywordWithPrefixAndSuffix(RESERVED.PROCEDURE, name, RESERVED.AS);

        this.builder.append(procedure);

        return this;
    }

    public IStoredProcedureBuilder go(Consumer<QueryComplexBuilderExtended> action) {
        action.accept(queryBuilder);

        return this;
    }

    public StringBuffer getBuilder() {
        return this.builder;
    }
}
