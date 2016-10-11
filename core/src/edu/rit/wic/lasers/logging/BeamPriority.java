package edu.rit.wic.lasers.logging;

import java.util.Comparator;

/**
 * Basics of a {@link Beam} logging priority object. A log representation and integer
 * value is all that is needed.
 *
 * @author Matthew Crocco
 */
public interface BeamPriority extends Comparator<BeamPriority> {

	String toMessageTag();

	int toInt();

}
