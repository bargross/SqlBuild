package util.guard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringGuardTest {

    @Test
    void testIsEmptyOrWhiteSpace() {
        boolean result = StringGuard.isEmptyOrWhiteSpace("value");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testIsForbiddenKeyword() {
        boolean result = StringGuard.isForbiddenKeyword("value");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testContainsExcept() {
        boolean result = StringGuard.containsExcept("value", "except");
        Assertions.assertEquals(true, result);
    }
}
