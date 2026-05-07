package util.iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GenericIteratorTest {

    @Test
    void testEach() {
        GenericIterator.each(new String[]{new String()}, null);
    }

    @Test
    void testContains() {
        boolean result = GenericIterator.contains(new String[]{new String()}, null);
        Assertions.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme