package query.expression;

import client.enums.SQLFunction;
import client.models.FieldDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.models.Tuple;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

class QueryFieldExpressionBuilderTest {

    @Mock
    List<FieldDefinition> columns;

    @InjectMocks
    QueryFieldExpressionBuilder queryFieldExpressionBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetColumn() {
        when(columns.add(any(FieldDefinition.class))).thenReturn(true);

        IQueryFieldExpressionBuilder result = queryFieldExpressionBuilder.setColumn("column", SQLFunction.MAX);
        Assertions.assertEquals(new QueryFieldExpressionBuilder(), result);
    }

    @Test
    void testSetColumn2() {
        when(columns.add(any(FieldDefinition.class))).thenReturn(true);

        IQueryFieldExpressionBuilder result = queryFieldExpressionBuilder.setColumn("column");
        Assertions.assertEquals(new QueryFieldExpressionBuilder(), result);
    }

    @Test
    void testSetColumns() {
        when(columns.addAll(any(Collection.class))).thenReturn(true);

        IQueryFieldExpressionBuilder result = queryFieldExpressionBuilder.setColumns(new FieldDefinition[]{new FieldDefinition("field", SQLFunction.MAX)});
        Assertions.assertEquals(new QueryFieldExpressionBuilder(), result);
    }

    @Test
    void testSetColumns2() {
        when(columns.addAll(any(Collection.class))).thenReturn(true);

        IQueryFieldExpressionBuilder result = queryFieldExpressionBuilder.setColumns(List.of(new FieldDefinition("field", SQLFunction.MAX)));
        verify(columns).forEach(any(Consumer.class));
        Assertions.assertEquals(new QueryFieldExpressionBuilder(), result);
    }

    @Test
    void testGetFields() {
        when(columns.isEmpty()).thenReturn(true);
        when(columns.stream()).thenReturn(null);

        Tuple<String, String>[] result = queryFieldExpressionBuilder.getFields();

        verify(columns).clear();

        Assertions.assertArrayEquals(new Tuple[]{new Tuple<String, String>("field1", "field2")}, result);
    }
}