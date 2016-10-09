package edu.rit.wic.lasers.functional;

/**
 * Created by Matthew on 10/8/2016.
 */
@FunctionalInterface
public interface Consumer<T> extends com.annimon.stream.function.Consumer<T>{

	@Override
	void accept(T input);

	default void consume(T input) {
		this.accept(input);
	}

	default Consumer<T> andThen(Consumer<T> after) {
		return in -> { this.accept(in); after.accept(in);};
	}

	default Consumer<T> andThen(com.annimon.stream.function.Consumer<T> after) {
		return in -> { this.accept(in); after.accept(in);};
	}

}
