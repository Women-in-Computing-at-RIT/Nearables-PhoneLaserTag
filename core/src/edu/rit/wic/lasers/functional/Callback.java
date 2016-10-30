package edu.rit.wic.lasers.functional;

/**
 * Simple interface for a callback, essentially a more descriptive alternative to a {@link Runnable}.
 *
 * @author Matthew Crocco
 */
@FunctionalInterface
public interface Callback extends Runnable {

	default void run() {
		this.call();
	}

	void call();

}
