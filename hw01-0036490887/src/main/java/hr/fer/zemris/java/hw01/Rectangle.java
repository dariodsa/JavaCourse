package hr.fer.zemris.java.hw01;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Scanner;

/**
 * 
 * @version 1.0
 * @author Dario
 *
 */
public class Rectangle {

    	/** Main method, which is called when we want to run a program. 
    	 * 
    	 * @param args
    	 */
    	public static void main(String[] args) {
		
		NumberFormat format = NumberFormat.getInstance();
		
		if(args.length != 0 && args.length != 2) {
			System.out.println("Program se trebao pokrenuti sa 2 ili 0 argumenata. Vi ste mu dali " 
								+ args.length + " argumenata."
			);
			System.exit(1);
		}
		
		if(args.length == 2) {
			try {
				double width  = format.parse(args[0]).doubleValue();
				double height = format.parse(args[1]).doubleValue();
				
				
				if(width < 0) {
					System.out.println("Unijeli ste negativnu širinu.");
				}
				else if(height < 0) {
					System.out.println("Unijeli ste negativnu visinu.");
				}
				else if(width == 0) {
				    System.out.println("Unijeli ste 0 za širinu.");
				}
				else if(height == 0) {
				    System.out.println("Unijeli ste 0 za visinu.");
				}
				else {
				    printResult(width, height);
				}
			} catch(NumberFormatException | ParseException exception) {
				System.out.println(args[0] + " i " + args[1] + " moraju biti brojčane vrijednosti.");
			}
			
		}
		else {
			Scanner scanner = new Scanner(System.in);
			double width  = getValue(scanner, "širinu");
			double height = getValue(scanner, "visinu");
			printResult(width, height);
		}

	}
	/**
	 * It returns the <code>double</code> value which was enter in the standard input. 
	 * If the given value can't be parsed in double using local {@link NumberFormat number format}
	 * settings, it prints error on standard output and again read double until it gets double.
	 * @param Scanner scanner which loads values from standard input
	 * @param printType string variable which is used to specify what we want from user
	 * @return double value from standard input
	 * @see <code>NumberFormat</code>
	 */
	public static double getValue(Scanner scanner, String printType) {
		
		NumberFormat format = NumberFormat.getInstance();
		
		System.out.print("Unesite " + printType + " > ");
		
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line == null) break;
			
			try {
				double value = format.parse(line).doubleValue();
				if(value < 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
					continue;
				}
				if(value == 0) {
				    	System.out.println("Unijeli ste nulu.");
				    	continue;
				}
				return value;
				
			} catch(NumberFormatException | ParseException exception) {
				System.out.println(line + " se ne može protumačiti kao broj.");
			}
			
			System.out.print("Unesite " + printType + " > ");
		}
		return 0;
	}
	/**
	 * Prints the rectangle's width, height , area and extent. 
	 * For area it uses {@link #getArea(double, double) getArea} 
	 * and for extent it uses {@link #getExtent(double, double) getExtent} 
	 * @param width of the rectangle
	 * @param height of the rectangle
	 */
	public static void printResult(double width, double height) {
		System.out.println("Pravokutnik širine " + width 
							+ " i visine " + height 
							+ " ima površinu " + getArea(width, height) 
							+ " te opseg " + getExtent(width, height)
		);
	}
	/**
	 * Calculates the area of the rectangle with width and height.
	 * Formula, which is used is width * height.
	 * @param width
	 * @param height
	 * @return area of the rectangle
	 */
	public static double getArea(double width, double height) {
		return width * height;
	}
	/**
	 * Calculates the  extent of the given rectangle with width and height.
	 * Formula, which is used if 2 * (height + width).
	 * @param width
	 * @param height
	 * @return extent of the rectangle
	 */
	public static double getExtent(double width, double height) {
		return 2 * (height + width);
	}
}
