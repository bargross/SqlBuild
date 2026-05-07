package query.expression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class QueryExpressionBuilderTest {
    QueryExpressionBuilder queryExpressionBuilder = new QueryExpressionBuilder("field", new StringBuffer(0));

    @Test
    void testSetColumn() {
        queryExpressionBuilder.setColumn("column");
    }

    @Test
    void testSetBuilder() {
        queryExpressionBuilder.setBuilder(new StringBuffer(0));
    }

    @Test
    void testEquals() {
        IQueryExpressionBuilder result = queryExpressionBuilder.equals("fieldValue");
        Assertions.assertEquals(new QueryExpressionBuilder("field", new StringBuffer(0)), result);
    }

    @Test
    void testIsEquals() {
        IQueryExpressionBuilder result = queryExpressionBuilder.isEquals("fieldValue");
        Assertions.assertEquals(new QueryExpressionBuilder("field", new StringBuffer(0)), result);
    }

    @Test
    void testLike() {
        IQueryExpressionBuilder result = queryExpressionBuilder.like("fieldValue");
        Assertions.assertEquals(new QueryExpressionBuilder("field", new StringBuffer(0)), result);
    }

    @Test
    void testIsLike() {
        IQueryExpressionBuilder result = queryExpressionBuilder.isLike("fieldValue");
        Assertions.assertEquals(new QueryExpressionBuilder("field", new StringBuffer(0)), result);
    }

    @Test
    void testIn() {
        IQueryExpressionBuilder result = queryExpressionBuilder.in("fieldValues");
        Assertions.assertEquals(new QueryExpressionBuilder("field", new StringBuffer(0)), result);
    }

    @Test
    void testIsIn() {
        IQueryExpressionBuilder result = queryExpressionBuilder.isIn("fieldValues");
        Assertions.assertEquals(new QueryExpressionBuilder("field", new StringBuffer(0)), result);
    }

    @Test
    void testOr() {
        IQueryExpressionBuilder result = queryExpressionBuilder.or(null);
        Assertions.assertEquals(new QueryExpressionBuilder("field", new StringBuffer(0)), result);
    }
}