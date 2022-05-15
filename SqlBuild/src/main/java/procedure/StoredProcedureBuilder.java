package procedure;

import query.IQueryExtendedBuilder;
import query.QueryExtendedBuilder;
import validator.ReservedKeywordValidator;
import validator.storedProcedureReserved.RESERVED;

import java.util.function.Consumer;

public class StoredProcedureBuilder implements IStoredProcedureBuilder {

    private final StringBuffer builder;
    private final IQueryExtendedBuilder queryBuilder;

    public StoredProcedureBuilder(StringBuffer builder, QueryExtendedBuilder queryBuilder) {
        this.builder = builder;
        this.queryBuilder = queryBuilder;

        this.queryBuilder.
    }

    public StoredProcedureBuilder() {
        this.builder = new StringBuffer();
        this.queryBuilder = new QueryExtendedBuilder(this.builder);
    }

    public IStoredProcedureBuilder createProcedure(String name) {
        if (ReservedKeywordValidator.hasReservedKeywords(name)) {
            throw new IllegalArgumentException("Invalid keyword as name");
        }

        var procedure = RESERVED.CREATE.getKeywordWithPrefixAndSuffix(RESERVED.PROCEDURE, name, RESERVED.AS);

        this.builder.append(name);

        return this;
    }

    public IStoredProcedureBuilder go(Consumer<IQueryExtendedBuilder> action) {
        action.accept(this.queryBuilder);
    }

    public StringBuffer getBuilder() {
        return this.builder;
    }
}
