import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for Div expression type.
 */
public class DivTest extends ExpressionTest {

    @Test
    void testDivCreation() {
        Expression div = new Div(new Number(6), new Number(2));
        assertNotNull(div);
    }

    @Test
    void testPrint() {
        Expression div = new Div(new Number(6), new Number(2));
        assertEquals("(6/2)", div.print());

        Expression complex = new Div(new Variable("x"), new Div(new Number(1), new Number(2)));
        assertEquals("(x/(1/2))", complex.print());
    }

    @Test
    void testDerivative() {
        Expression div = new Div(new Variable("x"), new Number(2));
        Expression derivative = div.derivative("x");

        assertEquals("(((1*2)-(x*0))/(2*2))", derivative.print());
    }

    @Test
    void testEvaluate() {
        Expression div = new Div(new Variable("x"), new Number(2));
        Map<String, Integer> vars = createVariables();

        assertEquals(2, div.evaluate(vars));
    }

    @Test
    void testDivisionByZero() {
        Expression div = new Div(new Number(5), new Number(0));
        Map<String, Integer> vars = createVariables();

        assertThrows(ArithmeticException.class, () -> {
            div.evaluate(vars);
        });
    }

    @Test
    void testSimplify() {
        // 0 / x = 0
        Expression div1 = new Div(new Number(0), new Variable("x"));
        Expression simplified1 = div1.simplify();
        assertInstanceOf(Number.class, simplified1);
        assertEquals(0, ((Number) simplified1).getValue());

        // x / 1 = x
        Expression div2 = new Div(new Variable("x"), new Number(1));
        Expression simplified2 = div2.simplify();
        assertInstanceOf(Variable.class, simplified2);
        assertEquals("x", simplified2.print());

        // x / x = 1
        Expression div3 = new Div(new Variable("x"), new Variable("x"));
        Expression simplified3 = div3.simplify();
        assertInstanceOf(Number.class, simplified3);
        assertEquals(1, ((Number) simplified3).getValue());

        // Constant folding
        Expression div4 = new Div(new Number(6), new Number(2));
        Expression simplified4 = div4.simplify();
        assertInstanceOf(Number.class, simplified4);
        assertEquals(3, ((Number) simplified4).getValue());
    }

    @Test
    void testEquals() {
        Expression div1 = new Div(new Number(6), new Number(2));
        Expression div2 = new Div(new Number(6), new Number(2));
        Expression div3 = new Div(new Number(6), new Number(3));

        assertEquals(div1, div2);
        assertNotEquals(div1, div3);
        assertNotEquals(new Mul(new Number(6), new Number(2)), div1);
    }

    @Test
    void testComplexDivision() {
        Expression div = new Div(
                new Div(new Variable("x"), new Variable("y")),
                new Variable("z")
        );

        assertEquals("((x/y)/z)", div.print());
        assertEquals(0, div.eval("x=5; y=3; z=10"));
        assertEquals(0, div.evaluate(createVariables()));
    }

    @Test
    void testDivisionPrecedenceInParsing() {
        Expression div = Parser.parseWithoutParentheses("x / y / z");
        assertEquals("((x/y)/z)", div.print());
    }
}