package comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.comparator.GenericComparator;

import java.util.Comparator;

import static org.mockito.Mockito.*;

class GenericComparatorTest {
    @Mock
    Comparator<String> comparator;

    @InjectMocks
    GenericComparator genericComparator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testIsGreater() {
        when(comparator.compare(any(String.class), any(String.class))).thenReturn(0);

        boolean result = genericComparator.isGreater("value", "valueToCompareTo");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testIsEqual() {
        when(comparator.compare(any(String.class), any(String.class))).thenReturn(0);

        boolean result = genericComparator.isEqual("value", "valueToCompareTo");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testIsLessThan() {
        when(comparator.compare(any(String.class), any(String.class))).thenReturn(0);

        boolean result = genericComparator.isLessThan("value", "valueToCompareTo");
        Assertions.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme