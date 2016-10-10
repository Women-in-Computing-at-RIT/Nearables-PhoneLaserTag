package edu.rit.wic.lasers.logging;

import com.annimon.stream.function.Consumer;
import com.github.czyzby.kiwi.util.common.UtilitiesClass;
import com.google.common.base.Preconditions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Matthew on 10/10/2016.
 */
public final class LightRayUtils extends UtilitiesClass {

	public static final int MAX_LOG_LENGTH = 4000;
	public static final int CALL_STACK_INDEX = 5;

	static final Pattern PATTERN_ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");
	static final int STACK_TRACE_SW_SIZE = 256;

	public static String getStackTrace(Throwable t) {
		t = Preconditions.checkNotNull(t, "Throwable to parse is null!");

		StringWriter sw = new StringWriter(STACK_TRACE_SW_SIZE);
		PrintWriter pw = new PrintWriter(sw, false);
		t.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}

	public static String getClassNameNoInner(StackTraceElement element) {
		Preconditions.checkNotNull(element);

		return getClassNameNoInner(element.getClassName());
	}

	public static String getClassNameNoInner(String className) {
		Matcher m = PATTERN_ANONYMOUS_CLASS.matcher(className);
		if (m.find())
			className = m.replaceAll("");

		return className.substring(className.lastIndexOf('.') + 1);
	}

	public static String getCallerNameNoInner(StackTraceElement[] stackTrace) {
		return getClassNameNoInner(extractCaller(stackTrace).getClassName());
	}

	public static StackTraceElement extractCaller(StackTraceElement[] stackTrace) {
		return extractElement(stackTrace, CALL_STACK_INDEX);
	}

	public static StackTraceElement extractElement(StackTraceElement[] stackTrace, int n) {
		Preconditions.checkElementIndex(n, stackTrace.length, "Not enough stack trace elements, check proguard.");

		return stackTrace[n];
	}

	public static void processLongMessage(String message, Consumer<String> consumer) {
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
