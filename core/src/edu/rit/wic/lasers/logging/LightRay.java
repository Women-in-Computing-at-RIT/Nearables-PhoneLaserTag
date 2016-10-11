package edu.rit.wic.lasers.logging;

/**
 * <p>
 * A {@link LightRay} describes an interface for logging at 5 basic levels:
 * <ul>
 *     <li>VERBOSE</li>
 *     <li>DEBUG</li>
 *     <li>INFO</li>
 *     <li>WARN</li>
 *     <li>ERROR</li>
 *     <li>WTF</li>
 * </ul>
 *
 * with an option of specifying a way to tell if a particular message is loggable baed
 * on Tag, {@link BeamPriority} or both or neither.
 * </p>
 *
 * @author Matthew Crocco
 */
public interface LightRay {

	/**
	 * Set tag for the next logging message and the next logging message only.
	 *
	 * @param tag Message Tag
	 */
	void setTag(String tag);

	/**
	 * Log out a message at the VERBOSE level
	 *
	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void v(String message, Object... args);

	/**
	 * Log out a message at the VERBOSE level
	 *
	 * @param t
	 *  {@link Throwable} to log stack trace for.
	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void v(Throwable t, String message, Object... args);

	/**
	 * Log out a message at the VERBOSE level
	 *
	 * @param t
	 *  {@link Throwable} to log stack trace for.
	 */
	void v(Throwable t);

	/**
	 * Log out a message at the DEBUG level
	 *
	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void d(String message, Object... args);

	/**
	 * Log out a message at the DEBUG level
	 *
	 * @param t
	 *  {@link Throwable} to log stack trace for.
	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void d(Throwable t, String message, Object... args);

	/**
	 * Log out a message at the DEBUG level
	 *
	 * @param t
	 *  {@link Throwable} to log stack trace for.
	 */
	void d(Throwable t);

	/**
	 * Log out a message at the INFO level
	 *
	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void i(String message, Object... args);

	/**
	 * Log out a message at the INFO level
	 *
	 * param t
	 *  {@link Throwable} to log stack trace for.
	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void i(Throwable t, String message, Object... args);

	/**
	 * Log out a message at the INFO level
	 *
	 * @param t
	 *  {@link Throwable} to log stack trace for.
	 */
	void i(Throwable t);

	/**
	 * Log out a message at the WARN level
	 *
	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void w(String message, Object... args);

	/**
	 * Log out a message at the WARN level
	 *
	 * @param t
	 *  {@link Throwable} to log stack trace for.
	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void w(Throwable t, String message, Object... args);

	/**
	 * Log out a message at the WARN level
	 *
	 * @param t
	 *  {@link Throwable} to log stack trace for.
	 */
	void w(Throwable t);

	/**
	 * Log out a message at the ERROR level
	 *
	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void e(String message, Object... args);

	/**
	 * Log out a message at the ERROR level
	 *
	 * @param t
	 *  {@link Throwable} to log stack trace for.
	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void e(Throwable t, String message, Object... args);

	/**
	 * Log out a message at the ERROR level
	 *
	 * @param t
	 *  {@link Throwable} to log stack trace for.
	 */
	void e(Throwable t);

	/**
	 * Log out a message at the WTF level

	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void wtf(String message, Object... args);

	/**
	 * Log out a message at the WTF level
	 *
	 * @param t
	 *  {@link Throwable} to log stack trace for.
	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void wtf(Throwable t, String message, Object... args);

	/**
	 * Log out a message at the WTF level
	 *
	 * @param t
	 *  {@link Throwable} to log stack trace for.
	 */
	void wtf(Throwable t);

	/**
	 * Log a message at the given {@link BeamPriority} level.
	 *
	 * @param priority
	 *  {@link BeamPriority} at which to log the given message
	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void log(BeamPriority priority, String message, Object... args);

	/**
	 * Log a message at the given {@link BeamPriority} level.
	 *
	 * @param priority
	 *  {@link BeamPriority} at which to log the given message
	 * @param t
	 *  {@link Throwable} to log stack trace for.
	 * @param message
	 *  Message either as a string by itself or a format string
	 * @param args
	 *  List of arguments to use in formatting if message is a format string
	 */
	void log(BeamPriority priority, Throwable t, String message, Object... args);

	/**
	 * Log a message at the given {@link BeamPriority} level.
	 *
	 * @param priority
	 *  {@link BeamPriority} at which to log the given message
	 * @param t
	 *  {@link Throwable} to log stack trace for.
	 */
	void log(BeamPriority priority, Throwable t);

	/**
	 * Determines if a message is loggable based on the message tag or the message
	 * {@link BeamPriority priority} or both or neither.
	 *
	 * @param tag
	 *  Message tag
	 * @param priority
	 *  Message {@link BeamPriority priority}
	 * @return True if should be logged, false otherwise.
	 */
	boolean isLoggable(String tag, BeamPriority priority);
}
