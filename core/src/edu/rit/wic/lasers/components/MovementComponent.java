package edu.rit.wic.lasers.components;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Matthew on 10/9/2016.
 */
public class MovementComponent implements PoolableComponent {

	public final Vector2 velocity = new Vector2();
	public final Vector2 acceleration = new Vector2();

	@Override
	public void reset() {
		this.velocity.setZero();
		this.acceleration.setZero();
	}

}
