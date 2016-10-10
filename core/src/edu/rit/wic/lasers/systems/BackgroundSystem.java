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
 * Created by Matthew on 10/9/2016.
 */
public class BackgroundSystem extends IteratingSystem {

	private final ComponentMapper<BackgroundComponent> bgMapper = ComponentMappers.BG_MAPPER;
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

	@Override protected void processEntity(final Entity entity, final float deltaTime) {
		TransformComponent transform = transformMapper.get(entity);

		transform.position.set(this.camera.position.x, this.camera.position.y, 10.0f);
	}
}
