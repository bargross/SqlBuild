package procedure;

import query.build.IQueryExtendedBuilder;
import query.build.QueryExtendedBuilder;
import util.guard.ReservedKeywordGuard;
import util.guard.storedProcedureReserved.RESERVED;

import java.util.function.Consumer;

public class StoredProcedureBuilder implements IStoredProcedureBuilder {

    private final StringBuffer builder;
    private final IQueryExtendedBuilder queryBuilder;

    public StoredProcedureBuilder(StringBuffer builder, QueryExtendedBuilder queryBuilder) {
        this.builder = builder;
        this.queryBuilder = queryBuilder;
    }

    public StoredProcedureBuilder() {
        this.builder = new StringBuffer();
        this.queryBuilder = new QueryExtendedBuilder(this.builder);
    }

    public StoredProcedureBuilder(StringBuffer builder, IQueryExtendedBuilder queryBuilder) {
        this.builder = builder;
        this.queryBuilder = queryBuilder;
    }

    public IStoredProcedureBuilder createProcedure(String name) {
        if (ReservedKeywordGuard.hasReservedKeywords(name)) {
            throw new IllegalArgumentException("Invalid keyword as name");
        }

        var procedure = RESERVED.CREATE.getKeywordWithPrefixAndSuffix(RESERVED.PROCEDURE, name, RESERVED.AS);

        this.builder.append(procedure);

        return this;
    }

    public IStoredProcedureBuilder go(Consumer<IQueryExtendedBuilder> action) {
        action.accept(this.queryBuilder);

        return this;
    }

    public StringBuffer getBuilder() {
        return this.builder;
    }
}
