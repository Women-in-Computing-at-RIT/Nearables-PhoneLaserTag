package edu.rit.wic.lasers.functional;

/**
 * <p> function that takes a value and returns nothing. Can be applied in sequence using
 * {@link Consumer#andThen(Consumer)}. Fully supports use in conjunction with
 * {@link com.annimon.stream.function.Consumer Lightweight Streaming API Consumers}.
 * </p>
 *
 * @author  Matthew Crocco
 * @param <T> Type of object that can be consumed/accepted.
 */
@FunctionalInterface
public interface Consumer<T> extends com.annimon.stream.function.Consumer<T> {

	/**
	 * Same as {@link #accept(Object)}}.
	 *
	 * @param input
	 *  Object to consume
	 */
	default void consume(T input) {
		this.accept(input);
	}

	@Override
	void accept(T input);

	default Consumer<T> andThen(Consumer<T> after) {
		return in -> {
			this.accept(in);
			after.accept(in);
		};
	}

}
