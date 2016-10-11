package edu.rit.wic.lasers.bridge;

// This is an example, could probably be done better than requiring translation on the
// platform end, but it's also not a huge feature/big deal.

/**
 * Platform-independent notification operations, these are relatively short-lived
 * messages and should not require user-interaction to dismiss.
 *
 * @author Matthew Crocco
 */
public interface Notifier {

	void success(Duration duration, String message, Object... args);
	void warning(Duration duration, String message, Object... args);
	void error(Duration duration, String message, Object... args);
	void info(Duration duration, String message, Object... args);
	void special(Duration duration, String message, Object... args);

	void msg(Duration duration, Type type, String message, Object... args);

	enum Duration {
		QUICK,
		NORMAL
	}

	enum Type {
		SUCCESS,
		WARNING,
		ERROR,
		INFO,
		SPECIAL
	}
}
