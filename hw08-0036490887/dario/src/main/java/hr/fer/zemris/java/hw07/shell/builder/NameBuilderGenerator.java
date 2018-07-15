package hr.fer.zemris.java.hw07.shell.builder;

import java.util.List;

/**
 * {@link NameBuilderGenerator} implements {@link NameBuilder} which 
 * task is to run all builders received in the constructor.  
 * @author dario
 *
 */
public class NameBuilderGenerator implements NameBuilder{
	
	/**
	 * list of builders
	 */
	private List<NameBuilder> listOfBuilders;
	/**
	 * Constructs {@link NameBuilderGenerator} which task is to run all builders. 
	 * @param listOfBuilders list of builders which will be executed
	 */
	public NameBuilderGenerator(List<NameBuilder> listOfBuilders) {
		this.listOfBuilders = listOfBuilders;
	}
	@Override
	public void execute(NameBuilderInfo info) {
		for(NameBuilder builder : listOfBuilders) {
			builder.execute(info);
		}
	}

}
