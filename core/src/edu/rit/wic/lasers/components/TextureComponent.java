package edu.rit.wic.lasers.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * <p> {@link Component Component} for {@link com.badlogic.ashley.core.Entity entities} with a visible texture that can
 * be rendered via {@link com.badlogic.gdx.graphics.g2d.SpriteBatch}. </p>
 *
 * @author Matthew Crocco
 */
public class TextureComponent implements Component {

	public TextureRegion texture = null;

}
