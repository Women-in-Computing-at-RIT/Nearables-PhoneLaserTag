package edu.rit.wic.lasers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.czyzby.kiwi.util.gdx.asset.Disposables;
import com.github.czyzby.kiwi.util.gdx.collection.GdxArrays;

import edu.rit.wic.lasers.assets.AssetUtils;
import edu.rit.wic.lasers.assets.Assets;
import edu.rit.wic.lasers.logging.Beam;
import edu.rit.wic.lasers.screens.GameScreen;
import edu.rit.wic.lasers.screens.SplashScreen;

public class LaserTagGame extends Game {
	public final Beam logger = Beam.INSTANCE;
	private SpriteBatch batch;
	private AssetManager assetManager;

	@Override public void create() {
		this.batch = new SpriteBatch();
		this.assetManager = AssetUtils.initialize(Assets.SPLASH_IMAGE);

		this.logger.i("Initial assets loaded and ready! Moving to SplashScreen...");

		Texture.setAssetManager(this.assetManager);
		setScreen(new SplashScreen(this, Assets.SPLASH_IMAGE, GdxArrays.newArray(), () -> setScreen(new GameScreen(this)), 5));
	}



	@Override public void dispose() {
		Disposables.disposeOf(this.batch, this.assetManager);
	}

	public SpriteBatch getBatch() {
		return this.batch;
	}

	public AssetManager getAssets() {
		return this.assetManager;
	}

}
