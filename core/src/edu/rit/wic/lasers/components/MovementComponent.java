package edu.rit.wic.lasers.components;

import com.badlogic.gdx.math.Vector2;

/**
 * <p> {@link PoolableComponent Poolable component} for {@link
 * com.badlogic.ashley.core.Entity entities} that can move. Consists of vectors for things
 * like Velocity and Acceleration. </p>
 *
 * @author Matthew Crocco
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
