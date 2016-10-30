package edu.rit.wic.lasers.screens;

import static com.google.common.base.Preconditions.checkArgument;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.github.czyzby.kiwi.util.gdx.GdxUtilities;

import edu.rit.wic.lasers.LaserTagGame;
import edu.rit.wic.lasers.assets.Asset;
import edu.rit.wic.lasers.assets.AssetType;
import edu.rit.wic.lasers.components.TextureComponent;
import edu.rit.wic.lasers.components.TransformComponent;
import edu.rit.wic.lasers.functional.Callback;
import edu.rit.wic.lasers.systems.RenderingSystem;

/**
 * <p> Intermediate SplashScreen to load assets before transitioning into another {@link com.badlogic.gdx.Screen}
 * instance. The requested {@link Asset assets} are queued to be loaded by the game's {@link
 * com.badlogic.gdx.assets.AssetManager AssetManager}. The screen is also given a minimum time to display in case
 * loading finishes early, so that the branding is shown at least for some time. </p> <p> While rendering and shown the
 * {@link com.badlogic.gdx.assets.AssetManager manager} is updated each frame. If the manager indicates loading is
 * finished the game will transition to the next screen if and only if the minimum display time specified has elapsed.
 * </p>
 *
 * @author Matthew Crocco
 */
public class SplashScreen extends ScreenAdapter {

	private final LaserTagGame game;
	private final Callback transition;

	private final Engine engine = new Engine();

	private final float minDisplayTime;
	private float elapsedTime = 0;
	private float pausedDelta = 0;

	/**
	 * Creates a Splash Screen using the given {@link Asset} as the image to display. The given {@link Iterable} of
	 * {@link Asset assets} are then loaded into the games {@link com.badlogic.gdx.assets.AssetManager} to be loaded.
	 *
	 * @param game
	 * 	{@link LaserTagGame} instance
	 * @param image
	 * 	Texture containing {@link Asset}
	 * @param forLoading
	 * 	{@link Iterable} of {@link Asset assets} to queue for loading
	 * @param onComplete
	 * 	{@link Callback} to indicate completion and to start transition
	 * @param minSeconds
	 * 	Minimum time to display splash screen in seconds
	 *
	 * @throws IllegalArgumentException
	 * 	If the given {@link Asset} to display is not a {@link AssetType#GRAPHICS graphical asset} nor {@link Texture}
	 * 	containing.
	 */
	public SplashScreen(final LaserTagGame game, final Asset image, Iterable<Asset> forLoading, Callback onComplete,
	                    float minSeconds) {
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

	/**
	 * @param asset
	 * 	{@link Asset} to check
	 *
	 * @return True if and only if the asset is a {@link AssetType#GRAPHICS graphical asset} and the asset class is a
	 * subclass of {@link Texture}
	 */
	private boolean isGraphicalAsset(Asset asset) {
		return asset.getAssetType() == AssetType.GRAPHICS && Texture.class.isAssignableFrom(asset.getAssetClass());
	}

	@Override
	public void render(final float delta) {
		GdxUtilities.clearScreen();

		engine.update(delta);

		if (elapsedTime >= minDisplayTime && this.game.getAssets().update()) {
			this.transition.call();
		} else if (!this.game.getAssets().update()) {
			float progress = this.game.getAssets().getProgress();
			this.game.logger.i("SplashScreen Loading: %d%% of the way done.", (int) (progress * 100));
		}

		elapsedTime += delta - pausedDelta;
		this.game.logger.v("SplashScreen Loading: minTime: Min Time: %.3f, Elapsed: %" + ".3f, pauseTime: %.3f",
			minDisplayTime, elapsedTime, pausedDelta);
		pausedDelta = 0;
	}

	@Override
	public void pause() {
		this.game.logger.i("SplashScreen Paused");
		pausedDelta = System.nanoTime();
		super.pause();
	}

	@Override
	public void resume() {
		pausedDelta = (System.nanoTime() - pausedDelta) / 1000000000.0f;
		this.game.logger.i("SplashScreen Resumed: Calculated Pause time is %.3f", pausedDelta);
		super.resume();
	}
}
