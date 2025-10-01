import java.util.Map;

/**
 * Represents subtraction operation.
 */
public class Sub extends BinaryOperation {
    /**
     * Creates a new subtraction operation.
     *
     * @param left the left operand
     * @param right the right operand
     */
    public Sub(Expression left, Expression right) {
        super(left, right, "-");
    }

    /** Returns derivative as difference of derivatives. */
    public Expression derivative(String var) {
        return new Sub(left.derivative(var), right.derivative(var));
    }

    /** Evaluates by subtracting right from left expression. */
    public int evaluate(Map<String, Integer> vars) {
        return left.evaluate(vars) - right.evaluate(vars);
    }

    /** Simplifies subtraction using algebraic rules. */
    public Expression simplify() {
        Expression simpleLeft = left.simplify();
        Expression simpleRight = right.simplify();

        // x - 0 = x
        if (simpleRight instanceof Number && ((Number) simpleRight).getValue() == 0) {
            return simpleLeft;
        }
        // x - x = 0
        if (simpleLeft.equals(simpleRight)) {
            return new Number(0);
        }
        // Constant folding
        if (simpleLeft instanceof Number && simpleRight instanceof Number) {
            return new Number(simpleLeft.evaluate(null) - simpleRight.evaluate(null));
        }

        return new Sub(simpleLeft, simpleRight);
    }
}