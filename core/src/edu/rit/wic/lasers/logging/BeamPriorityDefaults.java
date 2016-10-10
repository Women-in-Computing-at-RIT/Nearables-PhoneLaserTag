package edu.rit.wic.lasers.logging;

/**
 * Created by Matthew on 10/10/2016.
 */
public interface BeamPriorityDefaults {

	BeamPriority forInfo();
	BeamPriority forDebug();
	BeamPriority forWarn();
	BeamPriority forVerbose();
	BeamPriority forError();
	BeamPriority forWtf();

}
