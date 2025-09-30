import java.util.Map;

/**
 * Represents a variable in expressions.
 */
public class Variable extends Expression {
    private final String name;


    /**
     * Creates a new variable with the specified name.
     *
     * @param name the variable name
     */
    public Variable(String name) {
        this.name = name;
    }

    /** Returns the variable name. */
    public String print() {
        return name;
    }

    /** Returns 1 if derivative by this variable, 0 otherwise. */
    public Expression derivative(String var) {
        return new Number(name.equals(var) ? 1 : 0);
    }


    /** Returns value of the variable from the provided map. */
    public int evaluate(Map<String, Integer> vars) throws WrongAssignmentException {
        if (!vars.containsKey(name)) {
            throw new WrongAssignmentException("Undefined variable: " + name);
        }
        return vars.get(name);
    }

    /** Returns itself. */
    public Expression simplify() {
        return this;
    }

    /** Compares two variables for equality by name. */
    public boolean equals(Object obj) {
        return obj instanceof Variable && ((Variable) obj).name.equals(name);
    }

    /** Returns hash code based on the variable name. */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Returns the name of this variable.
     *
     * @return the variable name
     */
    public String getName() {
        return name;
    }
}
