package edu.rit.wic.lasers.bridge;

// This is an example, could probably be done better than requiring translation on the
// platform end, but it's also not a huge feature/big deal.


/**
 * Platform-independent notification operations, these are relatively short-lived messages and should not require
 * user-interaction to dismiss.
 *
 * @author Matthew Crocco
 */
public interface Notifier {

	void success(DurationHint duration, String message, Object... args);

	void warning(DurationHint duration, String message, Object... args);

	void error(DurationHint duration, String message, Object... args);

	void info(DurationHint duration, String message, Object... args);

	void special(DurationHint duration, String message, Object... args);

	void msg(DurationHint duration, NotifType type, String message, Object... args);

	/**
	 * <p> General display durations. These are only applicable to notification systems that create a visual display,
	 * this param may also be ignored but is required as part of the abstraction (Namely because Android Toast's
	 * require
	 * a duration of some sort). </p> <p> Like {@link NotifType} this should be translated to the appropriate type of
	 * Duration as necessary on the platform-specific {@link Notifier notifier implementation}. </p> <p> Since this may
	 * be ignored, this is termed a Hint instead of an absolute. </p>
	 */
	enum DurationHint {
		/** Notification should be displayed for a "short" period of time */
		SHORT,
		/**
		 * Notification should be displayed for a "typical" (possibly the default) amount of time.
		 */
		NORMAL,
		/** Notification should be displayed for a "long" period of time. */
		LONG
	}


	/**
	 * The general types of notifications within our system, these are all types that can be sent from the core
	 * project.
	 * These should be translated as necessary by the platform-specific {@link Notifier notifier implementation}.
	 */
	enum NotifType {
		SUCCESS,
		WARNING,
		ERROR,
		INFO,
		SPECIAL
	}
}
