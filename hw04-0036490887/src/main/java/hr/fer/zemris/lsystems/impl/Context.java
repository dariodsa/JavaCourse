package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.*;

/**
 * {@link Context} is like a {@link ObjectStack}, but it works with
 * {@link TurtleState} only. It cast all objects to {@link TurtleState} so other
 * objects might cause {@link ClassCastException}.
 * 
 * @author dario
 *
 */
public class Context {

	private ObjectStack stack;

	/**
	 * Construct the {@link Context} by initi the {@link ObjectStack} as the private
	 * atribute.
	 * 
	 * @see {@link hr.fer.zemris.java.custom.collections.ObjectStack ObjectStack}
	 */
	public Context() {
		this.stack = new ObjectStack();
	}

	/**
	 * It returns the {@link TurtleState} which is on the peek of the stack.
	 * 
	 * @return {@link TurtleState} which is on the peek of the stack
	 */
	public TurtleState getCurrentTurtleState() {
		return (TurtleState) stack.peek();
	}

	/**
	 * It pushs the new {@link TurtleState} at the peek of the {@link ObjectStack}.
	 * 
	 * @param state
	 *            {@link TurtleState}
	 */
	public void pushState(TurtleState state) {
		if (state == null)
			throw new IllegalArgumentException("Turtle state can't be null in context class.");
		if (!(state instanceof TurtleState)) {
			throw new ClassCastException("You should provide TurtleState, not something else.");
		}
		stack.push(state);
	}

	/**
	 * It removes the Object which is on the peek of the {@link ObjectStack}.
	 */
	public void popState() {
		stack.pop();
	}
}
