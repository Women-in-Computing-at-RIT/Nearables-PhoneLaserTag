package edu.rit.wic.lasers.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.github.czyzby.kiwi.util.gdx.GdxUtilities;
import static com.google.common.base.Preconditions.checkArgument;

import edu.rit.wic.lasers.LaserTagGame;
import edu.rit.wic.lasers.assets.Asset;
import edu.rit.wic.lasers.assets.AssetType;
import edu.rit.wic.lasers.components.TextureComponent;
import edu.rit.wic.lasers.components.TransformComponent;
import edu.rit.wic.lasers.functional.Callback;
import edu.rit.wic.lasers.systems.RenderingSystem;

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
		checkArgument(isGraphicalAsset(image), "SplashScreen expects Texture Asset!");

		this.game = game;
		this.minDisplayTime = minSeconds;

		this.game.logger.tag("SPLASH");
		this.game.logger.i("Displaying splash screen for at least ~%.2f seconds", minSeconds);
		this.game.logger.i("SplashScreen Asset: '%s' of type '%s'", image.getPath(), image.getAssetType());

		TransformComponent splashTransform = new TransformComponent();
		TextureComponent splashTextureComp = new TextureComponent();
		splashTextureComp.texture = new TextureRegion((Texture) image.get(game.getAssets()));

		float width = splashTextureComp.texture.getTexture().getWidth();
		float height = splashTextureComp.texture.getTexture().getHeight();

		Entity splashEntity = new Entity();
		splashEntity.add(splashTextureComp);
		splashEntity.add(splashTransform);

		engine.addSystem(new RenderingSystem(this.game.getBatch(), new StretchViewport(width, height)));
		engine.addEntity(splashEntity);

		for (Asset asset : forLoading) {
			this.game.logger.i("Adding '%s' to load queue...", asset.getPath());
			asset.load(this.game.getAssets());
		}

		this.transition = onComplete;
	}

	private boolean isGraphicalAsset(Asset asset) {
		return asset.getAssetType() == AssetType.GRAPHICS && Texture.class.isAssignableFrom(asset.getAssetClass());
	}

	@Override public void render(final float delta) {
		GdxUtilities.clearScreen();

		engine.update(delta);

		if (elapsedTime >= minDisplayTime && this.game.getAssets().update()) {
			this.transition.call();
		} else if(!this.game.getAssets().update()){
			float progress = this.game.getAssets().getProgress();
			this.game.logger.i("SplashScreen Loading: %d%% of the way done.", (int) (progress * 100));
		}

		elapsedTime += delta - pausedDelta;
		this.game.logger.v("SplashScreen Loading: minTime: Min Time: %.3f, Elapsed: %.3f, pauseTime: %.3f", minDisplayTime, elapsedTime, pausedDelta);
		pausedDelta = 0;
	}

	@Override public void pause() {
		this.game.logger.i("SplashScreen Paused");
		pausedDelta = System.nanoTime();
		super.pause();
	}

	@Override public void resume() {
		pausedDelta = (System.nanoTime() - pausedDelta) / 1000000000.0f;
		this.game.logger.i("SplashScreen Resumed: Calculated Pause time is %.3f", pausedDelta);
		super.resume();
	}
}
