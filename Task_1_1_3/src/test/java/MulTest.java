import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for Mul expression type.
 */
public class MulTest extends ExpressionTest {

    @Test
    void testMulCreation() {
        Expression mul = new Mul(new Number(2), new Number(3));
        assertNotNull(mul);
    }

    @Test
    void testPrint() {
        Expression mul = new Mul(new Number(2), new Number(3));
        assertEquals("(2*3)", mul.print());

        Expression complex = new Mul(new Variable("y"), new Mul(new Number(7), new Number(6)));
        assertEquals("(y*(7*6))", complex.print());
    }

    @Test
    void testDerivative() {
        // (x*2)' = (1*2 + x*0) = (2+0)
        Expression mul = new Mul(new Variable("x"), new Number(2));
        Expression derivative = mul.derivative("x");
        Expression expected = new Add(new Mul(new Number(1), new Number(2)), new Mul(new Variable("x"), new Number(0)));
        assertEquals(expected, derivative);
    }

    @Test
    void testEvaluate() throws WrongAssignmentException {
        Expression mul = new Mul(new Variable("x"), new Number(3));
        Map<String, Integer> vars = createVariables();
        assertEquals(15, mul.evaluate(vars));
    }

    @Test
    void testSimplify() throws WrongAssignmentException {
        // 0 * x = 0
        Expression mul1 = new Mul(new Number(0), new Variable("x"));
        Expression simplified1 = mul1.simplify();
        assertInstanceOf(Number.class, simplified1);
        Expression expected1 = new Number(0);
        assertEquals(expected1, simplified1);

        // 1 * x = x
        Expression mul2 = new Mul(new Number(1), new Variable("x"));
        Expression simplified2 = mul2.simplify();
        assertInstanceOf(Variable.class, simplified2);
        Expression expected2 = new Variable("x");
        assertEquals(expected2, simplified2);

        // x * 0 = 0
        Expression mul3 = new Mul(new Variable("x"), new Number(0));
        Expression simplified3 = mul3.simplify();
        assertInstanceOf(Number.class, simplified3);
        Expression expected3 = new Number(0);
        assertEquals(expected3, simplified3);

        // x * 1 = x
        Expression mul4 = new Mul(new Variable("x"), new Number(1));
        Expression simplified4 = mul4.simplify();
        assertInstanceOf(Variable.class, simplified4);
        Expression expected4 = new Variable("x");
        assertEquals(expected4, simplified4);

        // Constant folding
        Expression mul5 = new Mul(new Number(2), new Number(3));
        Expression simplified5 = mul5.simplify();
        assertInstanceOf(Number.class, simplified5);
        Expression expected5 = new Number(6);
        assertEquals(expected5, simplified5);
    }

    @Test
    void testEquals() {
        Expression add1 = new Mul(new Number(1), new Number(2));
        Expression add2 = new Mul(new Number(1), new Number(2));
        Expression add3 = new Mul(new Number(1), new Number(3));

        assertEquals(add1, add2);
        assertNotEquals(add1, add3);
    }

    @Test
    void testComplexMultiplication() throws WrongAssignmentException {
        Expression sub = new Mul(
                new Variable("x"), new Mul(new Variable("y"), new Variable("z"))
        );

        assertEquals("(x*(y*z))", sub.print());
        assertEquals(150, sub.eval("x=5; y=3; z=10"));
        assertEquals(150, sub.evaluate(createVariables()));
    }
}