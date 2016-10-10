package edu.rit.wic.lasers.logging;

import com.orhanobut.logger.Logger;

/**
 * Created by Matthew on 10/10/2016.
 */
public enum AndroidBeamPriority implements BeamPriority{
	VERBOSE(Logger.VERBOSE),
	DEBUG(Logger.DEBUG),
	INFO(Logger.INFO),
	WARN(Logger.WARN),
	ERROR(Logger.ERROR),
	WTF(Logger.ASSERT);

	private final int priority;

	AndroidBeamPriority(int priority) {
		this.priority = priority;
	}

	@Override public String toMessageTag() {
		return this.name();
	}

	@Override public int toInt() {
		return this.priority;
	}

	@Override public int compare(final BeamPriority lhs, final BeamPriority rhs) {
		int lhsi = lhs.toInt();
		int rhsi = rhs.toInt();

		return lhsi < rhsi ? -1: (lhsi == rhsi ? 0 : 1);
	}

	public static class Defaults implements BeamPriorityDefaults {
		@Override public BeamPriority forInfo() {
			return INFO;
		}

		@Override public BeamPriority forDebug() {
			return DEBUG;
		}

		@Override public BeamPriority forWarn() {
			return WARN;
		}

		@Override public BeamPriority forVerbose() {
			return VERBOSE;
		}

		@Override public BeamPriority forError() {
			return ERROR;
		}

		@Override public BeamPriority forWtf() {
			return WTF;
		}
	}

}
