package edu.rit.wic.lasers.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

import edu.rit.wic.lasers.components.BackgroundComponent;
import edu.rit.wic.lasers.components.ComponentMappers;
import edu.rit.wic.lasers.components.TransformComponent;

/**
 * {@link IteratingSystem} to render background {@link Entity entities}. That is those
 * with a {@link TransformComponent} and {@link BackgroundComponent}. The primary
 * benefit is that the transform's z-value (depth) is ignored and the entity is forced
 * to the back and follows the camera.
 *
 * @author Matthew Crocco
 */
public class BackgroundSystem extends IteratingSystem {

	private final ComponentMapper<TransformComponent> transformMapper = ComponentMappers.TRANSFORM_MAPPER;

	private Camera camera;

	public BackgroundSystem(Camera camera) {
		super(Family.all(BackgroundComponent.class, TransformComponent.class).get());

		this.camera = camera;
	}

	public Camera getCamera() {
		return this.camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	@Override
	protected void processEntity(final Entity entity, final float deltaTime) {
		TransformComponent transform = transformMapper.get(entity);

		transform.position.set(this.camera.position.x, this.camera.position.y, 100.0f);
	}
}
