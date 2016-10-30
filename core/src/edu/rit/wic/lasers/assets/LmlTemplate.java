package edu.rit.wic.lasers.assets;

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.util.LmlUtilities;

/**
 * Intended to just be a type alias for an {@link ImmutableArray} of {@link Actor Actors} which provides a view into
 * the {@link Array} provided by
 * {@link com.github.czyzby.lml.parser.LmlParser#parseTemplate(FileHandle) LmlParser#parseTemplate(FileHandle)} which
 * is used by {@link LmlTemplateLoader}.
 */
public class LmlTemplate extends ImmutableArray<Actor> {

	LmlTemplate(Array<Actor> template) {
		super(template);
	}

	public void fillStage(final Stage stage) {
		LmlUtilities.appendActorsToStage(stage, this);
	}

}
