package edu.rit.wic.lasers.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.async.ThreadUtils;
import com.github.czyzby.kiwi.util.common.UtilitiesClass;
import com.github.czyzby.kiwi.util.gdx.collection.GdxArrays;
import com.github.czyzby.kiwi.util.gdx.collection.GdxSets;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Iterables;
import net.dermetfan.gdx.assets.AnnotationAssetManager;

import edu.rit.wic.lasers.functional.FloatConsumer;
import edu.rit.wic.lasers.math.ByteMath;

/**
 * Created by Matthew on 10/8/2016
 */
public final class AssetUtils extends UtilitiesClass {

	public static final Color COLOR_BACKGROUND = AssetUtils.hex(0xFF7C1F, 1.0f);
	public static final Color TINT_GREEN = AssetUtils.hex(0x8CFF00, 0.75f);

	/**
	 * Initialize an AssetManager, time will be spent loading whatever initial assets are specified to be
	 * immediately loaded in the parameter. These will be immediately available. This will also generate
	 * all TrueType Fonts.
	 * <p>
	 * No other assets will be loaded other than those specified.
	 *
	 * @param initialAssets
	 *     Initial assets to load
	 *
	 * @return AssetManager with initial assets loaded and no others
	 */
	public static AssetManager initialize(Iterable<AssetDescriptor<?>> initialAssets) {
		AnnotationAssetManager manager = new AnnotationAssetManager();

		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
		manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(new InternalFileHandleResolver()));

		for (AssetDescriptor<?> asset : initialAssets)
			manager.load(asset);

		manager.finishLoading();

		// TrueType Font Loading //
		loadttf(manager);

