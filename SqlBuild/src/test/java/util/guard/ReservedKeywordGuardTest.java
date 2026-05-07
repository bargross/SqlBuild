package util.guard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ReservedKeywordGuardTest {

    @Test
    void testHasReservedKeywords() {
        boolean result = ReservedKeywordGuard.hasReservedKeywords("fieldValues");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testHasReservedKeywordsExcept() {
        boolean result = ReservedKeywordGuard.hasReservedKeywordsExcept(new String[]{"except"}, "fieldValues");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testGetInvalidKeywords() {
        ArrayList<String> result = ReservedKeywordGuard.getInvalidKeywords("fieldNames");
        Assertions.assertEquals(new ArrayList<>(List.of("replaceMeWithExpectedResult")), result);
    }
}