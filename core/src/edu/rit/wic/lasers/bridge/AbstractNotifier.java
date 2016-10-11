package edu.rit.wic.lasers.bridge;

/**
 * Notifier implementation that auto-fills the
 * {@link edu.rit.wic.lasers.bridge.Notifier.Type Notification Type} and calls
 * {@link #msg(Duration, Type, String, Object...)}.
 *
 * @author Matthew Crocco
 */
public abstract class AbstractNotifier implements Notifier {

	@Override
	public void success(final Duration duration, final String message,
	                    final Object... args) {
		msg(duration, Type.SUCCESS, message, args);
	}

	@Override
	public void warning(final Duration duration, final String message,
	                    final Object... args) {
		msg(duration, Type.WARNING, message, args);
	}

	@Override
	public void error(final Duration duration, final String message,
	                  final Object... args) {
		msg(duration, Type.ERROR, message, args);
	}

	@Override
	public void info(final Duration duration, final String message,
	                 final Object... args) {
		msg(duration, Type.INFO, message, args);
	}

	@Override
	public void special(final Duration duration, final String message,
	                    final Object... args) {
		msg(duration, Type.SPECIAL, message, args);
	}

	abstract  public void msg(final Duration duration, final Type type,
	                          final String message, final Object... args);
}
