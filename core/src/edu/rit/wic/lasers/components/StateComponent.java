package edu.rit.wic.lasers.components;

/**
 * Created by Matthew on 10/9/2016.
 */
public class StateComponent implements PoolableComponent {

	public float time = 0.0f;
	private int state = 0;

	public int get() {
		return state;
	}

	public void set(int state) {
		this.state = state;
		this.time = 0.0f;
	}

	@Override public void reset() {
		this.state = 0;
		this.time = 0.0f;
	}
}
