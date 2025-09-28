import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    void testParseNumber() {
        Expression expr = Expression.parse("42");
        assertInstanceOf(Number.class, expr);
        assertEquals(42, ((Number) expr).getValue());
    }

    @Test
    void testParseVariable() {
        Expression expr = Expression.parse("x");
        assertInstanceOf(Variable.class, expr);
        assertEquals("x", ((Variable) expr).getName());
    }

    @Test
    void testParseAddition() {
        Expression expr = Expression.parse("(2+3)");
        assertInstanceOf(Add.class, expr);
        assertEquals("(2+3)", expr.print());
    }

    @Test
    void testParseSubtraction() {
        Expression expr = Expression.parse("(5  - a)");
        assertInstanceOf(Sub.class, expr);
        assertEquals("(5-a)", expr.print());
    }

    @Test
    void testParseMultiplication() {
        Expression expr = Expression.parse("(17 * z)");
        assertInstanceOf(Mul.class, expr);
        assertEquals("(17*z)", expr.print());
    }

    @Test
    void testParseDivision() {
        Expression expr = Expression.parse("(138 /  e)");
        assertInstanceOf(Div.class, expr);
        assertEquals("(138/e)", expr.print());
    }

    @Test
    void testParseInvalidOperator() {
        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parse("(125 ^ c)");
        });
    }

    @Test
    void testParseComplexExpression() {
        Expression expr = Expression.parse("(3+(2*x))");
        assertEquals("(3+(2*x))", expr.print());
    }

    @Test
    void testParseInvalidExpression() {
        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parse("(2+3");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parse("(2 3)");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parse("(+ 2 3)");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parse("(");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parse("+");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parse("(2+");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parse("()");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parse("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Parser.parseWithoutParentheses("");
        });

        assertThrows(NullPointerException.class, () -> {
            Expression.parse(null);
        });

        assertThrows(NullPointerException.class, () -> {
            Parser.parseWithoutParentheses(null);
        });
    }

    @Test
    void testParseExpressionWithoutParenthesesComplexPrecedence() {
        Expression expr1 = Parser.parseWithoutParentheses("a + b * c - d / e");
        assertEquals("((a+(b*c))-(d/e))", expr1.print());

        Expression expr2 = Parser.parseWithoutParentheses("10 - 5 - 2");
        assertEquals("((10-5)-2)", expr2.print());
        assertEquals(3, expr2.eval(""));

        Expression expr3 = Parser.parseWithoutParentheses("12 / 3 * 2");
        assertEquals("((12/3)*2)", expr3.print());
        assertEquals(8, expr3.eval(""));
    }
}