package util.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArrayGenericValueFinderTest {

    @Test
    void testFind() {
        int result = ArrayGenericValueFinder.find(new Integer[]{12, 2, 4,5}, 55);
        Assertions.assertEquals(0, result);
    }

    @Test
    void testContains() {
        boolean result = ArrayGenericValueFinder.contains(new Integer[]{12, 2, 4,5}, 5);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testFind2() {
        int result = ArrayGenericValueFinder.find(new Integer[]{12, 2, 4,5}, null);
        Assertions.assertEquals(0, result);
    }

    @Test
    void testSearch() {
        int result = ArrayGenericValueFinder.search(new Integer[]{12, 2, 4,5}, 0);
        Assertions.assertEquals(0, result);
    }
}