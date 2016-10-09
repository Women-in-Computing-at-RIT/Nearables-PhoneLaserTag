package wic.rit.edu.lasers.math;

import com.github.czyzby.kiwi.util.common.UtilitiesClass;
import com.google.common.base.Preconditions;

/**
 * Created by Matthew on 10/8/2016.
 */
public final class ByteMath extends UtilitiesClass {

	public static final int BYTE_BITS = 8;
	public static final int SHORT_BITS = 2*BYTE_BITS;
	public static final int INT_BITS = 4*BYTE_BITS;
	public static final int LONG_BITS = 8*BYTE_BITS;

	public static final int BYTE_MASK = 0xFF;
	public static final int SHORT_MASK = 0xFF_FF;
	public static final int INT_MASK = 0xFF_FF_FF_FF;
	public static final long LONG_MASK = 0xFF_FF_FF_FF_FF_FF_FF_FFL;

	public static int getByte(long value, int n) {
		Preconditions.checkArgument(n < 8, "Largest primitive data type in java is 8 bytes long.");

		int shiftAmt = BYTE_BITS*n;
		return (int)((value >>> shiftAmt) & BYTE_MASK);
	}

	public static int getShort(long value, int n) {
		Preconditions.checkArgument(n < 4, "Largest primitive data type in java is 4 shorts long.");

		int shiftAmt = SHORT_BITS*n;
		return (int)((value >>> shiftAmt) & SHORT_MASK);
	}

	public static int getInt(long value, int n) {
		Preconditions.checkArgument(n < 2, "Largest primitive data type in java is 2 ints long.");

		int shiftAmt = INT_BITS*n;
		return (int)(value >>> shiftAmt);
	}

	public static int getUpperBits(long value) {
		return (int)(value >>> INT_BITS);
	}

	public static int getLowerBits(long value) {
		return (int)(value);
	}

	public static long toLong(int upper, int lower) {
		return ((long)upper << INT_BITS) | lower;
	}

}
