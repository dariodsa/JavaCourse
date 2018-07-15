package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;
/**
 * 
 * @author dario
 *
 */
public interface CalcModel {
	/**
	 * Adds new {@link CalcValueListener} to the list of observers.
	 * @param l listener
	 */
    	void addCalcValueListener(CalcValueListener l);
	/**
	 * Removes {@link CalcValueListener} from the list of observers.
	 * @param l listener
	 */
    	void removeCalcValueListener(CalcValueListener l);
	/**
	 * Returns String value of a current result in a calculator.
	 * @return {@link String} representation of a value in calculator
	 */
    	String toString();
	/**
	 * Returns current value of result in calculator. 
	 * @return double value of result 
	 */
    	double getValue();
	/**
	 * Sets new value of a reuslt in calculator.
	 * @param value new value
	 */
    	void setValue(double value);
	/**
	 * Clears current value of a calculator.
	 */
    	void clear();
	/**
	 * Clears current value of a calculator, pending operation and active 
	 * operand as well. 
	 */
    	void clearAll();
	/**
	 * Swaps sign of a value, sets it negative if it was positive, of positive if 
	 * it was negative.
	 */
    	void swapSign();
	/**
	 * INserts decimal point in the value.
	 */
    	void insertDecimalPoint();
	/**
	 * Inserts digit in the value.
	 * @param digit digit from 0 to 9  
	 */
    	void insertDigit(int digit);
	/**
	 * Returns true if the active operand is set.
	 * @return true if an active operand is set, false otherwise
	 */
	boolean isActiveOperandSet();
	/**
	 * Returns current active operand. 
	 * @return current active operand
	 */
	double getActiveOperand();
	/**
	 * Sets new active operand. 
	 * @param activeOperand new active operand.
	 */
	void setActiveOperand(double activeOperand);
	/**
	 * Removes current active operand. 
	 */
	void clearActiveOperand();
	/**
	 * Returns current pending binary operation
	 * @return {@link DoubleBinaryOperator} current binary operation
	 */
	DoubleBinaryOperator getPendingBinaryOperation();
	/**
	 * It sets new pending binary double operation.  
	 * @param op new pending binary double operation
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}