		return manager;
	}

	/**
	 * Initialize an AssetManager, time will be spent loading whatever initial assets are specified to be
	 * immediately loaded in the parameter. These will be immediately available. This will also generate
	 * all TrueType Fonts.
	 *
	 * @param splashTexture
	 *     Initial splash screen texture to load
	 *
	 * @return AssetManager with only the splash texture loaded
	 */
	public static AssetManager initialize(AssetDescriptor<Texture> splashTexture) {
		return initialize(GdxArrays.newArray(splashTexture));
	}

	/**
	 * Initialize an AssetManager, time will be spent loading whatever initial assets are specified to be
	 * immediately loaded in the parameter. These will be immediately available. This will also generate
	 * all TrueType Fonts.
	 *
	 * @param splashAsset
	 *     Initial splash screen texture to load
	 *
	 * @return AssetManager with only the splash texture loaded
	 */
	public static AssetManager initialize(Asset splashAsset) {
		return initialize(GdxArrays.newArray(splashAsset.getDescriptor()));
	}

	/**
	 * Initialize an AssetManager, time will be spent loading whatever initial assets are specified to be
	 * immediately loaded in the parameter. These will be immediately available. This will also generate
	 * all TrueType Fonts.
	 *
	 * @param splashTexture
	 *     Initial splash screen texture to load
	 *
	 * @return AssetManager with only the splash texture loaded
	 */
	public static AssetManager initialize(String splashTexture) {
		return initialize(GdxArrays.newArray(new AssetDescriptor<>(splashTexture, Texture.class)));
	}

	/**
	 * Initialize an AssetManager, no initial assets will be loaded. This will also generate
	 * all TrueType Fonts.
	 *
	 * @return AssetManager with only the splash texture loaded
	 */
	public static AssetManager initialize() {
		return initialize(GdxArrays.newArray(0));
	}

	private static FreetypeFontLoader.FreeTypeFontLoaderParameter getParams() {
		return new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	}

	private static void loadttf(AssetManager manager) {


		FreetypeFontLoader.FreeTypeFontLoaderParameter params = getParams();
		params.fontFileName = Assets.FNT_ZEKTON_GENERATOR.getPath();
		params.fontParameters.size = 18;
		manager.load(Assets.FNT_ZEKTON_18.getPath(), BitmapFont.class, params);

		params = getParams();
		params.fontFileName = Assets.FNT_CONTINUUM_GENERATOR.getPath();
		params.fontParameters.size = 24;
		manager.load(Assets.FNT_CONTINUUM_24.getPath(), BitmapFont.class, params);
	}

	/**
	 * Attempts to load all Assets according to the provided AssetDescriptors. All descriptors are enqueued and then
	 * asynchronously loads assets using {@link AssetManager#update()}, after each call the provided {@link FloatConsumer}
	 * is called with the current progress of loading provided by {@link AssetManager#getProgress()} and then yields to
	 * the CPU.
	 *
	 * @param manager
	 *     AssetManager to load assets with
	 * @param descriptors
	 *     List of {@link AssetDescriptor descriptors} to load
	 * @param loadAction
	 *     Action after every manager update.
	 */
	public static void ensureLoadAssets(AssetManager manager, Iterable<AssetDescriptor> descriptors, FloatConsumer loadAction) {
		for (AssetDescriptor desc : descriptors)
			manager.load(desc);

		while (!manager.update()) {
			loadAction.consume(manager.getProgress());
			ThreadUtils.yield();
		}
	}

	/**
	 * Attempts to load all Assets according to the provided AssetDescriptors. All descriptors are enqueued and then
	 * asynchronously loads assets using {@link AssetManager#update()}, after each call the provided {@link FloatConsumer}
	 * is called with the current progress of loading provided by {@link AssetManager#getProgress()} and then yields to
	 * the CPU.
	 *
	 * @param manager
	 *     AssetManager to load assets with
	 * @param assets
	 *     List of {@link Asset assets} to load
	 * @param loadAction
	 *     Action after every manager update.
	 */
	public static void ensureLoadAssets2(AssetManager manager, Iterable<Asset> assets, FloatConsumer loadAction) {
		ensureLoadAssets(manager, Iterables.transform(assets, Asset::getDescriptor), loadAction);
	}

	/**
	 * Attempts to unload the desired references. Partial completion may occur. <br />
	 * <br />
	 * If, according to LibGDX, there is more than one reference in the
	 * {@link AssetManager#getReferenceCount(String) reference count} then the unloading fails. This is also
	 * what {@link AssetManager#unload(String)} checks before unloading.
	 *
	 * @param manager
	 *     Asset Manager to unload assets from
	 * @param assets
	 *     A List of paths of the assets to unload
	 *
	 * @return Set of all asset names that were not freed.
	 */
	public static ObjectSet<String> unloadAssets(AssetManager manager, Iterable<String> assets) {

		final ObjectSet<String> failures = GdxSets.newSet();

		for (String s : assets)
			if (manager.getReferenceCount(s) <= 1)
				manager.unload(s);
			else
				failures.add(s);

		return failures;
	}

	/**
	 * Create an animation strip (an array of {@link TextureRegion TextureRegions}) from a base texture.
	 *
	 * @param t
	 *     Base texture
	 * @param n
	 *     Number of frames to grab
	 * @param r
	 *     Row to start on
	 * @param c
	 *     Column to start on
	 * @param width
	 *     Width of each frame
	 * @param height
	 *     Height of each frame
	 * @param frameTransform
	 *     Function that takes a TextureRegion and creates a transformed version
	 *
	 * @return Animation strip with n frames
	 *
	 * @throws IllegalStateException
	 *     if n frames could not be grabbed
	 */
	public static TextureRegion[] getAnimationStrip(Texture t, int n, int r, int c, int width, int height, Function<TextureRegion, TextureRegion> frameTransform) {
		TextureRegion[][] tiles = TextureRegion.split(t, width, height);
		TextureRegion[] strip = new TextureRegion[n];

		int i = 0;
		striploop:
		for (int row = r; r < tiles.length; row++)
			for (int col = c; c < tiles[0].length; col++) {
				strip[i++] = frameTransform.apply(tiles[row][col]);
				if (i == n)
					break striploop;
			}

		if (i != n)
			throw new IllegalStateException("Could not assemble strip of requested size!");

		return strip;
	}

	/**
	 * Create an animation strip (an array of {@link TextureRegion TextureRegions}) from a base texture.
	 *
	 * @param t
	 *     Base texture
	 * @param n
	 *     Number of frames to grab
	 * @param r
	 *     Row to start on
	 * @param c
	 *     Column to start on
	 * @param width
	 *     Width of each frame
	 * @param height
	 *     Height of each frame
	 *
	 * @return Animation strip with n frames
	 *
	 * @throws IllegalStateException
	 *     if n frames could not be grabbed
	 */
	public static TextureRegion[] getAnimationStrip(Texture t, int n, int r, int c, int width, int height) {
		return getAnimationStrip(t, n, r, c, width, height, Functions.identity());
	}

	/**
	 * Create an animation strip (an array of {@link TextureRegion TextureRegions}) from a base texture. It is
	 * assumed the frames start at the top left of the texture.
	 *
	 * @param t
	 *     Base texture
	 * @param n
	 *     Number of frames to grab
	 * @param width
	 *     Width of each frame
	 * @param height
	 *     Height of each frame
	 * @param frameTransform
	 *     Function that takes a TextureRegion and creates a transformed version
	 *
	 * @return Animation strip with n frames
	 *
	 * @throws IllegalStateException
	 *     if n frames could not be grabbed
	 */
	public static TextureRegion[] getAnimationStrip(Texture t, int n, int width, int height, Function<TextureRegion, TextureRegion> frameTransform) {
		return getAnimationStrip(t, n, 0, 0, width, height, frameTransform);
	}

	/**
	 * Create an animation strip (an array of {@link TextureRegion TextureRegions}) from a base texture. It is
	 * assumed the frames start at the top left of the texture.
	 *
	 * @param t
	 *     Base texture
	 * @param n
	 *     Number of frames to grab
	 * @param width
	 *     Width of each frame
	 * @param height
	 *     Height of each frame
	 *
	 * @return Animation strip with n frames
	 *
	 * @throws IllegalStateException
	 *     if n frames could not be grabbed
	 */
	public static TextureRegion[] getAnimationStrip(Texture t, int n, int width, int height) {
		return getAnimationStrip(t, n, width, height, Functions.identity());
	}

	/**
	 * Given a hexcode as a string, returns the appropriate LibGDX Color.
	 *
	 * @param hex
	 *     Hex code for RGB color
	 * @param alpha
	 *     Alpha value between 0 and 1
	 *
	 * @return LibGDX Color represented by the hex code and alpha value
	 */
	public static Color hex(String hex, float alpha) {
		return hex(Integer.parseInt(hex, 16), MathUtils.clamp(alpha, 0.0f, 1.0f));
	}

	/**
	 * Given a hexcode as a string, returns the appropriate LibGDX Color.
	 *
	 * @param hexa
	 *     Hex code for RGBA color
	 *
	 * @return LibGDX Color represented by the hex code w/ alpha value
	 */
	public static Color hex(String hexa) {
		int hexValue = Integer.parseInt(hexa, 16);

		if (hexValue <= 0xFFFFFF)
			hexValue <<= 8;

		return hex(hexValue);
	}

	/**
	 * Converts integer to separate rgb or rgba components (based on size). Each byte in the integer represents a
	 * component between 0 and 255.
	 * <p>
	 * There is a loss of precision when converted to a float between 0 and 1.
	 *
	 * @param hex
	 *     24-bit rgb or 32-bit rgba bit pattern
	 *
	 * @return LibGDX Color
	 */
	public static Color hex(int hex) {

		int red, green, blue, alpha = 255;

		if (hex > 0xFFFFFF) {
			red = ByteMath.getByte(hex, 3);
			green = ByteMath.getByte(hex, 2);
			blue = ByteMath.getByte(hex, 1);
			alpha = ByteMath.getByte(hex, 0);
		} else {
			red = ByteMath.getByte(hex, 2);
			green = ByteMath.getByte(hex, 1);
			blue = ByteMath.getByte(hex, 0);
		}

		return new Color(red / 255.f, green / 255.f, blue / 255.f, alpha / 255.f);
	}

	/**
	 * Takes a bit pattern representing an RGB value where the rightmost 3 bytes each represent one component. There
	 * is some loss of precision when the RGB components are converted to a value between 0 and 1. The alpha value
	 * is left unchanged.
	 *
	 * @param hex
	 *     24-bit bit pattern for RGB color
	 * @param alpha
	 *     Alpha value between 0 and 1
	 *
	 * @return LibGDX Color
	 */
	public static Color hex(int hex, float alpha) {

		int red = ByteMath.getByte(hex, 2);
		int green = ByteMath.getByte(hex, 1);
		int blue = ByteMath.getByte(hex, 0);

		return new Color(red / 255.f, green / 255.f, blue / 255.f, alpha);
	}
}
