package query.wildcard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import query.where.IWhereExpressionBuilder;
import query.where.WhereExpressionBuilder;

import static org.mockito.Mockito.*;

class WildCardExpressionBuilderTest {

    @Mock
    WhereExpressionBuilder _whereExpressionBuilderRef;

    @InjectMocks
    WildCardExpressionBuilder wildCardExpressionBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLike() {
        IWhereExpressionBuilder result = wildCardExpressionBuilder.like("fieldName", null);
        Assertions.assertEquals(new WhereExpressionBuilder(new StringBuffer(0), "field"), result);
    }

    @Test
    void testGo() {
        IWhereExpressionBuilder result = wildCardExpressionBuilder.go();
        Assertions.assertEquals(new WhereExpressionBuilder(new StringBuffer(0), "field"), result);
    }
}