import org.example.Calculator;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTest {
    private Calculator evaluator;
    @Test
    public void testValidExpression() {
        assertTrue(Calculator.isValidExpression("7 + 4 * (2 - 5)"));
        assertTrue(Calculator.isValidExpression("(4 - 1) / 3 + 3"));
        assertTrue(Calculator.isValidExpression("8 - 3 * (10 - 5) / 4"));
    }

    @Test
    public void testInvalidExpression() {
        assertFalse(Calculator.isValidExpression("5 / 0")); // Division by zero
        assertFalse(Calculator.isValidExpression("4 - * 5")); // Invalid character
        assertFalse(Calculator.isValidExpression("(8 + 7")); // Unbalanced parentheses
    }

    @Test
    public void testEvaluateExpression() {
        Calculator calculator = new Calculator();

        assertEquals(11.0, calculator.evaluateExpression("7 + 4"), 0.001);
        assertEquals(25.0, calculator.evaluateExpression("5 * (3 + 2)"), 0.001);
        assertEquals(3.0, calculator.evaluateExpression("(10 - 4) / 2"), 0.001);
    }
}