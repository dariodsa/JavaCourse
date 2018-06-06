package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.math.Vector2D;

public class TurtleState {

	/**
	 * {@link Vector2D} position of the turtle
	 */
	private Vector2D position;
	/**
	 * {@link Vector2D} direction of the turtle
	 */
	private Vector2D direction;
	/**
	 * {@link Color} which will turtle leave by moving on the screen
	 */
	private Color color;
	/**
	 * how many big step turtle make by one move
	 */
	private double step;
	/**
	 * It constructs the {@link TurtleState} with the initial parameters.
	 */
	public TurtleState() {

		this.position = new Vector2D(0, 0);
		this.direction = new Vector2D(1, 0);
		color = Color.BLACK;
		step = 1;
	}
	/**
	 * Copies the current object and returns it.
	 * @return new {@link TurtleState} same as current object
	 */
	public TurtleState copy() {
		TurtleState turtle = new TurtleState();
		turtle.direction = this.direction.copy();
		turtle.position = this.position.copy();
		turtle.color = this.color;
		turtle.step = this.step;

		return turtle;
	}
	/**
	 * Returns the current position of the turtle. 
	 * @return {@link Vector2D} current position of the turtle
	 */
	public Vector2D getPosition() {
		return position;
	}
	/**
	 * Sets the current position of the turtle.
	 * @param position {@link Vector2D} new position of the turtle
	 * @throws NullPointerException
	 */
	public void setPosition(Vector2D position) {
		if(position == null) throw new NullPointerException("position can't be null.");
		this.position = position;
	}
	/**
	 * Returns the current {@link Vector2D} direction of the turtle.
	 * @return current direction of the turtle
	 */
	public Vector2D getDirection() {
		return direction;
	}
	/**
	 * Sets the {@link Vector2D} direction of the turtle.
	 * @param direction 
	 * @throws NullPointerException
	 */
	public void setDirection(Vector2D direction) {
		if(direction == null) throw new NullPointerException("direction can't be null.");
		this.direction = direction;
	}
	/**
	 * Returns the current ink of the turtle.
	 * @return {@link Color} current color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * Sets the {@link Color} color of the future lines.
	 * @param color color of the future lines
	 */
	public void setColor(Color color) {
		if(color == null) throw new NullPointerException("color can't be null.");
		this.color = color;
	}
	/**
	 * Returns the initial move of the turtle
	 * @return initial move of the turtle
	 */
	public double getStep() {
		return step;
	}
	/**
	 * Sets the intial move of the turtle.
	 * @param step intitial move 
	 */
	public void setStep(double step) {
		this.step = step;
	}
}
