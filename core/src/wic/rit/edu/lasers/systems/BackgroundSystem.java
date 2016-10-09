package wic.rit.edu.lasers.systems;

import wic.rit.edu.lasers.components.BackgroundComponent;
import wic.rit.edu.lasers.components.ComponentMappers;
import wic.rit.edu.lasers.components.TransformComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Matthew on 10/9/2016.
 */
public class BackgroundSystem extends IteratingSystem {

	private final ComponentMapper<BackgroundComponent> bgMapper = ComponentMappers.BG_MAPPER;
	private final ComponentMapper<TransformComponent> transformMapper = ComponentMappers.TRANSFORM_MAPPER;

	private OrthographicCamera camera;

	public BackgroundSystem(OrthographicCamera camera) {
		super(Family.all(BackgroundComponent.class, TransformComponent.class).get());

		this.camera = camera;
	}

	public OrthographicCamera getCamera() {
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
