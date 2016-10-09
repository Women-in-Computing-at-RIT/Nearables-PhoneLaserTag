package edu.rit.wic.lasers.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.OrderedMap;
import com.github.czyzby.kiwi.util.gdx.collection.GdxMaps;

/**
 * Created by Matthew on 10/9/2016.
 */
public class AnimationComponent implements PoolableComponent {

	public final OrderedMap<Integer, Animation> animationMap = GdxMaps.newOrderedMap();

	@Override public void reset() {
		animationMap.clear();
	}
}
