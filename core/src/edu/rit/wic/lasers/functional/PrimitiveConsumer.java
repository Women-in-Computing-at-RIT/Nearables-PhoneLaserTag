package edu.rit.wic.lasers.functional;

/**
 * <p> function that takes a numerical value and returns nothing. Can be applied in
 * sequence using {@link PrimitiveConsumer#andThen(PrimitiveConsumer)}. This is a base
 * interface for all other primitive consumers like {@link FloatConsumer}. Supports any
 * arbitrary {@link Number} input but is ultimately converted to the needed value using
 * methods like {@link Number#floatValue()}.
 * </p>
 * <p>
 *     Acts exactly the same as {@link Consumer} but for primitives.
 * </p>
 *
 * @author  Matthew Crocco
 */
public interface PrimitiveConsumer extends Consumer<Number> {

	@Override
	void consume(Number input);

	@Override
	void accept(Number input);

	/**
	 * Allows to sequentially execute {@link PrimitiveConsumer primitive consumers}.
	 *
	 * @param after
	 *  Next consumer to execute
	 * @return New {@link PrimitiveConsumer} that executes this consumer and the given
	 * consumer in sequence.
	 */
	PrimitiveConsumer andThen(PrimitiveConsumer after);

}
