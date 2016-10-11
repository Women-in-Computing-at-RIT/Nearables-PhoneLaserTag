package edu.rit.wic.lasers.bridge;

/**
 * Created by Matthew on 10/11/2016.
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
		NORMAL;
	}

	enum Type {
		SUCCESS,
		WARNING,
		ERROR,
		INFO,
		SPECIAL;
	}
}
