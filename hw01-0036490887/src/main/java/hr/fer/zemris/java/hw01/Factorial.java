package hr.fer.zemris.java.hw01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Program returns the factorial of the given number only if the number is natural and
 * from 1 to 20 included. Otherwise it will print an error. 
 * If you want to exit the program you should type "kraj" without quotes.
 * 
 * @version 1.0
 * @author Dario
 *
 */

public class Factorial {

	/**
	 * Main method, which is called if we run the program.
	 * @param args arguments of the command line, they aren't being used in this program
	 * @throws IOException
	 */
    	public static void main(String[] args) throws IOException {
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			
			System.out.print("Unesi broj > ");
			
			String line = bufferedReader.readLine();
			if(line == null) {
				break;
			}
			if(line.compareTo("kraj") == 0) {
				System.out.println("Doviđenja.");
			}
			try {
				int number = Integer.parseInt(line);
				getResult(number);
			}
			catch(NumberFormatException exception) {
				System.out.println(line + " nije cijeli broj.");
			}
		}
		
	}
	/**
	 * It prints the {@link #factorial(int) factorial} of the given number iff the number is less
	 * than 21 and bigger than 0. Otherwise prints the error note.  
	 * @param number
	 */
	public static void getResult(int number) {
		if(number < 1 || number > 20) {
			System.out.println(number + "nije broj u dozvoljenom rasponu.");
		} else {
			System.out.printf("%d! = %ld%n",number, factorial(number));
		}
	}
	/**
	 * Returns the factorial of the given number.
	 * @param n int
	 * @return long factorial of the n
	 * @throws IllegalArgumentException
	 */
	public static long factorial(int n) throws IllegalArgumentException{
		if(n < 0) {
		    throw new IllegalArgumentException("I don't know how to calculate factorial for negative numbers.");
		}
	    	long answer = 1;
		for(int i=1; i <= n; ++i) {
			answer *= i;
		}
		return answer;
	}

}
