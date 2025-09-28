import java.util.*;
import java.util.regex.*;

/**
 * Parser for mathematical expressions.
 */
public class Parser {
    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            "\\d+|\\w+|[+\\-*/()]"
    );

    /** Operator precedence table for expressions without parentheses. */
    private static final Map<String, Integer> OPERATOR_PRECEDENCE = new HashMap<>();
    static {
        OPERATOR_PRECEDENCE.put("+", 1);
        OPERATOR_PRECEDENCE.put("-", 1);
        OPERATOR_PRECEDENCE.put("*", 2);
        OPERATOR_PRECEDENCE.put("/", 2);
    }

    /** Parses expression from string with parentheses. */
    public static Expression parse(String s) {
        List<String> tokens = tokenize(s);
        Expression result = parseExpression(tokens);

        if (!tokens.isEmpty()) {
            throw new IllegalArgumentException("Unexpected tokens remaining: " + tokens);
        }

        return result;
    }

    /** Tokenizes the input string into recognizable tokens. */
    private static List<String> tokenize(String s) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERN.matcher(s.replaceAll("\\s+", "")); // Remove spaces

        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    /** Parses a single expression from tokens. */
    private static Expression parseExpression(List<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Unexpected end of expression");
        }

        String token = tokens.getFirst();

        if (token.equals("(")) {
            return parseParenthetical(tokens);
        } else if (token.matches("\\d+")) {
            tokens.removeFirst();
            return new Number(Integer.parseInt(token));
        } else if (token.matches("\\w+")) {
            tokens.removeFirst();
            return new Variable(token);
        } else {
            throw new IllegalArgumentException("Unexpected token: " + token);
        }
    }

    /** Parses expression enclosed in parentheses. */
    private static Expression parseParenthetical(List<String> tokens) {
        tokens.removeFirst(); // Remove "("

        Expression left = parseExpression(tokens);

        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected operator after left expression");
        }

        String operator = tokens.removeFirst();

        Expression right = parseExpression(tokens);

        if (tokens.isEmpty() || !tokens.getFirst().equals(")")) {
            throw new IllegalArgumentException("Expected ')' but got: " +
                    (tokens.isEmpty() ? "end of expression" : tokens.getFirst()));
        }
        tokens.removeFirst(); // Remove ")"

        switch (operator) {
            case "+": return new Add(left, right);
            case "-": return new Sub(left, right);
            case "*": return new Mul(left, right);
            case "/": return new Div(left, right);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    /** Parses expression from string without requiring parentheses. */
    public static Expression parseWithoutParentheses(String s) {
        List<String> tokens = tokenize(s);
        return parseExpressionWithoutParentheses(tokens, 0);
    }

    /**
     * Parses expression without parentheses considering operator precedence.
     */
    private static Expression parseExpressionWithoutParentheses(List<String> tokens, int minPrecedence) {
        Expression left = parsePrimary(tokens);

        while (!tokens.isEmpty()) {
            String operator = tokens.getFirst();

            if (!OPERATOR_PRECEDENCE.containsKey(operator) || OPERATOR_PRECEDENCE.get(operator) < minPrecedence) {
                break;
            }

            int currentPrecedence = OPERATOR_PRECEDENCE.get(operator);
            tokens.removeFirst(); // Remove operator

            Expression right = parseExpressionWithoutParentheses(tokens, currentPrecedence + 1);

            switch (operator) {
                case "+": left = new Add(left, right); break;
                case "-": left = new Sub(left, right); break;
                case "*": left = new Mul(left, right); break;
                case "/": left = new Div(left, right); break;
            }
        }

        return left;
    }

    /** Parses primary expressions (numbers, variables, parenthesized expressions). */
    private static Expression parsePrimary(List<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Unexpected end of expression");
        }

        String token = tokens.getFirst();

        if (token.equals("(")) {
            tokens.removeFirst(); // Remove "("
            Expression expr = parseExpressionWithoutParentheses(tokens, 0);
            if (tokens.isEmpty() || !tokens.getFirst().equals(")")) {
                throw new IllegalArgumentException("Expected ')'");
            }
            tokens.removeFirst(); // Remove ")"
            return expr;
        } else if (token.matches("\\d+")) {
            tokens.removeFirst();
            return new Number(Integer.parseInt(token));
        } else if (token.matches("\\w+")) {
            tokens.removeFirst();
            return new Variable(token);
        } else {
            throw new IllegalArgumentException("Unexpected token: " + token);
        }
    }
}