package edu.rit.wic.lasers.screens;

import edu.rit.wic.lasers.LaserTagGame;
import edu.rit.wic.lasers.assets.Asset;
import edu.rit.wic.lasers.assets.Assets;
import edu.rit.wic.lasers.components.TextureComponent;
import edu.rit.wic.lasers.components.TransformComponent;
import edu.rit.wic.lasers.functional.Callback;
import edu.rit.wic.lasers.systems.RenderingSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.github.czyzby.kiwi.util.gdx.GdxUtilities;

/**
 * Created by Matthew on 10/9/2016.
 */
public class SplashScreen extends ScreenAdapter {

	private final LaserTagGame game;
	private final Engine engine = new Engine();
	private final Callback transition;

	private final float minDisplayTime;
	private float elapsedTime = 0;
	private float pausedDelta = 0;

	public SplashScreen(final LaserTagGame game, final Asset image, Iterable<Asset> forLoading, Callback onComplete, float minSeconds) {
		if(image.getAssetType() != Assets.AssetType.GRAPHICS || !ClassReflection.isAssignableFrom(Texture.class, image.getAssetClass())) {
			throw new IllegalArgumentException("SplashScreen expects Texture Asset!");
		}

		this.game = game;

		this.minDisplayTime = minSeconds;

		engine.addSystem(new RenderingSystem(this.game.getBatch()));

		TextureComponent splashTextureComp = new TextureComponent();
		splashTextureComp.texture = new TextureRegion((Texture)image.get(game.getAssets()));

		TransformComponent splashTransform = new TransformComponent();

		Entity splashEntity = new Entity();
		splashEntity.add(splashTextureComp);
		splashEntity.add(splashTransform);

		engine.addEntity(splashEntity);

		for(Asset asset : forLoading)
			asset.load(this.game.getAssets());

		this.transition = onComplete;
	}

	@Override public void render(final float delta) {
		GdxUtilities.clearScreen();

		engine.update(delta);

		if(elapsedTime >= minDisplayTime && this.game.getAssets().update()) {
			this.transition.call();
		}

		this.game.getAssets().update();
		float progress = this.game.getAssets().getProgress();
		Gdx.app.log("TEST", String.format("%.2f%% of the way done.", progress));

		elapsedTime += delta - pausedDelta;
	}

	@Override public void pause() {
		pausedDelta = System.nanoTime();
		super.pause();
	}

	@Override public void resume() {
		pausedDelta = (System.nanoTime() - pausedDelta) / 1000000000.0f;
		super.resume();
	}
}
