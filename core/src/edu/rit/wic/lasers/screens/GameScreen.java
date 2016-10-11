package edu.rit.wic.lasers.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.kiwi.util.gdx.viewport.Viewports;
import com.google.common.base.Function;
import com.google.common.base.Supplier;

import edu.rit.wic.lasers.LaserTagGame;
import edu.rit.wic.lasers.assets.AssetUtils;
import edu.rit.wic.lasers.assets.Assets;
import edu.rit.wic.lasers.systems.AnimationSystem;
import edu.rit.wic.lasers.systems.BackgroundSystem;
import edu.rit.wic.lasers.systems.MovementSystem;
import edu.rit.wic.lasers.systems.RenderingSystem;
import edu.rit.wic.lasers.systems.StateSystem;

/**
 * Primary Game Screen where most game interaction occurs.
 *
 * @author Matthew Crocco
 */
public class GameScreen implements Screen {

	private final LaserTagGame gameInstance;
	private final AssetManager assetManager;
	private final SpriteBatch spriteBatch;

	private final Stage guiStage;
	private final Supplier<Viewport> renderViewportProvider;
	private final Supplier<Camera> renderCamProvider;
	private final Function<Vector3, Vector3> unproject;

	private final Color bgColor = AssetUtils.COLOR_BACKGROUND;
	private final Vector2 touchPoint = new Vector2();
	private final Engine engine = new PooledEngine();

	private final Texture splashImage;

	public GameScreen(final LaserTagGame game) {
		this.gameInstance = game;
		this.assetManager = game.getAssets();
		this.spriteBatch = game.getBatch();

		this.guiStage = new Stage(Viewports.getDensityAwareViewport(), this.spriteBatch);

		this.splashImage = Assets.SPLASH_IMAGE.get(Texture.class, this.assetManager);

		RenderingSystem renderingSystem = new RenderingSystem(this.spriteBatch);
		renderViewportProvider = renderingSystem::getRenderViewport;
		BackgroundSystem bgSystem = new BackgroundSystem((renderCamProvider =
			renderViewportProvider
			.get()::getCamera).get());

		this.engine.addSystem(bgSystem);
		this.engine.addSystem(new MovementSystem());
		this.engine.addSystem(new StateSystem());
		this.engine.addSystem(new AnimationSystem());
		this.engine.addSystem(renderingSystem);

		this.unproject = (v) -> renderCamProvider.get().unproject(v);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(final float delta) {
		Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		this.engine.update(delta);
		this.guiStage.act(delta);
		this.guiStage.draw();
	}

	@Override
	public void resize(final int width, final int height) {
		Viewports.update(this.guiStage, width, height, true);
		renderViewportProvider.get().update(width, height, true);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
