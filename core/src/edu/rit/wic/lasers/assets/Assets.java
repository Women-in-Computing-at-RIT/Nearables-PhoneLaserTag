package edu.rit.wic.lasers.assets;

import static edu.rit.wic.lasers.assets.AssetType.FONT_GENERATOR;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.github.czyzby.kiwi.util.gdx.collection.GdxMaps;
import com.github.czyzby.kiwi.util.gdx.collection.GdxSets;
import com.github.czyzby.kiwi.util.gdx.collection.immutable.ImmutableObjectSet;

/**
 * <p> An enumeration of all available {@link Asset assets} with known paths, {@link Class
 * classes}, and {@link AssetType types}. </p> <p> <p> Assets instances can be loaded and
 * gotten using a provided {@link AssetManager}, the path is already resolved using {@link
 * InternalFileHandleResolver} or a provided {@link FileHandleResolver}. </p>
 *
 * @author Matthew Crocco
 */
public enum Assets implements Asset {

	SPLASH_IMAGE("badlogic.jpg", Texture.class, AssetType.GRAPHICS),

	FNT_ZEKTON_GENERATOR("fonts/zekton.ttf", FreeTypeFontGenerator.class,
		FONT_GENERATOR),
	FNT_CONTINUUM_GENERATOR("fonts/contb.ttf", FreeTypeFontGenerator.class,
		FONT_GENERATOR),

	FNT_ZEKTON_18("fonts/zekton18.ttf", BitmapFont.class, AssetType.FONT),
	FNT_CONTINUUM_24("fonts/contb24.ttf", BitmapFont.class, AssetType.FONT),

	I18N_APP_BUNDLE("bundles/app.properties", I18NBundle.class, AssetType.LANGUAGE);

	/** Maps AssetTypes to sets of Assets */
	private static final ObjectMap<AssetType, ObjectSet<Asset>> typeToAssets = GdxMaps
		.newObjectMap();

	static {
		// Add each asset to it's respective AssetType set
		for (Assets asset : Assets.values()) {
			final AssetType type = asset.getAssetType();
			final ObjectSet<Asset> assetSet = typeToAssets.get(type);

			if (assetSet == null)
				typeToAssets.put(type, GdxSets.newSet(asset));
			else
				assetSet.add(asset);
		}
	}

	private final FileHandle handle;
	private final AssetDescriptor<?> descriptor;
	private final AssetType assetType;

	/**
	 * Creates an {@link Assets} instance with all the information necessary to be an
	 * {@link Asset} instance as well. The path is resolved to a {@link FileHandle} using
	 * the provided {@link FileHandleResolver}.
	 *
	 * @param descriptor
	 * 	{@link AssetDescriptor} instance for this particular Asset
	 * @param type
	 * 	{@link AssetType} instance most closely representing this Asset's usage
	 * @param resolver
	 * 	{@link FileHandleResolver} to be used to resolve the path provided by the
	 * 	descriptor, to a file.
	 * @param <T>
	 * 	Asset Class Type ({@link Texture}, {@link BitmapFont}, etc.)
	 */
	<T> Assets(AssetDescriptor<T> descriptor, AssetType type,
	           FileHandleResolver resolver) {
		this.handle = descriptor.file == null ?
		              resolver.resolve(descriptor.fileName) :
		              descriptor.file;
		this.descriptor = descriptor;
		this.assetType = type;
	}

	/**
	 * Creates an {@link Assets} instance with all the information necessary to be an
	 * {@link Asset} instance as well. The path is resolved to a {@link FileHandle} using
	 * the provided {@link FileHandleResolver}.
	 *
	 * @param path
	 * 	Path relative to the android <tt>res</tt> directory that can be used to retrieve
	 * 	asset
	 * @param assetClass
	 * 	{@link Class} representing the type of asset ({@link Texture Texture.class},
	 * 	{@link BitmapFont BitmapFont.class}, etc.
	 * @param parameters
	 * 	{@link AssetLoaderParameters Parameters} for the {@link
	 * 	com.badlogic.gdx.assets.loaders.AssetLoader loader} when loading asset.
	 * @param type
	 * 	{@link AssetType} instance most closely representing this Asset's usage
	 * @param resolver
	 * 	{@link FileHandleResolver} to be used to resolve the path provided by the
	 * 	descriptor, to a file.
	 * @param <T>
	 * 	Asset Class Type ({@link Texture}, {@link BitmapFont}, etc.)
	 */
	<T> Assets(String path, Class<T> assetClass, AssetLoaderParameters<T> parameters,
	           AssetType type, FileHandleResolver resolver) {
		this(new AssetDescriptor<>(resolver.resolve(path), assetClass, parameters),
			type, resolver);
	}

	/**
	 * Creates an {@link Assets} instance with all the information necessary to be an
	 * {@link Asset} instance as well. The path is resolved to a {@link FileHandle} using
	 * the provided {@link FileHandleResolver}.
	 *
	 * @param path
	 * 	Path relative to the android <tt>res</tt> directory that can be used to retrieve
	 * 	asset
	 * @param assetClass
	 * 	{@link Class} representing the type of asset ({@link Texture Texture.class},
	 * 	{@link BitmapFont BitmapFont.class}, etc.
	 * @param type
	 * 	{@link AssetType} instance most closely representing this Asset's usage
	 * @param resolver
	 * 	{@link FileHandleResolver} to be used to resolve the path provided by the
	 * 	descriptor, to a file.
	 * @param <T>
	 * 	Asset Class Type ({@link Texture}, {@link BitmapFont}, etc.)
	 */
	<T> Assets(String path, Class<T> assetClass, AssetType type,
	           FileHandleResolver resolver) {
		this(new AssetDescriptor<>(resolver.resolve(path), assetClass), type, resolver);
	}

