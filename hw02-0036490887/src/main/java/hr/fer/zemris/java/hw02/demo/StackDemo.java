package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class {@code StackDemo} accepts postfix expression from the cmd arguments,
 * only one argument from the command line. If there is no problems in the
 * parsing, result will be printed at the standard output, otherwise at the
 * standard output will appear error message connected to the parsing error.
 * 
 * @author dario
 *
 */
public class StackDemo {

    /**
     * stack
     */
    private static ObjectStack stack;

    /**
     * It accepts only one arguments. That arguments is the math expression in the
     * postfix form. If the input can't be parsed, program will print error message,
     * otherwise expression will be evaluated and the result will be printed in the
     * Standard output.
     * 
     * @param args
     *            one parameter
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("You must give only one parameter.");
            System.exit(1);
        }
        stack = new ObjectStack();

        String[] expressions = args[0].split(" +");
        for (String expression : expressions) {
            if (expression.length() == 0)
                continue;
            System.out.println(expression  + " " + stack.size());
            if (isNumber(expression)) {
                stack.push(Integer.parseInt(expression));
            } else if (isOperator(expression)) {

                Object second = getFromStack();
                Object first = getFromStack();
                stack.push(performOperation(first, second, expression));
            } else {
                System.out.printf("String can't be parsed. Found something that doesn't belong in string.%n%s",
                        args[0]);
                System.exit(1);
            }
        }

        if (stack.size() != 1) {
            System.out.printf("String can't be parsed.%n%s", args[0]);
            System.exit(1);
        }

        System.out.printf("Expression evaluates to %d.%n", (Integer) stack.peek());
    }

    /**
     * Returns true if the expression can be parsed in integer, false otherwise.
     * 
     * @param expression
     *            which will be parsed in integer
     * @return true if it is number, false otherwise
     */
    private static boolean isNumber(String expression) {
        try {
            Integer.parseInt(expression);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Check if the given expression is valid operator. Valid operators are + , - ,
     * *, / and %.
     * 
     * @param expression
     *            expression
     * @return true if given expression is a operator
     */
    private static boolean isOperator(String expression) {
        if (expression.compareTo("+") == 0 || expression.compareTo("-") == 0 || expression.compareTo("*") == 0
                || expression.compareTo("/") == 0 || expression.compareTo("%") == 0)
            return true;
        return false;
    }

    /**
     * Return the result of the operator which will be performed on first and second
     * parameter of the function. If the first and second parameters can't be parsed
     * in integer it prints error message and exit the program. Also, you can't
     * divide by zero, that will also print the error and the program will exit.
     * 
     * @param first
     *            first parameter of the function
     * @param second
     *            second parameter of the function
     * @param operator
     *            valid string representation of the function
     * @return object
     */
    private static Object performOperation(Object first, Object second, String operator) {

        try {
            int firstNum = Integer.parseInt(first.toString());
            int secondNum = Integer.parseInt(second.toString());
            if (operator.compareTo("+") == 0)
                return firstNum + secondNum;
            if (operator.compareTo("-") == 0)
                return firstNum - secondNum;
            if (operator.compareTo("*") == 0)
                return firstNum * secondNum;
            if (operator.compareTo("%") == 0) {
                if (secondNum == 0) {
                    System.out.println("You can't divide by zero.");
                    System.exit(1);
                }
                return firstNum % secondNum;
            } else {
                if (secondNum == 0) {
                    System.out.println("You can't divide by zero.");
                    System.exit(1);
                }
                return firstNum / secondNum;
            }
        } catch (NumberFormatException ex) {
            System.out.println("Numbers in the stack can't be parsed.");
            return null;
        }

    }

    /**
     * Returns the object at the peek of the stack and pop it from the stack. If it
     * can't take from the stack it prints the error message and exit the program.
     * 
     * @return peek of the stack
     */
    private static Object getFromStack() {

        Object result = null;
        try {
            result = stack.pop();

        } catch (EmptyStackException ex) {
            System.out.println("String can't be parsed.");
            System.exit(1);
        }
        return result;
    }
}
