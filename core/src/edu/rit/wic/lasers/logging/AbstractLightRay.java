package edu.rit.wic.lasers.logging;

/**
 * Created by Matthew on 10/10/2016.
 */
public abstract class AbstractLightRay implements LightRay {

	protected final BeamPriorityDefaults defaults;
	final ThreadLocal<String> logTag = new ThreadLocal<>();

	public AbstractLightRay(BeamPriorityDefaults defaults) {
		this.defaults = defaults;
	}

	protected String getTag() {
		String tag = logTag.get();
		if (tag != null)
			logTag.remove();
		return tag;
	}

	@Override
	public void setTag(final String tag) {
		this.logTag.set(tag);
	}

	@Override
	public void v(final String message, final Object... args) {
		prepareLog(defaults.forVerbose(), null, message, args);
	}

	@Override
	public void v(final Throwable t, final String message, final Object... args) {
		prepareLog(defaults.forVerbose(), t, message, args);
	}

	@Override
	public void v(final Throwable t) {
		prepareLog(defaults.forVerbose(), t, null);
	}

	@Override
	public void d(final String message, final Object... args) {
		prepareLog(defaults.forDebug(), null, message, args);
	}

	@Override
	public void d(final Throwable t, final String message, final Object... args) {
		prepareLog(defaults.forDebug(), t, message, args);
	}

	@Override
	public void d(final Throwable t) {
		prepareLog(defaults.forDebug(), t, null);
	}

	@Override
	public void i(final String message, final Object... args) {
		prepareLog(defaults.forInfo(), null, message, args);
	}

	@Override
	public void i(final Throwable t, final String message, final Object... args) {
		prepareLog(defaults.forInfo(), t, message, args);
	}

	@Override
	public void i(final Throwable t) {
		prepareLog(defaults.forInfo(), t, null);
	}

	@Override
	public void w(final String message, final Object... args) {
		prepareLog(defaults.forWarn(), null, message, args);
	}

	@Override
	public void w(final Throwable t, final String message, final Object... args) {
		prepareLog(defaults.forWarn(), t, message, args);
	}

	@Override
	public void w(final Throwable t) {
		prepareLog(defaults.forWarn(), t, null);
	}

	@Override
	public void e(final String message, final Object... args) {
		prepareLog(defaults.forError(), null, message, args);
	}

	@Override
	public void e(final Throwable t, final String message, final Object... args) {
		prepareLog(defaults.forError(), t, message, args);
	}

	@Override
	public void e(final Throwable t) {
		prepareLog(defaults.forError(), t, null);
	}

	@Override
	public void wtf(final String message, final Object... args) {
		prepareLog(defaults.forWtf(), null, message, args);
	}

	@Override
	public void wtf(final Throwable t, final String message, final Object... args) {
		prepareLog(defaults.forWtf(), t, message, args);
	}

	@Override
	public void wtf(final Throwable t) {
		prepareLog(defaults.forWtf(), t, null);
	}

	@Override
	public void log(final BeamPriority priority, final String message, final Object... args) {
		prepareLog(priority, null, message, args);
	}

	@Override
	public void log(final BeamPriority priority, final Throwable t, final String message, final Object... args) {
		prepareLog(priority, t, message, args);
	}

	@Override
	public void log(final BeamPriority priority, final Throwable t) {
		prepareLog(priority, t, null);
	}

	@Override
	public boolean isLoggable(String tag, BeamPriority priority) {
		return true;
	}

	private boolean isEmptyMessage(String message) {
		return message == null || message.isEmpty();
	}

	private void prepareLog(BeamPriority priority, Throwable t, String message, Object... args) {
		String tag = getTag();

		if (!isLoggable(tag, priority))
			return;

		if (isEmptyMessage(message))
			if (t == null)
				return;
			else
				message = LightRayUtils.getStackTrace(t);
		else if (args.length > 0)
			message = String.format(message, args);
		else if (t != null)
			message += "\n" + LightRayUtils.getStackTrace(t);

		this.log(priority, tag, message, t);
	}

	protected abstract void log(BeamPriority priority, String tag, String message, Throwable t);

}
