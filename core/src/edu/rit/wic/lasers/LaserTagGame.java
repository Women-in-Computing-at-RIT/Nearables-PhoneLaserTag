package edu.rit.wic.lasers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.czyzby.kiwi.util.gdx.asset.Disposables;
import com.github.czyzby.kiwi.util.gdx.collection.GdxArrays;

import edu.rit.wic.lasers.assets.AssetUtils;
import edu.rit.wic.lasers.assets.Assets;
import edu.rit.wic.lasers.bridge.Notifier;
import edu.rit.wic.lasers.logging.Beam;
import edu.rit.wic.lasers.screens.GameScreen;
import edu.rit.wic.lasers.screens.SplashScreen;

/**
 * Game launching class. Initializes necessary one-off utilities and tools like {@link
 * SpriteBatch} and {@link AssetManager}.
 *
 * @author Matthew Crocco
 */
public class LaserTagGame extends Game {

	/** Logging uility */
	public final Beam logger = Beam.INSTANCE;
	public final Notifier notif;

	private SpriteBatch batch;
	private AssetManager assetManager;

	public LaserTagGame(Notifier notifier) {
		super();
		this.notif = notifier;
	}

	@Override
	public void create() {
		this.batch = new SpriteBatch();
		this.assetManager = AssetUtils.initialize(Assets.SPLASH_IMAGE);

		this.logger.i("Initial assets loaded and ready! Moving to SplashScreen...");

		Texture.setAssetManager(this.assetManager);
		setScreen(new SplashScreen(this, Assets.SPLASH_IMAGE, GdxArrays.newArray(), ()
			-> setScreen(new GameScreen(this)), 5));
	}


	@Override
	public void dispose() {
		Disposables.disposeOf(this.batch, this.assetManager);
	}

	/**
	 * @return Game's one-and-should-be-only {@link SpriteBatch} instance.
	 */
	public SpriteBatch getBatch() {
		return this.batch;
	}

	/**
	 * @return Shared asset manager
	 */
	public AssetManager getAssets() {
		return this.assetManager;
	}

}
