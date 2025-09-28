import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VariableTest extends ExpressionTest {

    @Test
    void testVariableCreation() {
        Variable var = new Variable("x");
        assertEquals("x", var.getName());
    }

    @Test
    void testPrint() {
        Variable var = new Variable("x");
        assertEquals("x", var.print());

        Variable multiChar = new Variable("varName");
        assertEquals("varName", multiChar.print());
    }

    @Test
    void testDerivative() {
        Variable varX = new Variable("x");
        Variable varY = new Variable("y");

        Expression derivX = varX.derivative("x");
        assertInstanceOf(Number.class, derivX);
        assertEquals(1, ((Number) derivX).getValue());

        Expression derivY = varX.derivative("y");
        assertInstanceOf(Number.class, derivY);
        assertEquals(0, ((Number) derivY).getValue());
    }

    @Test
    void testEvaluate() {
        Variable var = new Variable("x");
        Map<String, Integer> vars = createVariables();

        assertEquals(5, var.evaluate(vars));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            var.evaluate(new HashMap<>());
        });
        assertTrue(exception.getMessage().contains("Undefined variable"));
    }

    @Test
    void testSimplify() {
        Variable var = new Variable("x");
        Expression simplified = var.simplify();
        assertSame(var, simplified);
    }

    @Test
    void testEquals() {
        Variable var1 = new Variable("x");
        Variable var2 = new Variable("x");
        Variable var3 = new Variable("y");

        assertEquals(var1, var2);
        assertNotEquals(var1, var3);
        assertNotEquals(var1, new Number(1));
    }
}