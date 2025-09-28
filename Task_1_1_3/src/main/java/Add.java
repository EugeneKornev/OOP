import java.util.Map;

/**
 * Represents addition operation.
 */
public class Add extends BinaryOperation {
    /**
     * Creates a new addition operation.
     * @param left the left operand
     * @param right the right operand
     */
    public Add(Expression left, Expression right) {
        super(left, right, "+");
    }

    /** Returns derivative as sum of derivatives. */
    public Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var));
    }

    /** Evaluates by adding left and right expressions. */
    public int evaluate(Map<String, Integer> vars) {
        return left.evaluate(vars) + right.evaluate(vars);
    }

    /** Simplifies addition using algebraic rules. */
    public Expression simplify() {
        Expression simpleLeft = left.simplify();
        Expression simpleRight = right.simplify();

        // 0 + x = x
        if (simpleLeft instanceof Number && ((Number)simpleLeft).getValue() == 0)
            return simpleRight;
        // x + 0 = x
        if (simpleRight instanceof Number && ((Number)simpleRight).getValue() == 0)
            return simpleLeft;
        // x + x = 2*x
        if (simpleLeft.equals(simpleRight))
            return new Mul(new Number(2), simpleLeft).simplify();
        // Constant folding
        if (simpleLeft instanceof Number && simpleRight instanceof Number)
            return new Number(simpleLeft.evaluate(null) + simpleRight.evaluate(null));

        return new Add(simpleLeft, simpleRight);
    }
}