package edu.rit.wic.lasers.logging;

import com.orhanobut.logger.Logger;

/**
 * Created by Matthew on 10/10/2016.
 */
public class AndroidDebugLightRay extends AbstractLightRay {

	public AndroidDebugLightRay() {
		super(new AndroidBeamPriority.Defaults());
	}

	@Override protected void log(final BeamPriority priority, final String tag, final String message, final Throwable t) {
		if(message.length() < LightRayUtils.MAX_LOG_LENGTH)
			Logger.log(priority.toInt(), tag, message, t);
		else {
			Logger.i("Long Message (2 or More Messages) Follows:");
			LightRayUtils.processLongMessage(message, str -> Logger.log(priority.toInt(), tag, str, t));
		}
	}

	@Override protected String getTag() {
		String tag = super.getTag();

		if(tag != null)
			return tag;

		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
		return LightRayUtils.getCallerNameNoInner(stackTrace);
	}

}
