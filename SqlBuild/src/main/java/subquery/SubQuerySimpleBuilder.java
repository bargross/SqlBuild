package subquery;

import exception.EmptyQueryException;
import query.build.simple.QuerySimpleBuilder;
import query.parametized.ParameterizedQuery;
import util.guard.StringGuard;

public class SubQuerySimpleBuilder extends QuerySimpleBuilder implements ISubQuerySimpleBuilder {
    private final String fieldQueryStart;


    public SubQuerySimpleBuilder() {
        super();

        fieldQueryStart = "( ";
    }

    public ParameterizedQuery as(String fieldName) throws EmptyQueryException {
        if (StringGuard.isEmptyOrWhiteSpace(fieldName)) {
            throw new IllegalArgumentException("fieldName cannot be empty, null or white space.");
        }

        if (StringGuard.isForbiddenKeyword(fieldName)) {
            throw new IllegalArgumentException(String.format("%s has reserved SQL keywords.", fieldName));
        }

        if (queryBuilder.compareTo(new StringBuffer(fieldQueryStart)) == 0) {
            throw new EmptyQueryException();
        }

        queryBuilder.append(String.format(" ) AS %s", fieldName));

        return new ParameterizedQuery(queryBuilder);
    }
}
