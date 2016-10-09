package wic.rit.edu.lasers.systems;

import wic.rit.edu.lasers.components.AnimationComponent;
import wic.rit.edu.lasers.components.ComponentMappers;
import wic.rit.edu.lasers.components.StateComponent;
import wic.rit.edu.lasers.components.TextureComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by Matthew on 10/9/2016.
 */
public class AnimationSystem extends IteratingSystem {

	private final ComponentMapper<StateComponent> stateMapper = ComponentMappers.STATE_MAPPER;
	private final ComponentMapper<AnimationComponent> animMapper = ComponentMappers.ANIM_MAPPER;
	private final ComponentMapper<TextureComponent> texMapper = ComponentMappers.TEX_MAPPER;

	public AnimationSystem() {
		super(Family.all(StateComponent.class, AnimationComponent.class).get());
	}

	@Override protected void processEntity(final Entity entity, final float deltaTime) {
		TextureComponent tex = texMapper.get(entity);
		StateComponent state = stateMapper.get(entity);
		AnimationComponent anim = animMapper.get(entity);

		Animation animation = anim.animationMap.get(state.get());
		if(animation != null) {
			tex.texture = animation.getKeyFrame(state.time);
		}
	}
}
