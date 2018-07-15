package hr.fer.zemris.java.hw07.shell.builder;

/**
 * Command {@link NameBuilderStringSize} sets new data to the {@link NameBuilderInfo}, which 
 * is the minimal length of the given value. If the string is less then that length, it will 
 * be added with filling char. After that it will be appended to the string builder when the command 
 * execute will be called.
 * 
 * @see NameBuilderInfo
 * @author dario
 *
 */

public class NameBuilderStringSize implements NameBuilder{
	
	/**
	 * group index
	 */
	private int group;
	/**
	 * filling char
	 */
	private char fillChar;
	/**
	 * length
	 */
	private int len;
	
	/**
	 * Constructs {@link NameBuilderStringSize} with grouping index, filling char and expected length if the 
	 * given string length is less the that one. 
	 * @param group grouping index
	 * @param len minimal length 
	 * @param fillChar filling char
	 */
	public NameBuilderStringSize(int group, int len , char fillChar) {
		this.group = group;
		this.len = len;
		this.fillChar = fillChar;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(fill(info.getGroup(group), fillChar, len));
	}
	/**
	 * If the value's length is less then len it will be added char c times how much is 
	 * needed to have length equal to len, otherwise it returns original string.
	 * @param value string value
	 * @param c filling char
	 * @param len length of the expected string
	 * @return new transformed string
	 */
	private String fill(String value, char c, int len) {
		StringBuilder bob = new StringBuilder();
		if(value.length() >= len) return value;
		else {
			for(int i=0;i< len - value.length(); ++i) 
				bob.append(c);
			
			bob.append(value);
		}
		return bob.toString();
	}
}
