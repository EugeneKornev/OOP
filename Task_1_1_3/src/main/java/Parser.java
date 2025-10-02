import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parser for mathematical expressions.
 */
public class Parser {
    /** Operator precedence table. */
    private static final Map<String, Integer> OPERATOR_PRECEDENCE = new HashMap<>();

    static {
        OPERATOR_PRECEDENCE.put("+", 1);
        OPERATOR_PRECEDENCE.put("-", 1);
        OPERATOR_PRECEDENCE.put("*", 2);
        OPERATOR_PRECEDENCE.put("/", 2);
    }

    /** Parses expression from string (with or without parentheses). */
    public static Expression parse(String s) {
        if (s == null) {
            throw new NullPointerException("Input string cannot be null");
        }

        List<String> tokens = tokenize(s);

        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }

        Expression result = parseExpression(tokens, 0);

        if (!tokens.isEmpty()) {
            throw new IllegalArgumentException("Unexpected tokens remaining: " + tokens);
        }

        return result;
    }

    /** Tokenizes the input string into recognizable tokens. */
    private static List<String> tokenize(String s) {
        List<String> tokens = new ArrayList<>();

        char[] chars = s.toCharArray();
        int i = 0;
        int n = chars.length;

        while (i < n) {
            char c = chars[i];

            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            if (Character.isDigit(c)) {
                StringBuilder number = new StringBuilder();
                while (i < n && Character.isDigit(chars[i])) {
                    number.append(chars[i]);
                    i++;
                }
                tokens.add(number.toString());
                continue;
            }

            if (Character.isLetter(c)) {
                StringBuilder variable = new StringBuilder();
                while (i < n && (Character.isLetterOrDigit(chars[i]) || chars[i] == '_')) {
                    variable.append(chars[i]);
                    i++;
                }
                tokens.add(variable.toString());
                continue;
            }

            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')') {
                tokens.add(String.valueOf(c));
                i++;
                continue;
            }

            throw new IllegalArgumentException("Unexpected character: '" + c + "'");
        }

        return tokens;
    }

    /**
     * Parses expression considering operator precedence and parentheses.
     */
    private static Expression parseExpression(List<String> tokens, int minPrecedence) {
        Expression left = parsePrimary(tokens);

        while (!tokens.isEmpty()) {
            String operator = tokens.get(0);

            // Check if it's a valid operator and has sufficient precedence
            if (!OPERATOR_PRECEDENCE.containsKey(operator) ||
                    OPERATOR_PRECEDENCE.get(operator) < minPrecedence) {
                break;
            }

            tokens.remove(0); // Remove operator

            if (tokens.isEmpty()) {
                throw new IllegalArgumentException("Expected expression after operator: " + operator);
            }

            Expression right = parseExpression(tokens, OPERATOR_PRECEDENCE.get(operator) + 1);

            switch (operator) {
                case "+":
                    left = new Add(left, right);
                    break;
                case "-":
                    left = new Sub(left, right);
                    break;
                case "*":
                    left = new Mul(left, right);
                    break;
                case "/":
                    left = new Div(left, right);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown operator: " + operator);
            }
        }

        return left;
    }

    /** Parses primary expressions (numbers, variables, parenthesized expressions). */
    private static Expression parsePrimary(List<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Unexpected end of expression");
        }

        String token = tokens.get(0);

        if (token.equals("(")) {
            tokens.remove(0); // Remove "("

            if (tokens.isEmpty()) {
                throw new IllegalArgumentException("Expected expression after '('");
            }

            Expression expr = parseExpression(tokens, 0);

            if (tokens.isEmpty() || !tokens.get(0).equals(")")) {
                throw new IllegalArgumentException("Expected ')' but got: " +
                        (tokens.isEmpty() ? "end of expression" : tokens.get(0)));
            }
            tokens.remove(0); // Remove ")"
            return expr;
        } else if (token.matches("\\d+")) {
            tokens.remove(0);
            return new Number(Integer.parseInt(token));
        } else if (token.matches("[a-zA-Z_][a-zA-Z_0-9]*")) {
            tokens.remove(0);
            return new Variable(token);
        } else {
            throw new IllegalArgumentException("Unexpected token: " + token);
        }
    }
}
