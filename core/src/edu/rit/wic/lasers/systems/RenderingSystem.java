package edu.rit.wic.lasers.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.kiwi.util.gdx.viewport.Viewports;
import com.google.common.collect.MinMaxPriorityQueue;

import edu.rit.wic.lasers.Ref.Rendering;
import edu.rit.wic.lasers.components.ComponentMappers;
import edu.rit.wic.lasers.components.TextureComponent;
import edu.rit.wic.lasers.components.TransformComponent;
import edu.rit.wic.lasers.logging.Beam;

import java.util.Comparator;

/**
 * Created by Matthew on 10/9/2016.
 */
public class RenderingSystem extends IteratingSystem {


	private final SpriteBatch spriteBatch;
	private final MinMaxPriorityQueue<Entity> renderQueue;
	private final Viewport renderView;
	private Comparator<Entity> comparator;

	private final ComponentMapper<TextureComponent> texMapper = ComponentMappers.TEX_MAPPER;
	private final ComponentMapper<TransformComponent> transformMapper = ComponentMappers.TRANSFORM_MAPPER;

	public RenderingSystem(final SpriteBatch batch, final Viewport viewport) {
		super(Family.all(TransformComponent.class, TextureComponent.class).get());

		this.spriteBatch = batch;
		this.renderView = viewport;

		Beam.INSTANCE.v("Rendering using '%s' viewport", this.renderView.getClass().getName());

		this.comparator = (entA, entB) -> {
			TransformComponent transformA = transformMapper.get(entA);
			TransformComponent transformB = transformMapper.get(entB);

			return Float.compare(transformB.position.z, transformA.position.z);
		};

		this.renderQueue = MinMaxPriorityQueue.orderedBy(this.comparator).expectedSize(20).create();
	}

	public RenderingSystem(final SpriteBatch batch) {
		this(batch, Viewports.getDensityAwareViewport());
	}

	@Override public void update(final float deltaTime) {
		super.update(deltaTime);

		final Camera renderCam = this.renderView.getCamera();
		this.renderView.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

		this.spriteBatch.setProjectionMatrix(renderCam.combined);
		this.spriteBatch.begin();

		while (!renderQueue.isEmpty()) {
			Entity current = renderQueue.removeLast();

			TextureComponent curTex = texMapper.get(current);

			if (curTex.texture == null) {
				Beam.INSTANCE.v("Rendering Entity: Attempt to render entity with no texture in texture component!");
				continue;
			}

			TransformComponent curTransform = transformMapper.get(current);

			TextureRegion tex = curTex.texture;
			Vector3 pos = curTransform.position;
			Vector2 scaling = curTransform.scale;

			float width = curTex.texture.getRegionWidth();
			float height = curTex.texture.getRegionHeight();
			float originX = 0;
			float originY = 0;

			Beam.INSTANCE.v("Rendering Entity: Pos[(%.3f, %.3f)] Orig[(%.3f, %.3f)] WxH[(%.3f, %.3f)] Scl[(%.3f, %.3f)] Rot[%.3f]", pos.x, pos.y, originX, originY, width, height, scaling.x, scaling.y, curTransform.rotation);
			Beam.INSTANCE.v("Rendering Entity: Using Meters Per Pixel value of %f", Rendering.METERS_PER_PIXEL);
			spriteBatch.draw(tex, pos.x - originX, pos.y - originY, originX, originY, width, height, scaling.x * Rendering.METERS_PER_PIXEL, scaling.y * Rendering.METERS_PER_PIXEL, MathUtils.radiansToDegrees * curTransform.rotation);

		}

		spriteBatch.end();
		renderQueue.clear();
	}

	@Override protected void processEntity(final Entity entity, final float deltaTime) {
		this.renderQueue.add(entity);
	}

	public Viewport getRenderViewport() {
		return this.renderView;
	}
}
