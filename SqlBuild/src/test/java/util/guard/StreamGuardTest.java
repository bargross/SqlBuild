package util.guard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

class StreamGuardTest {

    @Test
    void testIsConsumed() {
        Optional<Stream<String>> result = StreamGuard.isConsumed(null);
        Assertions.assertEquals(null, result);
    }
}