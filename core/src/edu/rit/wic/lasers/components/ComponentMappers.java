package edu.rit.wic.lasers.components;

import com.badlogic.ashley.core.ComponentMapper;
import com.github.czyzby.kiwi.util.common.UtilitiesClass;

/**
 * Created by Matthew on 10/9/2016.
 */
public final class ComponentMappers extends UtilitiesClass {

	public static final ComponentMapper<TextureComponent> TEX_MAPPER = ComponentMapper.getFor(TextureComponent.class);
	public static final ComponentMapper<TransformComponent> TRANSFORM_MAPPER = ComponentMapper.getFor(TransformComponent.class);
	public static final ComponentMapper<MovementComponent> MOVEMENT_MAPPER = ComponentMapper.getFor(MovementComponent.class);
	public static final ComponentMapper<StateComponent> STATE_MAPPER = ComponentMapper.getFor(StateComponent.class);
	public static final ComponentMapper<AnimationComponent> ANIM_MAPPER = ComponentMapper.getFor(AnimationComponent.class);
	public static final ComponentMapper<BackgroundComponent> BG_MAPPER = ComponentMapper.getFor(BackgroundComponent.class);

}
