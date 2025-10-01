import java.util.*;

/**
 * Abstract base class for mathematical expressions.
 */
public abstract class Expression {
    /**
     * Returns string representation of the expression.
     */
    public abstract String print();

    /**
     * Computes derivative with respect to given variable.
     */
    public abstract Expression derivative(String var);

    /**
     * Evaluates expression with given variable values.
     *
     * @throws WrongAssignmentException if the assignment doesn't contain the correct variable name
     */
    public abstract int evaluate(Map<String, Integer> vars) throws WrongAssignmentException;

    /**
     * Simplifies the expression using algebraic rules.
     */
    public abstract Expression simplify();

    /**
     * Compares expressions for structural equality.
     */
    public abstract boolean equals(Object obj);

    /**
     * Evaluates expression with variable assignments from string.
     */
    public int eval(String assignments) {
        Map<String, Integer> vars = new HashMap<>();
        String[] parts = assignments.split(";");
        for (String part : parts) {
            String[] assignment = part.trim().split("=");
            if (assignment.length != 2) {
                continue;
            }
            String varName = assignment[0].trim();
            int value = Integer.parseInt(assignment[1].trim());
            vars.put(varName, value);
        }
        return evaluate(vars);
    }

    /**
     * Parses expression from string with parentheses.
     */
    public static Expression parse(String s) {
        return Parser.parse(s);
    }
}