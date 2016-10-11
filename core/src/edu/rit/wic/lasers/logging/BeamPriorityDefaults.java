package edu.rit.wic.lasers.logging;

/**
 * <p>
 * Since LibGDX logging may require overlap with otherwise inaccessible parts of code
 * (bridging to Android or Desktop from Core), there is no set defaults since on
 * Android you may use the Android Log utility and on desktop you may use a SLF4J logger.
 * </p>
 * <p>
 *     Thus we provide what defaults we need to know about based on our basic
 *     priorities and get the details we know we need using {@link BeamPriority}. This
 *     is only necessary if using a subclass of {@link AbstractLightRay} for logging.
 * </p>
 *
 * @author Matthew Crocco
 */
public interface BeamPriorityDefaults {

	/**
	 * @return Default {@link BeamPriority} most similar to an Info priority
	 */
	BeamPriority forInfo();

	/**
	 * @return Default {@link BeamPriority} most similar to a Debug priority
	 */
	BeamPriority forDebug();

	/**
	 * @return Default {@link BeamPriority} most similar to a Warning priority
	 */
	BeamPriority forWarn();

	/**
	 * @return Default {@link BeamPriority} most similar to a Verbose priority
	 */
	BeamPriority forVerbose();

	/**
	 * @return Default {@link BeamPriority} most similar to an Error priority
	 */
	BeamPriority forError();

	/**
	 * @return Default {@link BeamPriority} most similar to a WTF priority
	 */
	BeamPriority forWtf();

}
