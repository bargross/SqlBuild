package subquery;

import exception.EmptyQueryException;
import query.build.simple.IQuerySimpleBuilder;
import query.parametized.ParameterizedQuery;

public interface ISubQuerySimpleBuilder extends IQuerySimpleBuilder {
    ParameterizedQuery as(String fieldName) throws EmptyQueryException;
}
