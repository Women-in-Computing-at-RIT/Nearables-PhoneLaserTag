package wic.rit.edu.lasers.functional;

/**
 * Created by Matthew on 10/8/2016.
 */
public interface IntConsumer extends PrimitiveConsumer {

	void accept(int input);

	default void consume(int input) {
		this.accept(input);
	}

	@Override default void accept(Number input) {
		this.accept(input.intValue());
	}

	@Override default void consume(Number input) {
		this.consume(input.intValue());
	}

	@Override default IntConsumer andThen(PrimitiveConsumer after) {
		return input -> {this.accept(input); after.accept(input);};
	}

	default IntConsumer andThen(IntConsumer after) {
		return input -> {this.accept(input); after.accept(input);};
	}

}
