package edu.rit.wic.lasers.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.github.czyzby.kiwi.util.gdx.asset.AssetType;

/**
 * <p> A loadable and usable asset like a {@link com.badlogic.gdx.graphics.Texture
 * Texture}, {@link com.badlogic.gdx.graphics.g2d.BitmapFont BitmapFont}, {@link
 * com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator FreeType Font}, {@link
 * com.badlogic.gdx.audio.Sound Music}, {@link com.badlogic.gdx.audio.Music Music}, etc.
 * </p> <p> At the most basic level an Asset is represented by a path, a type (represented
 * as a {@link Class}), and asset type. These describe an {@link AssetDescriptor} as well.
 * </p>
 *
 * @author Matthew Crocco
 */
public interface Asset {

	/** @return location at which the resource should be present. */
	String getPath();

	/** @return class of the object that should be created using the loaded asset. */
	Class<?> getAssetClass();

	/** @return file handle created with the assets path. */
	FileHandle getFileHandle();

	/**
	 * @return type of the asset, used by AbstractAssetManager to filter assets that need
	 * to be loaded.
	 */
	AssetType getAssetType();

	/**
	 * @param withManager
	 * 	will schedule loading of this asset.
	 */
	void load(AssetManager withManager);

	/**
	 * @param fromManager
	 * 	has to contain the loaded asset.
	 *
	 * @return asset represented by this container.
	 */
	Object get(AssetManager fromManager);

	/**
	 * @param withType
	 * 	class of the loaded asset.
	 * @param fromManager
	 * 	has to contain the loaded asset.
	 * @param <Type>
	 * 	type of stored asset.
	 *
	 * @return asset represented by this container.
	 */
	<Type> Type get(Class<Type> withType, AssetManager fromManager);

	/**
	 * @return {@link AssetDescriptor} describing this particular {@link Asset}.
	 */
	AssetDescriptor<?> getDescriptor();

}


