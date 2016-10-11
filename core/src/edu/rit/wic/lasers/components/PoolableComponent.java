package edu.rit.wic.lasers.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Extending interface for {@link Component} that also makes the component {@link
 * com.badlogic.gdx.utils.Pool.Poolable}. Useful when using an {@link
 * com.badlogic.ashley.core.PooledEngine}.
 *
 * @author Matthew Crocco
 */
public interface PoolableComponent extends Pool.Poolable, Component {}
