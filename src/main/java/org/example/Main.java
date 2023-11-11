package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the expression:");
        String expression = scanner.nextLine();
        Calculator evaluator = new Calculator();
        if (Calculator.isValidExpression(expression)) {
            double result = evaluator.evaluateExpression(expression);
            System.out.println("Result: " + result);
        } else {
            System.out.println("Error: Invalid expression");
        }
    }
}