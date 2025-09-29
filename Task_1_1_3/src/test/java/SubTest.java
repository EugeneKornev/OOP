import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for Sub expression type.
 */
public class SubTest extends ExpressionTest {

    @Test
    void testSubCreation() {
        Expression sub = new Sub(new Number(5), new Number(3));
        assertNotNull(sub);
    }

    @Test
    void testPrint() {
        Expression sub = new Sub(new Number(5), new Number(3));
        assertEquals("(5-3)", sub.print());

        Expression complex = new Sub(new Variable("x"), new Sub(new Number(1), new Number(2)));
        assertEquals("(x-(1-2))", complex.print());
    }

    @Test
    void testDerivative() {
        Expression sub = new Sub(new Variable("x"), new Number(3));
        Expression derivative = sub.derivative("x");

        assertEquals("(1-0)", derivative.print());
    }

    @Test
    void testEvaluate() {
        Expression sub = new Sub(new Variable("x"), new Number(3));
        Map<String, Integer> vars = createVariables();

        assertEquals(2, sub.evaluate(vars));
    }

    @Test
    void testSimplify() {
        // x - 0 = x
        Expression sub1 = new Sub(new Variable("x"), new Number(0));
        Expression simplified1 = sub1.simplify();
        assertInstanceOf(Variable.class, simplified1);
        assertEquals("x", simplified1.print());

        // x - x = 0
        Expression sub2 = new Sub(new Variable("x"), new Variable("x"));
        Expression simplified2 = sub2.simplify();
        assertInstanceOf(Number.class, simplified2);
        assertEquals(0, ((Number) simplified2).getValue());

        // Constant folding
        Expression sub3 = new Sub(new Number(5), new Number(3));
        Expression simplified3 = sub3.simplify();
        assertInstanceOf(Number.class, simplified3);
        assertEquals(2, ((Number) simplified3).getValue());

        // (x + 5) - (x + 5) = 0
        Expression sub4 = new Sub(
                new Add(new Variable("x"), new Number(5)),
                new Add(new Variable("x"), new Number(5))
        );
        Expression simplified4 = sub4.simplify();
        assertInstanceOf(Number.class, simplified4);
        assertEquals(0, ((Number) simplified4).getValue());
    }

    @Test
    void testEquals() {
        Expression sub1 = new Sub(new Number(5), new Number(3));
        Expression sub2 = new Sub(new Number(5), new Number(3));
        Expression sub3 = new Sub(new Number(5), new Number(4));

        assertEquals(sub1, sub2);
        assertNotEquals(sub1, sub3);
        assertNotEquals(new Add(new Number(5), new Number(3)), sub1);
    }

    @Test
    void testComplexSubtraction() {
        Expression sub = new Sub(
                new Sub(new Variable("x"), new Variable("y")),
                new Variable("z")
        );

        assertEquals("((x-y)-z)", sub.print());
        assertEquals(-8, sub.eval("x=5; y=3; z=10"));
        assertEquals(-8, sub.evaluate(createVariables()));
    }
}