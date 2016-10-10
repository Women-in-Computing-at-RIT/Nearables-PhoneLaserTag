package edu.rit.wic.lasers.functional;

/**
 * Created by Matthew on 10/9/2016.
 */
@FunctionalInterface public interface Callback extends Runnable {

	default void run() {
		this.call();
	}

	void call();

}
