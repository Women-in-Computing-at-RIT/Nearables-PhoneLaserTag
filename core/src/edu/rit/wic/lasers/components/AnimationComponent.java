package edu.rit.wic.lasers.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;

/**
 * <p> {@link PoolableComponent Poolable component} allowing use of an {@link
 * com.badlogic.ashley.core.Entity} in an Animation system. Consists of an {@link IntMap}
 * to {@link Animation Animations}. </p>
 * <p>
 * <p> This is intended to be used with enumerated integers represnting each animation,
 * then mapping these integer keys to the respective animations. </p>
 *
 * @author Matthew Crocco
 */
public class AnimationComponent implements PoolableComponent {

	public final IntMap<Animation> animationMap = new IntMap<>();

	@Override
	public void reset() {
		animationMap.clear();
	}
}
