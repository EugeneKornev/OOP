import java.util.Map;

/**
 * Represents multiplication operation.
 */
public class Mul extends BinaryOperation {
    /**
     * Creates a new multiplication operation.
     * @param left the left operand
     * @param right the right operand
     */
    public Mul(Expression left, Expression right) {
        super(left, right, "*");
    }

    /** Returns derivative using product rule. */
    public Expression derivative(String var) {
        return new Add(
                new Mul(left.derivative(var), right),
                new Mul(left, right.derivative(var))
        );
    }

    /** Evaluates by multiplying left and right expressions. */
    public int evaluate(Map<String, Integer> vars) throws WrongAssignmentException {
        return left.evaluate(vars) * right.evaluate(vars);
    }

    /** Simplifies multiplication using algebraic rules. */
    public Expression simplify() throws WrongAssignmentException {
        Expression simpleLeft = left.simplify();
        Expression simpleRight = right.simplify();

        // 0 * x = 0
        if (simpleLeft instanceof Number && ((Number) simpleLeft).getValue() == 0) {
            return new Number(0);
        }
        // 1 * x = x
        if (simpleLeft instanceof Number && ((Number) simpleLeft).getValue() == 1) {
            return simpleRight;
        }
        // x * 0 = 0
        if (simpleRight instanceof Number && ((Number) simpleRight).getValue() == 0) {
            return new Number(0);
        }
        // x * 1 = x
        if (simpleRight instanceof Number && ((Number) simpleRight).getValue() == 1) {
            return simpleLeft;
        }
        // Constant folding
        if (simpleLeft instanceof Number && simpleRight instanceof Number) {
            return new Number(simpleLeft.evaluate(null) * simpleRight.evaluate(null));
        }

        return new Mul(simpleLeft, simpleRight);
    }
}