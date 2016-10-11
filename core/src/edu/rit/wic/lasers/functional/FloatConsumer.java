package edu.rit.wic.lasers.functional;


/**
 * <p> function that takes a float value and returns nothing. Can be applied in sequence
 * using {@link FloatConsumer#andThen(PrimitiveConsumer)}.
 * </p>
 * <p>
 *     Acts exactly the same as {@link Consumer} but for float primitives.
 * </p>
 *
 * @author  Matthew Crocco
 */
@FunctionalInterface
public interface FloatConsumer extends PrimitiveConsumer {

	@Override
	default void consume(Number input) {
		this.consume(input.floatValue());
	}

	@Override
	default void accept(Number input) {
		this.accept(input.floatValue());
	}

	void accept(float input);

	@Override
	default FloatConsumer andThen(PrimitiveConsumer after) {
		return (input) -> {
			this.accept(input);
			after.accept(input);
		};
	}

	default void consume(float input) {
		this.accept(input);
	}

	default FloatConsumer andThen(FloatConsumer after) {
		return (input) -> {
			this.accept(input);
			after.accept(input);
		};
	}
}
