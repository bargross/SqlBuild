package subquery;

import exception.EmptyQueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import query.build.simple.IQuerySimpleBuilder;
import query.expression.QueryFieldExpressionBuilder;
import query.join.IJoinExpressionBuilder;
import query.join.JoinExpressionBuilder;
import query.parametized.IParameterizedQuery;
import query.parametized.ParameterizedQuery;
import query.where.IWhereExpressionBuilder;
import query.where.WhereExpressionBuilder;
import util.models.Tuple;

import static org.mockito.Mockito.*;

class SubQuerySimpleBuilderTest {

    @Mock
    WhereExpressionBuilder whereBuilder;

    @Mock
    JoinExpressionBuilder joinBuilder;

    @Mock
    QueryFieldExpressionBuilder fieldBuilder;

    @InjectMocks
    SubQuerySimpleBuilder subQuerySimpleBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAs() throws EmptyQueryException {
        ParameterizedQuery result = subQuerySimpleBuilder.as("fieldName");
        Assertions.assertEquals(new ParameterizedQuery(new StringBuffer(0)), result);
    }

    @Test
    void testSelect() {
        when(fieldBuilder.getFields()).thenReturn(new Tuple[]{new Tuple<String, String>(null, null)});

        IQuerySimpleBuilder result = subQuerySimpleBuilder.select(null);
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }

    @Test
    void testSelectAll() {
        IQuerySimpleBuilder result = subQuerySimpleBuilder.selectAll();
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }

    @Test
    void testFrom() {
        IQuerySimpleBuilder result = subQuerySimpleBuilder.from("tableName");
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }

    @Test
    void testWhere() {
        IWhereExpressionBuilder result = subQuerySimpleBuilder.where("field");
        verify(whereBuilder).setField(anyString());
        verify(whereBuilder).setJoinBuilder(any(JoinExpressionBuilder.class));
        verify(whereBuilder).setQueryBuilderRef(any(IQuerySimpleBuilder.class));
        Assertions.assertEquals(new WhereExpressionBuilder(new StringBuffer(0), "field"), result);
    }

    @Test
    void testJoin() {
        IJoinExpressionBuilder result = subQuerySimpleBuilder.join("table");
        verify(joinBuilder).setColumn(anyString());
        verify(joinBuilder).setQueryBuilderRef(any(IQuerySimpleBuilder.class));
        Assertions.assertEquals(new JoinExpressionBuilder(new StringBuffer(0), "field"), result);
    }

    @Test
    void testBuild() throws EmptyQueryException {
        IParameterizedQuery result = subQuerySimpleBuilder.build();
        verify(whereBuilder).clearQueryBuilderRef();
        verify(joinBuilder).clearQueryBuilderRef();
        Assertions.assertEquals(new ParameterizedQuery(new StringBuffer(0)), result);
    }
}