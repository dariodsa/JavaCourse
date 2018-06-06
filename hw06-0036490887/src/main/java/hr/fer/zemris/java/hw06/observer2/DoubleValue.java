package hr.fer.zemris.java.hw06.observer2;


/**
 * Class {@link DoubleValue} observers object and when that object is changed it
 * prints to the standard output that value multiply it by 2.
 * 
 * @author dario
 *
 */
public class DoubleValue implements IntegerStorageObserver{

	/**
	 * number of modification that are tracked by this observer
	 */
	private int modificationCount;
	/**
	 * Constructs {@link SquareValue} with the number of modifications
	 * that are tracked by this object. When the observed object modifies 
	 * more then n times, this observer will automatically unregister. 
	 * @param modificationCount number of modifications that 
	 * are tracked by this observer
	 */
	public DoubleValue(int modificationCount) {
		this.modificationCount = modificationCount;
	}
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		--modificationCount;
		if(modificationCount < 0) {
			istorage.getStorage().removeObserver(this);
			return;
		}
		System.out.printf("Double value: %d%n", istorage.getNewValue() * 2);
		
	}

}
