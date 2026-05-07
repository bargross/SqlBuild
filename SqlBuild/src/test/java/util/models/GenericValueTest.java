package util.models;

import exception.GenericCastException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class GenericValueTest {
    @Mock
    Object value;
    @InjectMocks
    GenericValue genericValue;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetValue() {
        genericValue.setValue(new String());
    }

    @Test
    void testGetAndCast() throws GenericCastException {
        String result = genericValue.getAndCast();
        Assertions.assertEquals(new String(), result);
    }

    @Test
    void testOf() {
        GenericValue result = GenericValue.of(new String());
        Assertions.assertEquals(new GenericValue(), result);
    }

    @Test
    void testEquals() {
        boolean result = genericValue.equals("o");
        Assertions.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme