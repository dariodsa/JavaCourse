package hr.fer.zemris.java.custom.scripting.exec;

import javax.management.RuntimeErrorException;

/**
 * Class {@link ValueWrapper} encapsulate object value, that can be added,
 * subtracted, multiplied or divied. You can also compare this object with
 * others.
 * 
 * @author dario
 *
 */
public class ValueWrapper {

	/**
	 * current value of the object
	 */
	private Object value;

	/**
	 * Constructs {@link ValueWrapper} with the given value.
	 * 
	 * @param value
	 *            object's value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Adds current value by the given parameter. If the operation is not possible
	 * exception will be throw.
	 * 
	 * @param incValue
	 *            by which we will increase current value
	 * @throws RuntimeErrorException
	 *             operation is not possible
	 */
	public void add(Object incValue) {

	    System.out.println("dsaaas");
	    Object firstValue = perform(value);
		Object secondValue = perform(incValue);
		
		if (firstValue.getClass() == Double.class || secondValue.getClass() == Double.class) {
			this.value = ((Number) firstValue).doubleValue() + ((Number) secondValue).doubleValue();

		} else {
			this.value = ((Integer) firstValue).intValue() + ((Integer) secondValue).intValue();
		}
	}

	/**
	 * Subtracts current value by the given parameter. If the operation is not
	 * possible exception will be throw.
	 * 
	 * @param decValue
	 *            by which we will subtract
	 * @throws RuntimeErrorException
	 *             operation is not possible
	 */
	public void subtract(Object decValue) {
		Object firstValue = perform(value);
		Object secondValue = perform(decValue);
		if (firstValue.getClass() == Double.class || secondValue.getClass() == Double.class) {
			this.value = ((Number) firstValue).doubleValue() - ((Number) secondValue).doubleValue();

		} else {
			this.value = ((Integer) firstValue).intValue() - ((Integer) secondValue).intValue();
		}
	}

	/**
	 * Multiplies current value by the given parameter. If the operation is not
	 * possible exception will be throw.
	 * 
	 * @param mulValue
	 *            by which we will multiply
	 * @throws RuntimeErrorException
	 *             operation is not possible
	 */
	public void multiply(Object mulValue) {
		Object firstValue = perform(value);
		Object secondValue = perform(mulValue);
		if (firstValue.getClass() == Double.class || secondValue.getClass() == Double.class) {
			this.value = ((Number) firstValue).doubleValue() * ((Number) secondValue).doubleValue();

		} else {
			this.value = ((Integer) firstValue).intValue() * ((Integer) secondValue).intValue();
		}
	}

	/**
	 * Divides current value by the given parameter. If the operation is not
	 * possible exception will be throw.
	 * 
	 * @param divValue
	 *            by which we will divide
	 * @throws IllegalArgumentException
	 *             dividing by zero
	 * @throws RuntimeErrorException
	 *             operation is not possible
	 */
	public void divide(Object divValue) {
		Object firstValue = perform(value);
		Object secondValue = perform(divValue);
		if (firstValue.getClass() == Double.class || secondValue.getClass() == Double.class) {
			if (((Number) secondValue).doubleValue() == 0)
				throw new IllegalArgumentException("Dividing by zero.");
			this.value = ((Number) firstValue).doubleValue() / ((Number) secondValue).doubleValue();

		} else {
			if (((Number) secondValue).intValue() == 0)
				throw new IllegalArgumentException("Dividing by zero.");
			this.value = ((Integer) firstValue).intValue() / ((Integer) secondValue).intValue();
		}
	}

	/**
	 * Compares current object to the given object as the parameter.
	 * @param withValue object with which object will be compared
	 * @return comparison value
	 */
	public int numCompare(Object withValue) {
		Object firstValue = perform(value);
		Object secondValue = perform(withValue);
		return Double.valueOf(((Number) firstValue).doubleValue())
				.compareTo(Double.valueOf(((Number) secondValue).doubleValue()));
	}

	/**
	 * Sets value of the {@link ValueWrapper}.
	 * 
	 * @param value
	 *            new value of the object
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Returns value of the {@link ValueWrapper}
	 * 
	 * @return value wrapper's value
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Perform method is responsible for getting the number value from 
	 * the object. If it is a String it will try to parse it to number value. 
	 * @param object object which will be performed
	 * @return performed object 
	 */
	public Object perform(Object object) {
	    
	    if (!(object == null || object.getClass() == String.class || object.getClass() == Integer.class
				|| object.getClass() == Double.class)) {
			throw new RuntimeException("Unexpected type " + object.getClass().getCanonicalName());
		}
		if (object == null) {
			object = Integer.valueOf(0);
		} else if (object.toString().indexOf('.') != -1 || object.toString().indexOf('E') != -1) {
			try {
				object = Double.parseDouble(object.toString());
			} catch (NumberFormatException ex) {
				throw new RuntimeException("Can't parse this string to double.");
			}
		} else {
			try {
				object = Integer.parseInt(object.toString());
			} catch (NumberFormatException ex) {
				throw new RuntimeException("Can't parse this string to double.");
			}
		}
		return object;

	}
	/**
	 * Clone method which clones current object.
	 */
	public ValueWrapper clone() {
	    if(value.getClass() == Double.class) {
	        return new ValueWrapper(((Double)value).doubleValue());
	    } else if(value.getClass() == Integer.class) {
            return new ValueWrapper(((Integer)value).intValue());
        }
	    return new ValueWrapper(value);
	}
}
