package edu.rit.wic.lasers.functional;

/**
 * Created by Matthew on 10/8/2016.
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
