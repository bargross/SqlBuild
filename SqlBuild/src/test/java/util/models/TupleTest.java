package util.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class TupleTest {
    @Mock
    String itemOne;

    @Mock
    Integer itemTwo;

    @InjectMocks
    Tuple tuple;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOf() {
        Tuple<String, Integer> result = Tuple.of("value", 1);
        Assertions.assertEquals(new Tuple<String, Integer>("value", 1), result);
    }

    @Test
    void testItemOneIsPresent() {
        boolean result = tuple.itemOneIsPresent();
        Assertions.assertEquals(true, result);
    }

    @Test
    void testItemTwoIsPresent() {
        boolean result = tuple.itemTwoIsPresent();
        Assertions.assertEquals(true, result);
    }

    @Test
    void testGetNonNull() {
        GenericValue result = tuple.getNonNull();
        Assertions.assertEquals(new GenericValue(), result);
    }

    @Test
    void testEquals() {
        boolean result = tuple.equals("o");
        Assertions.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme