package edu.rit.wic.lasers.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;

import edu.rit.wic.lasers.components.AnimationComponent;
import edu.rit.wic.lasers.components.ComponentMappers;
import edu.rit.wic.lasers.components.StateComponent;
import edu.rit.wic.lasers.components.TextureComponent;

/**
 * {@link IteratingSystem} to manage animated {@link Entity entities}. Those being
 * entities that have a {@link StateComponent}, {@link AnimationComponent} and {@link
 * TextureComponent}. This system does not manage animation time but depends on the state
 * time in the state component.
 *
 * @author Matthew Crocco
 */
public class AnimationSystem extends IteratingSystem {

	private final ComponentMapper<StateComponent> stateMapper = ComponentMappers
		.STATE_MAPPER;
	private final ComponentMapper<AnimationComponent> animMapper = ComponentMappers
		.ANIM_MAPPER;
	private final ComponentMapper<TextureComponent> texMapper = ComponentMappers
		.TEX_MAPPER;

	public AnimationSystem() {
		super(Family.all(StateComponent.class, AnimationComponent.class).get());
	}

	@Override
	protected void processEntity(final Entity entity, final float deltaTime) {
		TextureComponent tex = texMapper.get(entity);
		StateComponent state = stateMapper.get(entity);
		AnimationComponent anim = animMapper.get(entity);

		Animation animation = anim.animationMap.get(state.get());
		if (animation != null) {
			tex.texture = animation.getKeyFrame(state.time);
		}
	}
}
