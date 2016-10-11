package edu.rit.wic.lasers.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import edu.rit.wic.lasers.components.ComponentMappers;
import edu.rit.wic.lasers.components.StateComponent;

/**
 * {@link IteratingSystem} to handle {@link Entity entities} that have state. The system
 * handles updating stateful time. Potentially, the system can also handle State Machine
 * transitions. Thus the entities require {@link StateComponent}.
 *
 * @author Matthew Crocco
 */
public class StateSystem extends IteratingSystem {

	private final ComponentMapper<StateComponent> stateMapper = ComponentMappers
		.STATE_MAPPER;

	public StateSystem() {
		super(Family.all(StateComponent.class).get());
	}

	@Override
	protected void processEntity(final Entity entity, final float deltaTime) {
		stateMapper.get(entity).time += deltaTime;
	}
}
