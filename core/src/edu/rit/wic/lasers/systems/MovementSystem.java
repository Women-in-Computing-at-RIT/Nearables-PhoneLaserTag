package edu.rit.wic.lasers.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import edu.rit.wic.lasers.components.ComponentMappers;
import edu.rit.wic.lasers.components.MovementComponent;
import edu.rit.wic.lasers.components.TransformComponent;

/**
 * Created by Matthew on 10/9/2016.
 */
public class MovementSystem extends IteratingSystem {

	private final Vector2 tmp = new Vector2();

	private final ComponentMapper<TransformComponent> transformMapper = ComponentMappers.TRANSFORM_MAPPER;
	private final ComponentMapper<MovementComponent> mvmtMapper = ComponentMappers.MOVEMENT_MAPPER;

	public MovementSystem() {
		super(Family.all(TransformComponent.class, MovementComponent.class).get());
	}

	@Override
	protected void processEntity(final Entity entity, final float deltaTime) {
		TransformComponent transform = transformMapper.get(entity);
		MovementComponent movement = mvmtMapper.get(entity);

		tmp.set(movement.acceleration).scl(deltaTime);
		movement.velocity.add(tmp);

		tmp.set(movement.velocity).scl(deltaTime);
		transform.position.add(tmp.x, tmp.y, 0);
	}
}
