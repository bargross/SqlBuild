package query.join;

import client.enums.SQLJoin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import query.build.simpleQuery.IQuerySimpleBuilder;
import query.expression.QueryExpressionBuilder;
import subquery.SubQuerySimpleBuilder;

import static org.mockito.Mockito.*;

class JoinExpressionBuilderTest {

    @Mock
    QueryExpressionBuilder expressionBuilder;

    @Mock
    IQuerySimpleBuilder querySimpleBuilder;

    @InjectMocks
    JoinExpressionBuilder joinExpressionBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetColumn() {
        joinExpressionBuilder.setColumn("field");
        verify(expressionBuilder).setColumn(anyString());
    }

    @Test
    void testClearQueryBuilderRef() {
        joinExpressionBuilder.clearQueryBuilderRef();
    }

    @Test
    void testSetBuilder() {
        joinExpressionBuilder.setBuilder(new StringBuffer(0));
        verify(expressionBuilder).setBuilder(any(StringBuffer.class));
    }

    @Test
    void testWith() {
        IQuerySimpleBuilder result = joinExpressionBuilder.with("field", SQLJoin.JOIN);
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }

    @Test
    void testOn() {
        IQuerySimpleBuilder result = joinExpressionBuilder.on("field", null);
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }
}