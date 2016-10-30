package edu.rit.wic.lasers.functional;

/**
 * <p> function that takes an int value and returns nothing. Can be applied in sequence using {@link
 * IntConsumer#andThen(PrimitiveConsumer)}. </p> <p> Acts exactly the same as {@link java8.util.function.Consumer} but
 * for int primitives. </p>
 *
 * @author Matthew Crocco
 */
public interface IntConsumer extends PrimitiveConsumer {

	@Override
	default void consume(Number input) {
		this.consume(input.intValue());
	}

	@Override
	default void accept(Number input) {
		this.accept(input.intValue());
	}

	@Override
	default IntConsumer andThen(PrimitiveConsumer after) {
		return input -> {
			this.accept(input);
			after.accept(input);
		};
	}

	default void consume(int input) {
		this.accept(input);
	}

	void accept(int input);

	default IntConsumer andThen(IntConsumer after) {
		return input -> {
			this.accept(input);
			after.accept(input);
		};
	}

}
