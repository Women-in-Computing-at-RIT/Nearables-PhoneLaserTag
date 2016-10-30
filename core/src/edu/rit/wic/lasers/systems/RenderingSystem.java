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

import edu.rit.wic.lasers.Ref.Graphics;
import edu.rit.wic.lasers.components.ComponentMappers;
import edu.rit.wic.lasers.components.TextureComponent;
import edu.rit.wic.lasers.components.TransformComponent;
import edu.rit.wic.lasers.logging.Beam;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p> {@link IteratingSystem} to handle {@link Entity entities} that can be rendered. Entities that can be rendered
 * must have a {@link TransformComponent} for position and orientation, and a {@link TextureComponent} containing the
 * graphics data. </p> <p> <p> Rendering is depth-based, thus entities are added to a Max-Heap ordered by z-value. This
 * is handled by {@link PriorityQueue}. </p>
 *
 * @author Matthew Crocco
 */
public class RenderingSystem extends IteratingSystem {

	private static final ComponentMapper<TextureComponent> texMapper = ComponentMappers.TEX_MAPPER;
	private static final ComponentMapper<TransformComponent> transformMapper = ComponentMappers.TRANSFORM_MAPPER;
	private static final Comparator<Entity> renderComparator = (entA, entB) -> {
		TransformComponent transformA = transformMapper.get(entA);
		TransformComponent transformB = transformMapper.get(entB);

		return Float.compare(transformB.position.z, transformA.position.z);
	};

	private final SpriteBatch spriteBatch;
	private final Queue<Entity> renderQueue;
	private final Viewport renderView;

	public RenderingSystem(final SpriteBatch batch) {
		this(batch, Viewports.getDensityAwareViewport());
	}

	public RenderingSystem(final SpriteBatch batch, final Viewport viewport) {
		super(Family.all(TransformComponent.class, TextureComponent.class).get());

		this.spriteBatch = batch;
		this.renderView = viewport;

		Beam.BEAM.v("Rendering using '%s' viewport", this.renderView.getClass().getName());

		this.renderQueue = new PriorityQueue<>(20, renderComparator);
	}

	@Override
	public void update(final float deltaTime) {
		super.update(deltaTime);

		final Camera renderCam = this.renderView.getCamera();
		this.renderView.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

		this.spriteBatch.setProjectionMatrix(renderCam.combined);
		this.spriteBatch.begin();

		// Process all jobs in render queue.
		//
		// The render queue is a PriorityQueue which is a Max Heap. Therefore
		// all background elements are rendered first since BackgroundComponents
		// (if updated by a Background System) have a large positive Z-value.
		while (!renderQueue.isEmpty()) {
			Entity current = renderQueue.remove();

			TextureComponent curTex = texMapper.get(current);

			// Something has probably gone PRETTY wrong if there is no texture
			// to render... but prevents foul play from destroying everything.
			//
			// This is why we can't have nice things.
			if (curTex.texture == null) {
				Beam.BEAM.v("Rendering Entity: Attempt to render entity with no " + "texture in texture component!");
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

			Beam.BEAM.v("Rendering Entity: Pos[(%.3f, %.3f)] Orig[(%.3f, %.3f)] WxH[" + "(%.3f, %.3f)] Scl[(%.3f, %"
				+ ".3f)] Rot[%.3f]", pos.x, pos.y, originX, originY, width, height, scaling.x, scaling.y, curTransform
				.rotation);
			Beam.BEAM.v("Rendering Entity: Using Meters Per Pixel value of %f", Graphics.METERS_PER_PIXEL);

			// Let M2P = Meters Per Pixel Constant
			// Position = (x, y) = (entX - originX, entY - originY)
			// Origin (Texture Coords) = (x, y) = (originX, originY)
			// width = Width of Texture
			// height = Height of Texture
			// Scale = (x, y) = (entScaleX * M2P, entScaleY * M2P)
			// angle = Rotation of texture relative to origin
			spriteBatch.draw(tex, pos.x - originX, pos.y - originY, originX, originY, width, height, scaling.x *
				Graphics.METERS_PER_PIXEL, scaling.y * Graphics.METERS_PER_PIXEL, MathUtils.radiansToDegrees *
				curTransform.rotation);

		}

		spriteBatch.end();
		renderQueue.clear();
	}

	@Override
	protected void processEntity(final Entity entity, final float deltaTime) {
		this.renderQueue.add(entity);
	}

	public Viewport getRenderViewport() {
		return this.renderView;
	}
}
