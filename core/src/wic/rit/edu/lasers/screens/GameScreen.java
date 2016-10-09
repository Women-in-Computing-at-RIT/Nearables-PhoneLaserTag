package wic.rit.edu.lasers.screens;

import wic.rit.edu.lasers.Assets;
import wic.rit.edu.lasers.LaserTagGame;
import wic.rit.edu.lasers.systems.AnimationSystem;
import wic.rit.edu.lasers.systems.BackgroundSystem;
import wic.rit.edu.lasers.systems.MovementSystem;
import wic.rit.edu.lasers.systems.RenderingSystem;
import wic.rit.edu.lasers.systems.StateSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.common.base.Function;
import com.google.common.base.Supplier;

/**
 * Created by Matthew on 10/8/2016.
 */
public class GameScreen implements Screen {

	private final LaserTagGame gameInstance;
	private final AssetManager assetManager;
	private final SpriteBatch spriteBatch;

	private final OrthographicCamera guiCam;
	private final Supplier<OrthographicCamera> renderCamProvider;
	private final Function<Vector3, Vector3> unproject;

	private final Color bgColor = Assets.COLOR_BACKGROUND;
	private final Vector2 touchPoint = new Vector2();
	private final Engine engine = new PooledEngine();

	public GameScreen(final LaserTagGame game) {
		this.gameInstance = game;
		this.assetManager = game.getAssets();
		this.spriteBatch = game.getBatch();

		this.guiCam = new OrthographicCamera(320, 480);
		this.guiCam.position.scl(320/2, 480/2, 0);		// Center GUI Camera

		RenderingSystem renderingSystem = new RenderingSystem(this.spriteBatch);
		BackgroundSystem bgSystem = new BackgroundSystem((renderCamProvider = renderingSystem::getRenderCamera).get());

		this.engine.addSystem(bgSystem);
		this.engine.addSystem(new MovementSystem());
		this.engine.addSystem(new StateSystem());
		this.engine.addSystem(new AnimationSystem());
		this.engine.addSystem(renderingSystem);

		this.unproject = (v) -> renderCamProvider.get().unproject(v);
	}

	@Override public void show() {

	}

	@Override public void render(final float delta) {
		Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		this.engine.update(delta);
	}

	@Override public void resize(final int width, final int height) {

	}

	@Override public void pause() {

	}

	@Override public void resume() {

	}

	@Override public void hide() {

	}

	@Override public void dispose() {

	}
}
