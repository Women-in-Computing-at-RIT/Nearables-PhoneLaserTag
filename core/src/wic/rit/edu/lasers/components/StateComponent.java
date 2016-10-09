package wic.rit.edu.lasers.components;

/**
 * Created by Matthew on 10/9/2016.
 */
public class StateComponent implements PoolableComponent {

	private int state = 0;
	public float time = 0.0f;

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
