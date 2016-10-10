package edu.rit.wic.lasers.logging;

import java.util.Comparator;

/**
 * Created by Matthew on 10/10/2016.
 */
public interface BeamPriority extends Comparator<BeamPriority> {

	String toMessageTag();
	int toInt();

}
