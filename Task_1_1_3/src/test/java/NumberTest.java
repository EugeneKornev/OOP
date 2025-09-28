import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NumberTest extends ExpressionTest {

    @Test
    void testNumberCreation() {
        Number num = new Number(42);
        assertEquals(42, num.getValue());
    }

    @Test
    void testPrint() {
        Number num = new Number(42);
        assertEquals("42", num.print());
    }

    @Test
    void testDerivative() {
        Number num = new Number(42);
        Expression derivative = num.derivative("x");
        assertInstanceOf(Number.class, derivative);
        assertEquals(0, ((Number) derivative).getValue());
        assertEquals("0", derivative.print());
    }

    @Test
    void testEvaluate() {
        Number num = new Number(42);
        Map<String, Integer> vars = createVariables();
        assertEquals(42, num.evaluate(vars));
        assertEquals(42, num.evaluate(new HashMap<>()));
    }

    @Test
    void testSimplify() {
        Number num = new Number(42);
        Expression simplified = num.simplify();
        assertInstanceOf(Number.class, simplified);
        assertEquals(42, ((Number) simplified).getValue());
        assertSame(num, simplified);
    }

    @Test
    void testEquals() {
        Number num1 = new Number(42);
        Number num2 = new Number(42);
        Number num3 = new Number(43);

        assertEquals(num1, num2);
        assertNotEquals(num1, num3);
        assertNotEquals(null, num1);
        assertNotEquals(new Variable("x"), num1);
    }

    @Test
    void testEvalMethod() {
        Number num = new Number(42);
        int result = num.eval("x = 5; y = 10");
        assertEquals(42, result);
    }
}