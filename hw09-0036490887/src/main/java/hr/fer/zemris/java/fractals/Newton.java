package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class {@link Newton} runs {@link NewtonProducer} in order to show factors
 * based on Newton-Raphson iteration. It accepts user input, roots of the
 * polynom which will be used later in processing. When user entered all roots,
 * he need to enter key word "done" to stop entering inputs and to start
 * 
 * @author dario
 */
public class Newton {

    /**
     * welcome message
     */
    private static final String MESSAGE = "Welcome to Newton-Raphson iteration-based fractal viewer.\n"
            + "Please enter at least two roots, one root per line. Enter 'done' when done.";
    /**
     * string for ending entering inputs
     */
    private static final String DONE = "done";
    /**
     * error message
     */
    private static final String ERROR_MESSAGE = "Please try again. :-(";

    /**
     * Main method which starts program which read from STDIN, converts in
     * {@link Complex} numbers and draw a picture from it.
     * 
     * @param args
     *            not used
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println(MESSAGE);

        List<Complex> roots = new ArrayList<>();
        int numOfRoot = 1;
        while (true) {
            printInputStatus(numOfRoot);

            String input = scanner.nextLine();
            if (input.equals(DONE) && numOfRoot > 1) {
                break;
            } else if (input.equals(DONE)) {
                System.out.println("Please enter only one root.");
                continue;
            }

            try {
                Complex result = parsing(input);
                roots.add(result);
                ++numOfRoot;
            } catch (NumberFormatException ex) {
                System.out.println(ERROR_MESSAGE);
            }
        }
        scanner.close();
        FractalViewer.show(new NewtonProducer(new ComplexRootedPolynomial(roots.toArray(new Complex[0]))));
    }

    /**
     * Parse given input which should represent complex number. It will throw
     * {@link NumberFormatException} if there is some error in parsing.
     * 
     * @param input
     *            string input
     * @param result
     *            {@link Complex} number, for result
     */
    private static Complex parsing(String input) {
        Complex result = null;

        input = input.replace(" ", "");
        int index = 0;
        for (int i = 0; i < input.length(); ++i) {
            if (input.charAt(i) == '+' || input.charAt(i) == '-') {
                index = i;
                break;
            }
        }
        double real = 0;
        double imaginary = 0;
        if (index == 0) {
            if (input.contains("i")) {
                if (input.equals("i")) {
                    result = new Complex(0, 1);
                    return result;
                } else if (input.equals("-i")) {
                    result = new Complex(0, -1);
                    return result;
                } else {
                    input = input.replace("i", "");
                    result = new Complex(0, Double.parseDouble(input));
                    return result;
                }
            } else {
                real += Double.parseDouble(input);
            }
        } else {
            String first = input.substring(0, index);
            String second = input.substring(index);
            if (first.contains("i")) {
                first = first.replace("i", "");
                imaginary += Double.parseDouble(first);
            } else if (!first.contains("i")) {
                real += Double.parseDouble(first);
            }

            if (second.contains("i")) {
                second = second.replace("i", "");
                imaginary += Double.parseDouble(second);
            } else if (!second.contains("i")) {
                real += Double.parseDouble(second);
            }
        }
        // System.out.println(real + " " + imaginary);
        result = new Complex(real, imaginary);
        return result;
    }

    /**
     * Prints input helper text. It says which root are you entering.
     * 
     * @param numOfRoot
     *            nuber of roots which you are entering
     */
    private static void printInputStatus(int numOfRoot) {
        System.out.println("Root " + numOfRoot + "> ");
    }

}
