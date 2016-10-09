package edu.rit.wic.lasers;

import edu.rit.wic.lasers.assets.AssetUtils;
import edu.rit.wic.lasers.assets.Assets;
import edu.rit.wic.lasers.screens.GameScreen;
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
		this.assetManager = AssetUtils.initialize(Assets.SPLASH_IMAGE);

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
