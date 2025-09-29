import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for Add expression type.
 */
public class AddTest extends ExpressionTest {

    @Test
    void testAddCreation() {
        Expression add = new Add(new Number(2), new Number(3));
        assertNotNull(add);
    }

    @Test
    void testPrint() {
        Expression add = new Add(new Number(2), new Number(3));
        assertEquals("(2+3)", add.print());

        Expression complex = new Add(new Variable("x"), new Add(new Number(1), new Number(2)));
        assertEquals("(x+(1+2))", complex.print());
    }

    @Test
    void testDerivative() {
        Expression add = new Add(new Variable("x"), new Number(3));
        Expression derivative = add.derivative("x");

        assertEquals("(1+0)", derivative.print());
    }

    @Test
    void testEvaluate() {
        Expression add = new Add(new Variable("x"), new Number(3));
        Map<String, Integer> vars = createVariables();

        assertEquals(8, add.evaluate(vars));
    }

    @Test
    void testSimplify() {
        // x + 0 = x
        Expression add1 = new Add(new Variable("x"), new Number(0));
        Expression simplified1 = add1.simplify();
        assertInstanceOf(Variable.class, simplified1);
        assertEquals("x", simplified1.print());

        // 0 + x = x
        Expression add2 = new Add(new Number(0), new Variable("x"));
        Expression simplified2 = add2.simplify();
        assertInstanceOf(Variable.class, simplified2);
        assertEquals("x", simplified2.print());

        // x + x = 2*x
        Expression add3 = new Add(new Variable("x"), new Variable("x"));
        Expression simplified3 = add3.simplify();
        assertEquals("(2*x)", simplified3.print());

        // Constant folding
        Expression add4 = new Add(new Number(2), new Number(3));
        Expression simplified4 = add4.simplify();
        assertInstanceOf(Number.class, simplified4);
        assertEquals(5, ((Number) simplified4).getValue());
    }

    @Test
    void testEquals() {
        Expression add1 = new Add(new Number(1), new Number(2));
        Expression add2 = new Add(new Number(1), new Number(2));
        Expression add3 = new Add(new Number(1), new Number(3));

        assertEquals(add1, add2);
        assertNotEquals(add1, add3);
    }

    @Test
    void testComplexAddition() {
        Expression sub = new Add(
                new Add(new Variable("x"), new Variable("y")),
                new Variable("z")
        );

        assertEquals("((x+y)+z)", sub.print());
        assertEquals(18, sub.eval("x=5; y=3; z=10"));
        assertEquals(18, sub.evaluate(createVariables()));
    }
}