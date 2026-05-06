package query.parametizedQuery;

import exception.EmptyQueryException;
import util.guard.StringGuard;

public class ParameterizedQuery  implements IParameterizedQuery  {
    private final String _query;

    public ParameterizedQuery(StringBuffer queryBuilder) throws IllegalArgumentException, EmptyQueryException {
        if (queryBuilder == null) {
            throw new IllegalArgumentException("Builder is null");
        }

        var query = queryBuilder.toString();

        if (StringGuard.isEmptyOrWhiteSpace(query)) {
            throw new EmptyQueryException();
        }

        _query = query;
    }


    public String getSqlString() {
        return _query;
    }
}
