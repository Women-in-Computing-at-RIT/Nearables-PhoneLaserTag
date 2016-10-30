package edu.rit.wic.lasers.logging;

import java8.util.function.Consumer;
import com.github.czyzby.kiwi.util.common.UtilitiesClass;
import com.google.common.base.Preconditions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Various utilities needed for {@link Beam} logging classes for manipulating Stack Traces and {@link StackTraceElement
 * StackTraceElements}. Various constants are also stored here such as the maximum length of a message for logging and
 * the call stack index of callers.
 *
 * @author Matthew Crocco
 */
public final class LightRayUtils extends UtilitiesClass {

	/** Maximum Message Size for Log Cat primarily */
	public static final int MAX_LOG_LENGTH = 4000;

	/** Index of Caller in a {@link java.lang.Throwable} stack trace */
	public static final int CALL_STACK_INDEX = 5;

	private static final Pattern PATTERN_ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)" + "+$");
	private static final int STACK_TRACE_SW_SIZE = 256;

	/**
	 * Gets Stack Trace as a string from a throwable.
	 *
	 * @param t
	 * 	Throwable to get stack trace from
	 *
	 * @return Stack trace as string
	 *
	 * @throws NullPointerException
	 * 	If parameter is null
	 */
	public static String getStackTrace(Throwable t) {
		t = Preconditions.checkNotNull(t, "Throwable to parse is null!");

		StringWriter sw = new StringWriter(STACK_TRACE_SW_SIZE);
		PrintWriter pw = new PrintWriter(sw, false);

		t.printStackTrace(pw);
		pw.flush();

		return sw.toString();
	}

	/**
	 * Gets the Class Name from a {@link StackTraceElement} without any synthetic components in the name like "$1" for
	 * the first inner class and so on. Will be the Simple Name, not the Fully-Qualified Name.
	 *
	 * @param element
	 * 	{@link StackTraceElement} from which to get class name
	 *
	 * @return Class name without inner class details
	 *
	 * @throws NullPointerException
	 * 	If parameter is null
	 */
	public static String getClassNameNoInner(StackTraceElement element) {
		Preconditions.checkNotNull(element);

		return getClassNameNoInner(element.getClassName());
	}

	/**
	 * Given a class name as a string, strips away the synthetic details about inner classes and package details.
	 * Leaving only the Class' simple name.
	 *
	 * @param className
	 * 	Class Name as String
	 *
	 * @return Simple name of Class without inner class or package details
	 */
	public static String getClassNameNoInner(String className) {
		Matcher m = PATTERN_ANONYMOUS_CLASS.matcher(className);
		if (m.find())
			className = m.replaceAll("");

		return className.substring(className.lastIndexOf('.') + 1);
	}

	/**
	 * Gets the Class Name from a {@link StackTraceElement} of the caller without any synthetic components in the name
	 * like "$1" for the first inner class and so on. Will be the Simple Name, not the Fully-Qualified Name.
	 *
	 * @param stackTrace
	 * 	Array {@link StackTraceElement elements} from which to get caller
	 *
	 * @return Class name of caller without inner class details
	 *
	 * @throws NullPointerException
	 * 	If parameter is null
	 */
	public static String getCallerNameNoInner(StackTraceElement[] stackTrace) {
		return getClassNameNoInner(extractCaller(stackTrace).getClassName());
	}

	/**
	 * Gets the {@link StackTraceElement} representing the caller of the top-most method in the given stack trace.
	 *
	 * @param stackTrace
	 * 	Array of {@link StackTraceElement elements} from which to get caller
	 *
	 * @return {@link StackTraceElement} of caller
	 */
	public static StackTraceElement extractCaller(StackTraceElement[] stackTrace) {
		return extractElement(stackTrace, CALL_STACK_INDEX);
	}

	/**
	 * Gets the mth {@link StackTraceElement} representing the caller of the top-most method in the given stack trace.
	 *
	 * @param stackTrace
	 * 	Array of {@link StackTraceElement elements} from which to get nth element
	 *
	 * @return {@link StackTraceElement} of nth element in stack trace
	 *
	 * @throws ArrayIndexOutOfBoundsException
	 * 	if n >= Stack Trace length
	 */
	public static StackTraceElement extractElement(StackTraceElement[] stackTrace, int n) {
		Preconditions.checkElementIndex(n, stackTrace.length, "Not enough stack trace " + "elements, check proguard.");

		return stackTrace[n];
	}

	/**
	 * Takes a long string and breaks it into chunks of characters then passed to a consuming processor function. The
	 * length of the chunks are determined by {@link #MAX_LOG_LENGTH}.
	 *
	 * @param message
	 * 	Message String
	 * @param consumer
	 * 	Consuming processor function
	 */
	public static void processLongMessage(String message, Consumer<String> consumer) {
		// For each position in string
		for (int i = 0, length = message.length(); i < length; i++) {
			int newline = message.indexOf('\n', i);
			newline = newline != -1 ? newline : length;
			do {
				int end = Math.min(newline, i + MAX_LOG_LENGTH);
				String part = message.substring(i, end);
				consumer.accept(part);
				i = end;
			} while (i < newline);
		}
	}

}
