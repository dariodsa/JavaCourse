package hr.fer.zemris.java.hw07.shell.builder;

/**
 * {@link NameBuilderString} task is to add given data as it was 
 * given to the {@link NameBuilderInfo} string builder. 
 * @author dario
 *
 */
public class NameBuilderString implements NameBuilder{
	
	/**
	 * new data, expression
	 */
	private String expression;
	
	/**
	 * Constructs {@link NameBuilderString} which task is to add new data.  
	 * @param expression new data
	 */
	public NameBuilderString(String expression) {
		this.expression = expression;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(expression);
	}
	
}
