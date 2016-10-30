package edu.rit.wic.lasers.bridge;

/**
 * Notifier implementation that auto-fills the
 * {@link NotifType Notification Type} and calls
 * {@link #msg(DurationHint, NotifType, String, Object...)}.
 *
 * @author Matthew Crocco
 */
public abstract class AbstractNotifier implements Notifier {

	@Override
	public void success(final DurationHint duration, final String message,
	                    final Object... args) {
		msg(duration, NotifType.SUCCESS, message, args);
	}

	@Override
	public void warning(final DurationHint duration, final String message,
	                    final Object... args) {
		msg(duration, NotifType.WARNING, message, args);
	}

	@Override
	public void error(final DurationHint duration, final String message,
	                  final Object... args) {
		msg(duration, NotifType.ERROR, message, args);
	}

	@Override
	public void info(final DurationHint duration, final String message,
	                 final Object... args) {
		msg(duration, NotifType.INFO, message, args);
	}

	@Override
	public void special(final DurationHint duration, final String message,
	                    final Object... args) {
		msg(duration, NotifType.SPECIAL, message, args);
	}

	abstract  public void msg(final DurationHint duration, final NotifType type,
	                          final String message, final Object... args);
}
