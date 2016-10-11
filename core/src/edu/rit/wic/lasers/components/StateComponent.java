package edu.rit.wic.lasers.components;

/**
 * <p> {@link PoolableComponent Poolable component} for stateful {@link
 * com.badlogic.ashley.core.Entity entities}. Particularly useful for Animations where the
 * current time <i>for that specific entity</i> needs to be known to determine which frame
 * to display. Also maintains an integer state for use in a State Machine. </p>
 *
 * @author Matthew Crocco
 */
public class StateComponent implements PoolableComponent {

	public float time = 0.0f;
	private int state = 0;

	/**
	 * @return Current state as integer
	 */
	public int get() {
		return state;
	}

	/**
	 * Set current state to the given state, also resets the entity's state time to 0.
	 *
	 * @param state
	 * 	New state
	 */
	public void set(int state) {
		this.state = state;
		this.time = 0.0f;
	}

	@Override
	public void reset() {
		this.state = 0;
		this.time = 0.0f;
	}
}
