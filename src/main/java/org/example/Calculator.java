package org.example;
import java.util.Stack;

/**
 * Класс Calculator представляет собой калькулятор, который может вычислять арифметические выражения.
 * Для выполнения вычислений использует два стека: один для значений и другой для операторов.
 */
public class Calculator {

    private Stack<Double> values;
    private Stack<Character> operators;

    /**
     * Конструктор создает новый объект с пустыми стеками значений и операторов.
     */
    public Calculator() {
        this.values = new Stack<>();
        this.operators = new Stack<>();
    }

    /**
     * Метод evaluateExpression вычисляет заданное математическое выражение и возвращает результат.
     * Поддерживает основные арифметические операции +, -, *, / и скобки.
     *
     * @param expression - это введенное пользователем математическое выражение для вычисления.
     * @return возвращает результат вычисления выражения.
     * @throws IllegalArgumentException - исключение, которое генерируется при передаче некорректных аргументов методу.
     * @throws IllegalStateException - возникает, если операция, которая пытается быть выполнена, недопустима в данном контексте.
     * @throws ArithmeticException - возникает при обнаружении деления на ноль.
     */
    public double evaluateExpression(String expression) {
        char[] tokens = expression.toCharArray();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ') {
                continue;
            }

            if (Character.isDigit(tokens[i])) {
                StringBuilder sb = new StringBuilder();
                while (i < tokens.length && (Character.isDigit(tokens[i]) || tokens[i] == '.')) {
                    sb.append(tokens[i++]);
                }
                values.push(Double.parseDouble(sb.toString()));
                i--;
            } else if (tokens[i] == '(') {
                operators.push(tokens[i]);
            } else if (tokens[i] == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            } else if (isOperator(tokens[i])) {
                while (!operators.isEmpty() && hasPrecedence(tokens[i], operators.peek())) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(tokens[i]);
            } else {
                throw new IllegalArgumentException("Invalid character: " + tokens[i]);
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    /**
     * Метод peek получает верхнее значение из стека значений без его удаления.
     *
     * @return возвращает верхнее значение из стека значений.
     * @throws IllegalStateException возникает в случае, если стек значений пуст.
     */
    public double peek() {
        if (values.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return values.peek();
    }

    /**
     * Метод push помещает значение в стек значений.
     *
     * @param value - это значение для помещения в стек значений.
     */
    public void push(double value) {
        values.push(value);
    }

    /**
     * Метод pop извлекает и возвращает верхнее значение из стека значений.
     *
     * @return возвращает верхнее значение из стека значений.
     * @throws IllegalStateException возникает, если стек значений пуст.
     */
    public double pop() {
        if (values.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return values.pop();
    }

    /** Метод isOperator - это вспомогательный метод для проверки того, является ли символ оператором
     *
     * @param token - проверяемый символ.
     * @return true, если символ является оператором, иначе false.
     */
    private boolean isOperator(char token) {
        return token == '+' || token == '-' || token == '*' || token == '/';
    }

    /**
     * Метод hasPsecedence проверяет приоритет двух операторов.
     *
     * @param op1 - первый оператор.
     * @param op2 - второй оператор.
     * @return true, если у op1 приоритет выше или равен приоритету op2, иначе false.
     */
    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return (op2 == '*' || op2 == '/') && (op1 == '+' || op1 == '-');
    }

    /**
     * Метод applyOperator применяет указанный оператор к двум операндам.
     *
     * @param operator -оператор для применения.
     * @param a - первый операнд.
     * @param b - второй операнд.
     * @return возврвщвет результат применения оператора к операндам.
     * @throws ArithmeticException вызывается при обнаружении деления на ноль.
     */
    private double applyOperator(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
        }
        return 0;
    }

    /**
     * Метод isValidExpression проверяет, является ли заданное выражение корректным, путем попытки его вычислить.
     *
     * @param expression - проверяемое выражение.
     * @return true, если выражение корректно, иначе false.
     */
    public static boolean isValidExpression(String expression) {
        try {
            // Attempt to evaluate the expression to check for validity
            new Calculator().evaluateExpression(expression);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
