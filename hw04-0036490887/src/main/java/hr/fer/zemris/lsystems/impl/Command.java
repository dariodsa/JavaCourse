package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;
/**
 * Interface {@link Command} abstract the method {@link #execute(Context, Painter)}.
 * @author dario
 *
 */
public interface Command {
	/**
	 * Allows you to execute something on the painter with the Context. 
	 * @param ctx - {@link Context} context of the working process
	 * @param painter - {@link Painter} allows you to draw lines 
	 */
	public void execute(Context ctx, Painter painter);
}
