/** Main demonstration class for the mathematical expression system. */
public class Main {
    /** Main entry point that demonstrates the complete expression system capabilities. */
    public static void main(String[] args) throws WrongAssignmentException {
        Expression e = new Add(
                new Number(3),
                new Mul(new Number(2), new Variable("x"))
        );

        System.out.println("Expression: " + e.print());

        Expression de = e.derivative("x");
        System.out.println("Derivative: " + de.print());

        int result = e.eval("x = 10");
        System.out.println("Result: " + result);

        Expression parsed = Expression.parse("(3 + (1 * (2 * (x + 0))))");
        System.out.println("Parsed: " + parsed.print());

        Expression simplified = e.simplify();
        System.out.println("Simplified: " + simplified.print());

        Expression ex = Parser.parseWithoutParentheses("x * y + 2 * z");
        result = ex.eval("x = 2; y = 3; z = 7");
        System.out.println("Result: " + result);
    }
}