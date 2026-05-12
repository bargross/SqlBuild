package query.build.simpleQuery;

import exception.EmptyQueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import query.build.simple.IQuerySimpleBuilder;
import query.build.simple.QuerySimpleBuilder;
import query.expression.QueryFieldExpressionBuilder;
import query.join.IJoinExpressionBuilder;
import query.join.JoinExpressionBuilder;
import query.parametized.IParameterizedQuery;
import query.parametized.ParameterizedQuery;
import query.where.IWhereExpressionBuilder;
import query.where.WhereExpressionBuilder;
import subquery.SubQuerySimpleBuilder;
import util.models.Tuple;

import static org.mockito.Mockito.*;

class QuerySimpleBuilderTest {
    //Field queryBuilder of type StringBuffer - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    WhereExpressionBuilder whereBuilder;

    @Mock
    JoinExpressionBuilder joinBuilder;

    @Mock
    QueryFieldExpressionBuilder fieldBuilder;

    @InjectMocks
    QuerySimpleBuilder querySimpleBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSelect() {
        when(fieldBuilder.getFields()).thenReturn(new Tuple[]{new Tuple<String, String>(null, null)});

        IQuerySimpleBuilder result = querySimpleBuilder.select(null);
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }

    @Test
    void testSelectAll() {
        IQuerySimpleBuilder result = querySimpleBuilder.selectAll();
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }

    @Test
    void testFrom() {
        IQuerySimpleBuilder result = querySimpleBuilder.from("tableName");
        Assertions.assertEquals(new SubQuerySimpleBuilder(), result);
    }

    @Test
    void testWhere() {
        IWhereExpressionBuilder result = querySimpleBuilder.where("field");
        verify(whereBuilder).setField(anyString());
        verify(whereBuilder).setJoinBuilder(any(JoinExpressionBuilder.class));
        verify(whereBuilder).setQueryBuilderRef(any(IQuerySimpleBuilder.class));
        Assertions.assertEquals(new WhereExpressionBuilder(new StringBuffer(0), "field"), result);
    }

    @Test
    void testJoin() {
        IJoinExpressionBuilder result = querySimpleBuilder.join("table");
        verify(joinBuilder).setColumn(anyString());
        verify(joinBuilder).setQueryBuilderRef(any(IQuerySimpleBuilder.class));
        Assertions.assertEquals(new JoinExpressionBuilder(new StringBuffer(0), "field"), result);
    }

    @Test
    void testBuild() throws EmptyQueryException {
        IParameterizedQuery result = querySimpleBuilder.build();
        verify(whereBuilder).clearQueryBuilderRef();
        verify(joinBuilder).clearQueryBuilderRef();
        Assertions.assertEquals(new ParameterizedQuery(new StringBuffer(0)), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme