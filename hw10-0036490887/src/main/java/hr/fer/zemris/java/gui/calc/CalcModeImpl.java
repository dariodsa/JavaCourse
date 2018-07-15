package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * {@link CalcModeImpl} implements {@link CalcModel} and specifies how should
 * calculator interpret user instructions and orders from the graphical
 * interface. It specifies how should active operand or pending binary operation
 * be set or removed from the active ones, and specifies when the value should
 * or shouldn't be changed, for example when the given number is just too big
 * for double representation, extra, input digit is not added.
 * 
 * @author dario
 *
 */
public class CalcModeImpl implements CalcModel {

    /**
     * current value
     */
    private String value = null;
    /**
     * current active operand
     */
    private Double activeOperand = null;
    /**
     * pending binary operation
     */
    private DoubleBinaryOperator pendingBinaryOperation = null;
    /**
     * list of listeners on the calculator model
     */
    private List<CalcValueListener> listeners = new ArrayList<>();
    /**
     * indicate should the content of the calculator be deleted when the user press
     * button with number or dot. Example is when 2 + 3 * 6, and we want to 5 to be
     * printed but not 6 to be appended to 5.
     */
    private boolean firstDigit = false;

    @Override
    public void addCalcValueListener(CalcValueListener l) {
	this.listeners.add(l);
    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {
	this.listeners.remove(l);
    }

    @Override
    public double getValue() {
	if (value == null)
	    return 0.0;
	return Double.parseDouble(value);

    }

    @Override
    public void setValue(double value) {
	if (notWantedNumber()) {
	    throw new IllegalArgumentException("Double value infinitiy");
	}
	this.value = String.valueOf(value);
	notifyThem();
	firstDigit = true;
    }

    private boolean notWantedNumber() {
	double val = getValue();
	return val == Double.NaN || val == Double.POSITIVE_INFINITY || val == Double.NEGATIVE_INFINITY;
    }

    @Override
    public void clear() {
	value = null;
	notifyThem();
    }

    @Override
    public void clearAll() {
	clear();
	clearActiveOperand();
	pendingBinaryOperation = null;
	notifyThem();
    }

    @Override
    public void swapSign() {
	if (value == null || value.length() == 0)
	    return;
	if (value.charAt(0) != '-') {
	    value = '-' + value;
	} else {
	    value = value.substring(1);
	}
	notifyThem();
    }

    @Override
    public void insertDecimalPoint() {
	if (firstDigit) {
	    value = "0.";
	    firstDigit = false;
	    notifyThem();
	    return;
	}
	if (value == null || value.length() == 0) {
	    value = "0.";
	} else if (!value.contains(".")) {
	    value += ".";
	}
	notifyThem();
    }

    @Override
    public void insertDigit(int digit) {

	if (firstDigit) {
	    value = "";
	    value += digit;
	    firstDigit = false;
	    notifyThem();
	    return;
	}
	if (value == null)
	    value = "";
	if (digit == 0 && value.length() == 1 && value.charAt(0) == '0') {
	    return;
	}
	if (digit != 0 && value.length() == 1 && value.charAt(0) == '0') {
	    value = String.valueOf(digit);
	    notifyThem();
	    return;
	}
	value += String.valueOf(digit);
	if (notWantedNumber()) {
	    value = value.substring(0, value.length() - 1);
	    return;
	}
	notifyThem();
    }

    /**
     * Notifies all observers which were registered on this object when the value of
     * the result was changed.
     */
    public void notifyThem() {
	for (CalcValueListener l : listeners) {
	    l.valueChanged(this);
	}
    }

    @Override
    public boolean isActiveOperandSet() {
	return activeOperand != null;
    }

    @Override
    public double getActiveOperand() {
	if (activeOperand == null)
	    throw new IllegalStateException();
	return activeOperand;
    }

    public String toString() {

	if (value == null) {
	    return "0";
	}
	return value;
    }

    @Override
    public void setActiveOperand(double activeOperand) {
	this.activeOperand = activeOperand;

    }

    @Override
    public void clearActiveOperand() {
	this.activeOperand = null;

    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
	Objects.requireNonNull(pendingBinaryOperation);
	return pendingBinaryOperation;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
	this.pendingBinaryOperation = op;

    }

}
