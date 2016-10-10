package edu.rit.wic.lasers.assets;

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
 * Created by Matthew on 10/9/2016.
 */
public enum Assets implements Asset {

	SPLASH_IMAGE("badlogic.jpg", Texture.class, AssetType.GRAPHICS),

	FNT_ZEKTON_GENERATOR("fonts/zekton.ttf", FreeTypeFontGenerator.class, AssetType.FONT_GENERATOR),
	FNT_CONTINUUM_GENERATOR("fonts/contb.ttf", FreeTypeFontGenerator.class, AssetType.FONT_GENERATOR),

	FNT_ZEKTON_18("fonts/zekton18.ttf", BitmapFont.class, AssetType.FONT),
	FNT_CONTINUUM_24("fonts/contb24.ttf", BitmapFont.class, AssetType.FONT),

	I18N_APP_BUNDLE("bundles/app.properties", I18NBundle.class, AssetType.LANGUAGE);

	private static final ObjectMap<AssetType, ObjectSet<Asset>> typeToAssets = GdxMaps.newObjectMap();

	static {
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

	<T> Assets(String path, Class<T> assetClass, AssetLoaderParameters<T> parameters, AssetType type, FileHandleResolver resolver) {
		this(new AssetDescriptor<>(resolver.resolve(path), assetClass, parameters), type, resolver);
	}

	<T> Assets(AssetDescriptor<T> descriptor, AssetType type, FileHandleResolver resolver) {
		this.handle = resolver.resolve(descriptor.fileName);
		this.descriptor = descriptor;
		this.assetType = type;
	}

	<T> Assets(String path, Class<T> assetClass, AssetType type, FileHandleResolver resolver) {
		this(new AssetDescriptor<>(resolver.resolve(path), assetClass), type, resolver);
	}

	<T> Assets(String path, Class<T> assetClass, AssetLoaderParameters<T> parameters, AssetType type) {
		this(new AssetDescriptor<>(path, assetClass, parameters), type);
	}

	<T> Assets(AssetDescriptor<T> descriptor, AssetType type) {
		this(descriptor, type, new InternalFileHandleResolver());
	}	@Override public String getPath() {
		return this.handle.path();
	}

	<T> Assets(String path, Class<T> assetClass, AssetType type) {
		this(new AssetDescriptor<>(path, assetClass), type);
	}	@Override public Class<?> getAssetClass() {
		return this.descriptor.type;
	}

	public static ImmutableObjectSet<Asset> getAssetsOfType(AssetType type) {
		if (typeToAssets.containsKey(type))
			return ImmutableObjectSet.of();
		else
			return GdxSets.toImmutable(typeToAssets.get(type));
	}	@Override public FileHandle getFileHandle() {
		return this.handle;
	}

	@Override public AssetType getAssetType() {
		return this.assetType;
	}

	@Override public void load(final AssetManager withManager) {
		withManager.load(this.descriptor);
	}

	@Override public Object get(final AssetManager fromManager) {
		return fromManager.get(this.descriptor);
	}

	@Override public <T> T get(final Class<T> withType, final AssetManager fromManager) {
		return fromManager.get(this.getPath(), withType);
	}

	@Override public AssetDescriptor<?> getDescriptor() {
		return this.descriptor;
	}







}

