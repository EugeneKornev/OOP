import java.util.Map;

/**
 * Represents division operation.
 */
public class Div extends BinaryOperation {
    /**
     * Creates a new division operation.
     *
     * @param left the left operand
     * @param right the right operand
     */
    public Div(Expression left, Expression right) {
        super(left, right, "/");
    }

    /** Returns derivative using quotient rule. */
    public Expression derivative(String var) {
        return new Div(
                new Sub(
                        new Mul(left.derivative(var), right),
                        new Mul(left, right.derivative(var))
                ),
                new Mul(right, right)
        );
    }

    /** Evaluates by dividing left by right expression. */
    public int evaluate(Map<String, Integer> vars) throws WrongAssignmentException {
        int denominator = right.evaluate(vars);
        if (denominator == 0) throw new ArithmeticException("Division by zero");
        return left.evaluate(vars) / denominator;
    }

    /** Simplifies division using algebraic rules. */
    public Expression simplify() throws WrongAssignmentException {
        Expression simpleLeft = left.simplify();
        Expression simpleRight = right.simplify();

        // 0 / x = 0
        if (simpleLeft instanceof Number && ((Number) simpleLeft).getValue() == 0) {
            return new Number(0);
        }
        // x / 1 = x
        if (simpleRight instanceof Number && ((Number) simpleRight).getValue() == 1) {
            return simpleLeft;
        }
        // x / x = 1
        if (simpleLeft.equals(simpleRight)) {
            return new Number(1);
        }
        // Constant folding
        if (simpleLeft instanceof Number && simpleRight instanceof Number) {
            return new Number(simpleLeft.evaluate(null) / simpleRight.evaluate(null));
        }

        return new Div(simpleLeft, simpleRight);
    }
}