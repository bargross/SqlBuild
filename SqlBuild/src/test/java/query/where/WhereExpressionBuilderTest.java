package query.where;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import query.build.simple.IQuerySimpleBuilder;
import query.expression.QueryExpressionBuilder;
import query.join.JoinExpressionBuilder;
import subquery.SubQuerySimpleBuilder;

import static org.mockito.Mockito.*;

class WhereExpressionBuilderTest {
    @Mock
    QueryExpressionBuilder _expressionBuilder;
    @Mock
    JoinExpressionBuilder _joinBuilder;
    @Mock
    IQuerySimpleBuilder _querySimpleBuilderRef;

    @InjectMocks
    WhereExpressionBuilder whereExpressionBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetField() {
        whereExpressionBuilder.setField("column");
        verify(_expressionBuilder).setColumn(anyString());
        verify(_joinBuilder).setColumn(anyString());
    }

    @Test
    void testSetJoinBuilder() {
        whereExpressionBuilder.setJoinBuilder(new JoinExpressionBuilder(new StringBuffer(0), "field"));
        verify(_joinBuilder).setColumn(anyString());
    }

    @Test
    void testClearQueryBuilderRef() {
        whereExpressionBuilder.clearQueryBuilderRef();
    }

    @Test
    void testJoin() {
        IQuerySimpleBuilder result = whereExpressionBuilder.join(null);
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }

    @Test
    void testWith() {
        IQuerySimpleBuilder result = whereExpressionBuilder.with(null);
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }

    @Test
    void testGetQueryBuilderRef() {
        IQuerySimpleBuilder result = whereExpressionBuilder.getQueryBuilderRef();
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }
}