	/**
	 * Creates an {@link Assets} instance with all the information necessary to be an
	 * {@link Asset} instance as well. The path is resolved to a {@link FileHandle} using
	 * the provided {@link FileHandleResolver}.
	 *
	 * @param path
	 * 	Path relative to the android <tt>res</tt> directory that can be used to retrieve
	 * 	asset
	 * @param assetClass
	 * 	{@link Class} representing the type of asset ({@link Texture Texture.class},
	 * 	{@link BitmapFont BitmapFont.class}, etc.
	 * @param parameters
	 * 	{@link AssetLoaderParameters Parameters} for the {@link
	 * 	com.badlogic.gdx.assets.loaders.AssetLoader loader} when loading asset.
	 * @param type
	 * 	{@link AssetType} instance most closely representing this Asset's usage
	 * @param <T>
	 * 	Asset Class Type ({@link Texture}, {@link BitmapFont}, etc.)
	 */
	<T> Assets(String path, Class<T> assetClass, AssetLoaderParameters<T> parameters,
	           AssetType type) {
		this(new AssetDescriptor<>(path, assetClass, parameters), type);
	}

	/**
	 * Creates an {@link Assets} instance with all the information necessary to be an
	 * {@link Asset} instance as well. The path is resolved to a {@link FileHandle} using
	 * the provided {@link FileHandleResolver}.
	 *
	 * @param descriptor
	 * 	{@link AssetDescriptor} instance for this particular Asset
	 * @param type
	 * 	{@link AssetType} instance most closely representing this Asset's usage
	 * @param <T>
	 * 	Asset Class Type ({@link Texture}, {@link BitmapFont}, etc.)
	 */
	<T> Assets(AssetDescriptor<T> descriptor, AssetType type) {
		this(descriptor, type, new InternalFileHandleResolver());
	}

	/**
	 * Creates an {@link Assets} instance with all the information necessary to be an
	 * {@link Asset} instance as well. The path is resolved to a {@link FileHandle} using
	 * the provided {@link FileHandleResolver}.
	 *
	 * @param path
	 * 	Path relative to the android <tt>res</tt> directory that can be used to retrieve
	 * 	asset
	 * @param assetClass
	 * 	{@link Class} representing the type of asset ({@link Texture Texture.class},
	 * 	{@link BitmapFont BitmapFont.class}, etc.
	 * @param type
	 * 	{@link AssetType} instance most closely representing this Asset's usage
	 * @param <T>
	 * 	Asset Class Type ({@link Texture}, {@link BitmapFont}, etc.)
	 */
	<T> Assets(String path, Class<T> assetClass, AssetType type) {
		this(new AssetDescriptor<>(path, assetClass), type);
	}

	@Override
	public String getPath() {
		return this.handle.path();
	}

	/**
	 * Returns an {@link ImmutableObjectSet} representing all {@link Asset assets} known
	 * to this class.
	 *
	 * @param type
	 * 	{@link AssetType} type to filter for.
	 *
	 * @return {@link ImmutableObjectSet} of {@link Assets assets} of the given type.
	 */
	public static ImmutableObjectSet<Asset> getAssetsOfType(AssetType type) {
		if (typeToAssets.containsKey(type))
			return ImmutableObjectSet.of();
		else
			return GdxSets.toImmutable(typeToAssets.get(type));
	}

	/**
	 * Returns an {@link ImmutableObjectSet} of all {@link Asset assets}, known to this
	 * class, which have an {@link Class asset class} equal to the supplied asset class.
	 * If <tt>subclasses</tt> is true, the set will also consist of all Assets whose
	 * asset
	 * class is a subclass of the given asset class.
	 *
	 * @param assetClass
	 * 	{@link Class} to filter for
	 * @param subclasses
	 * 	Whether or not to include subclasses of the given asset class
	 *
	 * @return {@link ImmutableObjectSet} of assets whose asset class matches the given
	 * asset class, optionally including subclasses.
	 */
	public static ImmutableObjectSet<Asset> getAssetsOfClass(Class<?> assetClass,
	                                                         boolean subclasses) {
		ObjectSet<Asset> assets = GdxSets.newSet();

		for (Asset a : Assets.values())
			if (subclasses && assetClass.isAssignableFrom(a.getAssetClass()))
				assets.add(a);
			else if (assetClass == a.getAssetClass())
				assets.add(a);

		return GdxSets.toImmutable(assets);
	}

	/**
	 * Returns an {@link ImmutableObjectSet} of all {@link Asset assets}, known to this
	 * class, which have an {@link Class asset class} equal to the supplied asset class.
	 * Subclasses are not included.
	 *
	 * @param assetClass
	 * 	{@link Class} to filter for
	 *
	 * @return {@link ImmutableObjectSet} of assets whose asset class matches the given
	 * asset class, not including subclasses.
	 */
	public static ImmutableObjectSet<Asset> getAssetOfClass(Class<?> assetClass) {
		return getAssetsOfClass(assetClass, false);
	}

	@Override
	public Class<?> getAssetClass() {
		return this.descriptor.type;
	}

	@Override
	public FileHandle getFileHandle() {
		return this.handle;
	}

	@Override
	public AssetType getAssetType() {
		return this.assetType;
	}

	@Override
	public void load(final AssetManager withManager) {
		withManager.load(this.descriptor);
	}

	@Override
	public Object get(final AssetManager fromManager) {
		return fromManager.get(this.descriptor);
	}

	@Override
	public <T> T get(final Class<T> withType, final AssetManager fromManager) {
		return fromManager.get(this.getPath(), withType);
	}

	@Override
	public AssetDescriptor<?> getDescriptor() {
		return this.descriptor;
	}



}

