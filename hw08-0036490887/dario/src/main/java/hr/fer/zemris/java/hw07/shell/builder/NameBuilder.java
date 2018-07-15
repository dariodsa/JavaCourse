package hr.fer.zemris.java.hw07.shell.builder;

/**
 * {@link NameBuilder} encapsulate builder which will add new data to the {@link NameBuilderInfo}.
 * @author dario
 *
 */
public interface NameBuilder {
	/**
	 * Method will update {@link NameBuilderInfo} string builder, it will append new data.
	 * @param info {@link NameBuilderInfo}, using only string builder
	 */
	void execute(NameBuilderInfo info);
}
