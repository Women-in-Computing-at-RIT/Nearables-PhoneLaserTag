package edu.rit.wic.lasers.logging;

import com.orhanobut.logger.Logger;

/**
 * <p>
 * Debug {@link LightRay} using {@link AbstractLightRay} with
 * {@link AndroidBeamPriority} for {@link BeamPriority beam priorites} and
 * {@link edu.rit.wic.lasers.logging.AndroidBeamPriority.Defaults AndroidBeamPriority.Defaults}
 * {@link BeamPriorityDefaults defaults}.
 * </p>
 * <p>
 *     Uses {@link Logger} for pretty logging. Debug printing includes automatically
 *     determining Message Tag by the name of the class doing the logging.
 * </p>
 *
 * @author Matthew Crocco
 */
public class AndroidDebugLightRay extends AbstractLightRay {

	public AndroidDebugLightRay() {
		super(new AndroidBeamPriority.Defaults());
	}

	@Override
	protected String getTag() {
		String tag = super.getTag();

		if (tag != null)
			return tag;

		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
		return LightRayUtils.getCallerNameNoInner(stackTrace);
	}

	@Override
	protected void log(final BeamPriority priority, final String tag, final String message, final Throwable t) {
		if (message.length() < LightRayUtils.MAX_LOG_LENGTH)
			Logger.log(priority.toInt(), tag, message, t);
		else {
			Logger.i("Long Message (2 or More Messages) Follows:");
			LightRayUtils.processLongMessage(message, str -> Logger.log(priority.toInt(), tag, str, t));
		}
	}

}
