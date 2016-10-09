package wic.rit.edu.lasers;

import wic.rit.edu.lasers.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.czyzby.kiwi.util.gdx.asset.Disposables;

public class LaserTagGame extends Game {
	private SpriteBatch batch;
	private AssetManager assetManager;

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.assetManager = Assets.initialize(Assets.TEX_SPLASH_SCREEN);

		Texture.setAssetManager(this.assetManager);
		setScreen(new GameScreen(this));
	}



	@Override
	public void dispose () {
		Disposables.disposeOf(this.batch, this.assetManager);
	}

	public SpriteBatch getBatch() {
		return this.batch;
	}

	public AssetManager getAssets() {
		return this.assetManager;
	}

}
