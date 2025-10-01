import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for Parser functionality.
 * Tests parsing of expressions with and without parentheses.
 */
public class ParserTest {

    @Test
    void testParseNumber() {
        Expression expr = Parser.parse("42");
        assertInstanceOf(Number.class, expr);
        Expression expected = new Number(42);
        assertEquals(expected, expr);
    }

    @Test
    void testParseVariable() {
        Expression expr = Parser.parse("x");
        assertInstanceOf(Variable.class, expr);
        Expression expected = new Variable("x");
        assertEquals(expected, expr);
    }

    @Test
    void testParseAddition() {
        Expression expr = Parser.parse("(2+3)");
        assertInstanceOf(Add.class, expr);
        Expression expected = new Add(new Number(2), new Number(3));
        assertEquals(expected, expr);
    }

    @Test
    void testParseSubtraction() {
        Expression expr = Parser.parse("(5  - a)");
        assertInstanceOf(Sub.class, expr);
        Expression expected = new Sub(new Number(5), new Variable("a"));
        assertEquals(expected, expr);
    }

    @Test
    void testParseMultiplication() {
        Expression expr = Parser.parse("(17 * z)");
        assertInstanceOf(Mul.class, expr);
        Expression expected = new Mul(new Number(17), new Variable("z"));
        assertEquals(expected, expr);
    }

    @Test
    void testParseDivision() {
        Expression expr = Parser.parse("(138 /  e)");
        assertInstanceOf(Div.class, expr);
        Expression expected = new Div(new Number(138), new Variable("e"));
        assertEquals(expected, expr);
    }

    @Test
    void testParseInvalidOperator() {
        assertThrows(IllegalArgumentException.class, () -> {
            Parser.parse("(125 ^ c)");
        });
    }

    @Test
    void testParseComplexExpression() {
        Expression expr = Parser.parse("(3+(2*x))");
        Expression expected = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals(expected, expr);
    }

    @Test
    void testParseInvalidExpression() {
        assertThrows(IllegalArgumentException.class, () -> {
            Parser.parse("(2+3");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Parser.parse("(2 3)");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Parser.parse("(+ 2 3)");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Parser.parse("(");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Parser.parse("+");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Parser.parse("(2+");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Parser.parse("()");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Parser.parse("");
        });

        assertThrows(NullPointerException.class, () -> {
            Parser.parse(null);
        });
    }

    @Test
    void testParseExpressionWithoutParenthesesComplexPrecedence() {
        Expression expr1 = Parser.parse("a + b * c - d / e");
        Expression expected1 = new Sub(
                new Add(new Variable("a"),
                        new Mul(new Variable("b"), new Variable("c"))),
                new Div(new Variable("d"), new Variable("e")));
        assertEquals(expected1, expr1);

        Expression expr2 = Parser.parse("10 - 5 - 2");
        assertEquals("((10-5)-2)", expr2.print());
        assertEquals(3, expr2.eval(""));

        Expression expr3 = Parser.parse("12 / 3 * 2");
        assertEquals("((12/3)*2)", expr3.print());
        assertEquals(8, expr3.eval(""));
    }

    @Test
    void testParseMixedParentheses() {
        Expression expr1 = Parser.parse("(a + b) * c");
        Expression expected1 = new Mul(
                new Add(new Variable("a"), new Variable("b")),
                new Variable("c"));
        assertEquals(expected1, expr1);

        Expression expr2 = Parser.parse("a * (b + c)");
        Expression expected2 = new Mul(
                new Variable("a"),
                new Add(new Variable("b"), new Variable("c")));
        assertEquals(expected2, expr2);

        Expression expr3 = Parser.parse("(a + b) * (c - d)");
        Expression expected3 = new Mul(
                new Add(new Variable("a"), new Variable("b")),
                new Sub(new Variable("c"), new Variable("d")));
        assertEquals(expected3, expr3);
    }

    @Test
    void testParseOperatorPrecedence() {
        Expression expr1 = Parser.parse("a + b * c");
        Expression expected1 = new Add(
                new Variable("a"),
                new Mul(new Variable("b"), new Variable("c")));
        assertEquals(expected1, expr1);

        Expression expr2 = Parser.parse("a * b + c");
        Expression expected2 = new Add(
                new Mul(new Variable("a"), new Variable("b")),
                new Variable("c"));
        assertEquals(expected2, expr2);
    }

    @Test
    void testParseSimpleWithoutParentheses() {
        Expression expr1 = Parser.parse("2 + 3");
        Expression expected1 = new Add(new Number(2), new Number(3));
        assertEquals(expected1, expr1);

        Expression expr2 = Parser.parse("5 - a");
        Expression expected2 = new Sub(new Number(5), new Variable("a"));
        assertEquals(expected2, expr2);

        Expression expr3 = Parser.parse("17 * z");
        Expression expected3 = new Mul(new Number(17), new Variable("z"));
        assertEquals(expected3, expr3);

        Expression expr4 = Parser.parse("138 / e");
        Expression expected4 = new Div(new Number(138), new Variable("e"));
        assertEquals(expected4, expr4);
    }
}