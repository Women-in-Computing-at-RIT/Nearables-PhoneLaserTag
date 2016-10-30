package edu.rit.wic.lasers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.kiwi.util.gdx.asset.Disposables;
import com.github.czyzby.kiwi.util.gdx.collection.GdxArrays;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.util.LmlParserBuilder;

import edu.rit.wic.lasers.assets.AssetUtils;
import edu.rit.wic.lasers.assets.Assets;
import edu.rit.wic.lasers.assets.LmlTemplate;
import edu.rit.wic.lasers.assets.LmlTemplateLoader;
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
	public final Beam logger = Beam.BEAM;
	/** Notification Utility (Visual Notifications) */
	public final Notifier notifier;

	private Stage ui;
	private SpriteBatch batch;
	private AssetManager assetManager;

	LaserTagGame(Notifier notifier) {
		super();
		this.notifier = notifier;
	}

	@Override
	public void create() {
		this.batch = new SpriteBatch();
		this.ui = new Stage();
		this.assetManager = AssetUtils.initialize(Assets.SPLASH_IMAGE);

		Beam.interceptUncaughtExceptions();

		LmlParser parser = new LmlParserBuilder()
			.i18nBundle(Assets.I18N_APP_BUNDLE.get(I18NBundle.class, this.assetManager))
			.debugLinesOnException(5)
			.build();
		this.assetManager.setLoader(LmlTemplate.class, ".lml", new LmlTemplateLoader(parser));

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

	public void setUi(final Camera camera) {
		Viewport view = new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(),
			Gdx.graphics.getHeight(), camera);
		this.ui = new Stage(view, this.batch);
	}

	public Stage getUi() {
		return this.ui;
	}

	public void drawUi(final float delta) {
		this.ui.act(delta);
		this.ui.draw();
	}

}
