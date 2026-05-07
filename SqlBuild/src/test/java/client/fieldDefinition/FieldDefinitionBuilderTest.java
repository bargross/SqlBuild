package client.fieldDefinition;

import client.enums.SQLFunction;
import client.models.FieldDefinition;
import exception.EmptyQueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import subquery.SubQuerySimpleBuilder;

import java.util.List;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

class FieldDefinitionBuilderTest {

    @Mock
    List<FieldDefinition> fields;

    @InjectMocks
    FieldDefinitionBuilder fieldDefinitionBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetColumn() {
        when(fields.add(any(FieldDefinition.class))).thenReturn(true);

        IFieldDefinitionBuilder result = fieldDefinitionBuilder.setColumn("field");
        Assertions.assertEquals(new FieldDefinitionBuilder(), result);
    }

    @Test
    void testSetColumn2() {
        when(fields.add(any(FieldDefinition.class))).thenReturn(true);

        IFieldDefinitionBuilder result = fieldDefinitionBuilder.setColumn("field", SQLFunction.MAX);
        Assertions.assertEquals(new FieldDefinitionBuilder(), result);
    }

    @Test
    void testSetColumnAsQuery() throws EmptyQueryException {
        when(fields.add(any(FieldDefinition.class))).thenReturn(true);

        IFieldDefinitionBuilder result = fieldDefinitionBuilder.setColumnAsQuery(null, "asFieldName");
        Assertions.assertEquals(new FieldDefinitionBuilder(), result);
    }

    @Test
    void testToList() {
        List<FieldDefinition> result = fieldDefinitionBuilder.toList();
        Assertions.assertEquals(List.of(new FieldDefinition("field", SQLFunction.MAX)), result);
    }

    @Test
    void testToArray() {
        FieldDefinition[] result = fieldDefinitionBuilder.toArray();
        Assertions.assertArrayEquals(new FieldDefinition[]{new FieldDefinition("field", SQLFunction.MAX)}, result);
    }

    @Test
    void testClear() {
        fieldDefinitionBuilder.clear();
        verify(fields).clear();
    }
}