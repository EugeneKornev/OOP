import java.util.Map;

/**
 * Represents a numeric constant.
 */
public class Number extends Expression {
    private final int value;

    /**
     * Creates a new number with the specified value.
     * @param value the numeric value
     */
    public Number(int value) {
        this.value = value;
    }

    /** Returns string representation of the number. */
    public String print() {
        return Integer.toString(value);
    }

    /** Returns zero. */
    public Expression derivative(String var) {
        return new Number(0);
    }

    /** Returns the constant value. */
    public int evaluate(Map<String, Integer> vars) {
        return value;
    }

    /** Returns itself. */
    public Expression simplify() {
        return this;
    }

    /** Compares two numbers for equality. */
    public boolean equals(Object obj) {
        return obj instanceof Number && ((Number)obj).value == value;
    }

    /** Returns hash code based on the numeric value. */
    public int hashCode() {
        return Integer.hashCode(value);
    }

    /**
     * Returns the numeric value of this constant.
     * @return the numeric value
     */
    public int getValue() {
        return value;
    }
}