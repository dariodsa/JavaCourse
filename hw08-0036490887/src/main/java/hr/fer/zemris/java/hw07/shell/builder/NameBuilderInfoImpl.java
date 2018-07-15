package hr.fer.zemris.java.hw07.shell.builder;

import java.util.regex.Matcher;

/**
 * {@link NameBuilderInfoImpl} implements {@link NameBuilderInfo} and gets
 * {@link Matcher} in the constructor which will be used in the
 * {@link #getGroup(int)}.
 * 
 * @see NameBuilderInfo
 * @author dario
 *
 */
public class NameBuilderInfoImpl implements NameBuilderInfo {

	/**
	 * string builder of {@link NameBuilderInfoImpl}
	 */
	private StringBuilder builder;
	/**
	 * matcher for grouping
	 */
	private Matcher matcher;

	/**
	 * Constructs {@link NameBuilderInfoImpl} with specific matcher will be used in
	 * returning STring in method {@link #getGroup(int)}.
	 * 
	 * @param matcher
	 *            {@link Matcher} matcher of regex
	 */
	public NameBuilderInfoImpl(Matcher matcher) {
		this.builder = new StringBuilder();
		this.matcher = matcher;
	}

	@Override
	public StringBuilder getStringBuilder() {
		return this.builder;
	}

	@Override
	public String getGroup(int index) {
		return matcher.group(index);
	}

}
