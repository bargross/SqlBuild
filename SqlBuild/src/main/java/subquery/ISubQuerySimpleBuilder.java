package subquery;

import exception.EmptyQueryException;
import query.build.simpleQuery.IQuerySimpleBuilder;
import query.parametizedQuery.ParameterizedQuery;

public interface ISubQuerySimpleBuilder extends IQuerySimpleBuilder {
    ParameterizedQuery as(String fieldName) throws EmptyQueryException;
}
