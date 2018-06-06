package hr.fer.zemris.java.hw07.shell.builder;

/**
 * {@link NameBuilderInfo} offers it's {@link StringBuilder} in which will others add data,
 * and also returns string by index in the group.
 * @author dario
 *
 */
public interface NameBuilderInfo {
	/**
	 * Returns {@link StringBuilder} of the {@link NameBuilderInfo}.
	 * @return it's string builder
	 */
	StringBuilder getStringBuilder();
	/**
	 * Return {@link String} by index in the group.
	 * @param index index in the group
	 * @return {@link String} with index in the group
	 */
	String getGroup(int index);
}
