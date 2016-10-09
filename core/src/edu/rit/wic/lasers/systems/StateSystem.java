package edu.rit.wic.lasers.systems;

import edu.rit.wic.lasers.components.ComponentMappers;
import edu.rit.wic.lasers.components.StateComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * Created by Matthew on 10/9/2016.
 */
public class StateSystem extends IteratingSystem {

	private final ComponentMapper<StateComponent> stateMapper = ComponentMappers.STATE_MAPPER;

	public StateSystem() {
		super(Family.all(StateComponent.class).get());
	}

	@Override protected void processEntity(final Entity entity, final float deltaTime) {
		stateMapper.get(entity).time += deltaTime;
	}
}
