package edu.rit.wic.lasers.logging;

/**
 * Created by Matthew on 10/10/2016.
 */
public class DebugLightRay extends AbstractLightRay {

	@Override protected String getTag() {
		String tag = super.getTag();

		if(tag != null)
			return tag;

		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
		return LightRayUtils.getCallerNameNoInner(stackTrace);
	}

	@Override protected void log(final BeamPriority priority, final String tag, final String message, final Throwable t) {

	}
}
