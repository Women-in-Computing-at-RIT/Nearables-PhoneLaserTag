package edu.rit.wic.lasers.logging;

/**
 * Created by Matthew on 10/10/2016.
 */
public interface LightRay {

	void setTag(String tag);

	void v(String message, Object... args);

	void v(Throwable t, String message, Object... args);

	void v(Throwable t);

	void d(String message, Object... args);

	void d(Throwable t, String message, Object... args);

	void d(Throwable t);

	void i(String message, Object... args);

	void i(Throwable t, String message, Object... args);

	void i(Throwable t);

	void w(String message, Object... args);

	void w(Throwable t, String message, Object... args);

	void w(Throwable t);

	void e(String message, Object... args);

	void e(Throwable t, String message, Object... args);

	void e(Throwable t);

	void wtf(String message, Object... args);

	void wtf(Throwable t, String message, Object... args);

	void wtf(Throwable t);

	void log(BeamPriority priority, String message, Object... args);

	void log(BeamPriority priority, Throwable t, String message, Object... args);

	void log(BeamPriority priority, Throwable t);

	boolean isLoggable(String tag, BeamPriority priority);
}
