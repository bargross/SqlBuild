package client.fieldDefinition;

import client.column.ColumnDefinitionBuilder;
import client.column.IColumnDefinitionBuilder;
import client.enums.SQLFunction;
import client.models.FieldDefinition;
import exception.EmptyQueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class ColumnDefinitionBuilderTest {

    @Mock
    List<FieldDefinition> fields;

    @InjectMocks
    ColumnDefinitionBuilder columnDefinitionBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetColumn() {
        when(fields.add(any(FieldDefinition.class))).thenReturn(true);

        IColumnDefinitionBuilder result = columnDefinitionBuilder.setColumn("field");
        Assertions.assertEquals(new ColumnDefinitionBuilder(), result);
    }

    @Test
    void testSetColumn2() {
        when(fields.add(any(FieldDefinition.class))).thenReturn(true);

        IColumnDefinitionBuilder result = columnDefinitionBuilder.setColumn("field", SQLFunction.MAX);
        Assertions.assertEquals(new ColumnDefinitionBuilder(), result);
    }

    @Test
    void testSetColumnAsQuery() throws EmptyQueryException {
        when(fields.add(any(FieldDefinition.class))).thenReturn(true);

        IColumnDefinitionBuilder result = columnDefinitionBuilder.setColumnAsQuery(null, "asFieldName");
        Assertions.assertEquals(new ColumnDefinitionBuilder(), result);
    }

    @Test
    void testToList() {
        List<FieldDefinition> result = columnDefinitionBuilder.toList();
        Assertions.assertEquals(List.of(new FieldDefinition("field", SQLFunction.MAX)), result);
    }

    @Test
    void testToArray() {
        FieldDefinition[] result = columnDefinitionBuilder.toArray();
        Assertions.assertArrayEquals(new FieldDefinition[]{new FieldDefinition("field", SQLFunction.MAX)}, result);
    }

    @Test
    void testClear() {
        columnDefinitionBuilder.clear();
        verify(fields).clear();
    }
}