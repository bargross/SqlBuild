package query.build.complexQuery;

import exception.EmptyQueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import query.build.simpleQuery.IQuerySimpleBuilder;
import query.expression.QueryFieldExpressionBuilder;
import query.join.IJoinExpressionBuilder;
import query.join.JoinExpressionBuilder;
import query.parametizedQuery.IParameterizedQuery;
import query.parametizedQuery.ParameterizedQuery;
import query.where.IWhereExpressionBuilder;
import query.where.WhereExpressionBuilder;
import subquery.SubQuerySimpleBuilder;
import util.models.Tuple;

import static org.mockito.Mockito.*;

class QueryComplexBuilderTest {

    @Mock
    WhereExpressionBuilder whereBuilder;
    @Mock
    JoinExpressionBuilder joinBuilder;
    @Mock
    QueryFieldExpressionBuilder fieldBuilder;
    @InjectMocks
    QueryComplexBuilder queryComplexBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSelectDistinct() {
        when(fieldBuilder.getFields()).thenReturn(new Tuple[]{new Tuple<String, String>(null, null)});

        IQueryComplexBuilder result = queryComplexBuilder.selectDistinct(null);
        Assertions.assertEquals(new QueryComplexBuilder(), result);
    }

    @Test
    void testSelectAllDistinct() {
        IQueryComplexBuilder result = queryComplexBuilder.selectAllDistinct();
        Assertions.assertEquals(new QueryComplexBuilder(), result);
    }

    @Test
    void testSelectTop() {
        when(fieldBuilder.getFields()).thenReturn(new Tuple[]{new Tuple<String, String>(null, null)});

        IQueryComplexBuilder result = queryComplexBuilder.selectTop(Integer.valueOf(0), null);
        Assertions.assertEquals(new QueryComplexBuilder(), result);
    }

    @Test
    void testSelectAllTop() {
        IQueryComplexBuilder result = queryComplexBuilder.selectAllTop(Integer.valueOf(0));
        Assertions.assertEquals(new QueryComplexBuilder(), result);
    }

    @Test
    void testSelect() {
        when(fieldBuilder.getFields()).thenReturn(new Tuple[]{new Tuple<String, String>(null, null)});

        IQuerySimpleBuilder result = queryComplexBuilder.select(null);
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }

    @Test
    void testSelectAll() {
        IQuerySimpleBuilder result = queryComplexBuilder.selectAll();
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }

    @Test
    void testFrom() {
        IQuerySimpleBuilder result = queryComplexBuilder.from("tableName");
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }

    @Test
    void testWhere() {
        IWhereExpressionBuilder result = queryComplexBuilder.where("field");
        verify(whereBuilder).setField(anyString());
        verify(whereBuilder).setJoinBuilder(any(JoinExpressionBuilder.class));
        verify(whereBuilder).setQueryBuilderRef(any(IQuerySimpleBuilder.class));
        Assertions.assertEquals(new WhereExpressionBuilder(new StringBuffer(0), "field"), result);
    }

    @Test
    void testJoin() {
        IJoinExpressionBuilder result = queryComplexBuilder.join("table");
        verify(joinBuilder).setColumn(anyString());
        verify(joinBuilder).setQueryBuilderRef(any(IQuerySimpleBuilder.class));
        Assertions.assertEquals(new JoinExpressionBuilder(new StringBuffer(0), "field"), result);
    }

    @Test
    void testBuild() throws EmptyQueryException {
        IParameterizedQuery result = queryComplexBuilder.build();
        verify(whereBuilder).clearQueryBuilderRef();
        verify(joinBuilder).clearQueryBuilderRef();
        Assertions.assertEquals(new ParameterizedQuery(new StringBuffer(0)), result);
    }
}