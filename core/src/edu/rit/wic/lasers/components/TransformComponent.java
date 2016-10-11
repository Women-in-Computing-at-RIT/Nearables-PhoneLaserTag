package edu.rit.wic.lasers.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * <p>
 * {@link PoolableComponent Poolable component} for position, scale and orientation of
 * an {@link com.badlogic.ashley.core.Entity} within the game world.
 * </p>
 *
 * @author Matthew Crocco
 */
public class TransformComponent implements PoolableComponent {

	public final Vector3 position = new Vector3();
	public final Vector2 scale = new Vector2(1.0f, 1.0f);

	/** Degrees relative to positive x-axis */
	public float rotation = 0.0f;

	@Override
	public void reset() {
		position.setZero();
		scale.set(1.0f, 1.0f);
		rotation = 0;
	}
}
