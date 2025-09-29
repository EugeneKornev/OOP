import java.util.HashMap;
import java.util.Map;

/**
 * Base test class for expression tests providing common test data.
 */
public class ExpressionTest {

    protected Map<String, Integer> createVariables() {
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        vars.put("y", 3);
        vars.put("z", 10);
        return vars;
    }
}