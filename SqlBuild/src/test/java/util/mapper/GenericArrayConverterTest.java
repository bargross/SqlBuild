package util.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GenericArrayConverterTest {

    @Test
    void testFromGenericElementsToArray() {
        String[] result = GenericArrayConverter.fromGenericElementsToArray(new String());
        Assertions.assertArrayEquals(new String[]{new String()}, result);
    }
}