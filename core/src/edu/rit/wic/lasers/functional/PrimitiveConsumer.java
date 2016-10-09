package edu.rit.wic.lasers.functional;

/**
 * Created by Matthew on 10/8/2016.
 */
public interface PrimitiveConsumer extends Consumer<Number> {

	@Override
	void accept(Number input);

	@Override
	void consume(Number input);

	PrimitiveConsumer andThen(PrimitiveConsumer after);

}
