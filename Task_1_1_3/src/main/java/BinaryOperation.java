import java.util.Objects;

/**
 * Abstract base class for binary operations.
 */
public abstract class BinaryOperation extends Expression {
    protected Expression left;
    protected Expression right;
    protected String operator;


    /**
     * Creates a new binary operation with given left and right expressions.
     *
     * @param left the left operand
     * @param right the right operand
     * @param operator the operator symbol
     */
    public BinaryOperation(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    /** Returns string representation with parentheses. */
    public String print() {
        return "(" + left.print() + operator + right.print() + ")";
    }

    /** Compares binary operations for structural equality. */
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BinaryOperation other = (BinaryOperation) obj;
        return left.equals(other.left) && right.equals(other.right);
    }

    /** Returns hash code. */
    public int hashCode() {
        return Objects.hash(left, right, getClass());
    }
}