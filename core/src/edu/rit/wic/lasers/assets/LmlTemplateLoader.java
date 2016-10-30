package edu.rit.wic.lasers.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.parser.LmlParser;

/**
 * Loads an {@link LmlTemplate} using a provided {@link LmlParser} from the given file if possible. The actual result
 * is an {@link Array} of {@link Actor} but LmlTemplate wraps this raw array a little nicer and is also encapsulating
 * the given Array as an {@link com.badlogic.ashley.utils.ImmutableArray ImmutableArray}.
 *
 * @author Matthew Crocco
 */
public class LmlTemplateLoader extends SynchronousAssetLoader<LmlTemplate, LmlTemplateParameter> {

	private final LmlParser parser;

	public LmlTemplateLoader(final FileHandleResolver resolver, final LmlParser parser) {
		super(resolver);
		this.parser = parser;
	}

	public LmlTemplateLoader(final LmlParser parser) {
		this(new InternalFileHandleResolver(), parser);
	}

	@Override
	public LmlTemplate load(final AssetManager assetManager, final String fileName,
	                        final FileHandle file, final LmlTemplateParameter
		                            parameter) {
		final Array<Actor> templateAsArray;

		if(file != null && file.exists())
			templateAsArray = this.parser.parseTemplate(file);
		else
			templateAsArray = this.parser.parseTemplate(this.resolve(fileName));

		return new LmlTemplate(templateAsArray);
	}

	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName,
	                                              final FileHandle file,
	                                              final LmlTemplateParameter parameter) {
		return null;
	}
}
