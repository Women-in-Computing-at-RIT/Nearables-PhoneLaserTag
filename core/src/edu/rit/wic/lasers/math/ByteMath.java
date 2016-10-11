package edu.rit.wic.lasers.math;

import com.github.czyzby.kiwi.util.common.UtilitiesClass;
import com.google.common.base.Preconditions;

/**
 * Mathematical utilities for doing math on numbers requiring byte and bit operations.
 *
 * @author Matthew Crocco
 */
public final class ByteMath extends UtilitiesClass {

	/** Number of bits in a byte */
	public static final int BYTE_BITS = 8;
	/** Number of bits in a short integer */
	public static final int SHORT_BITS = 2 * BYTE_BITS;
	/** Number of bits in an integer */
	public static final int INT_BITS = 4 * BYTE_BITS;
	/** Number of bits in a long integer */
	public static final int LONG_BITS = 8 * BYTE_BITS;

	/** 8-bit Bitmask */
	public static final int BYTE_MASK = 0xFF;
	/** 16-bit Bitmask */
	public static final int SHORT_MASK = 0xFF_FF;
	/** 32-bit Bitmask */
	public static final int INT_MASK = 0xFF_FF_FF_FF;
	/** 64-bit Bitmask */
	public static final long LONG_MASK = 0xFF_FF_FF_FF_FF_FF_FF_FFL;

	/**
	 * Get the nth byte in a long integer value. This can also be used for floating point
	 * numbers using {@link Float#floatToIntBits(float)} and {@link
	 * Double#doubleToLongBits(double)}.
	 *
	 * @param value
	 * 	Bit pattern to extract byte from
	 * @param n
	 * 	nth byte
	 *
	 * @return nth byte in given bit pattern
	 *
	 * @throws IllegalArgumentException
	 * 	if n >= 8 since maximum bit pattern size is 64 bits
	 */
	public static int getByte(long value, int n) {
		Preconditions.checkArgument(n < 8, "Largest primitive data type in java is 8 "
			+ "bytes long.");

		int shiftAmt = BYTE_BITS * n;
		return (int) ((value >>> shiftAmt) & BYTE_MASK);
	}

	/**
	 * Get the nth short integer (group of 2 bytes) in a long integer value. This can
	 * also
	 * be used for floating point numbers using {@link Float#floatToIntBits(float)} and
	 * {@link Double#doubleToLongBits(double)}.
	 *
	 * @param value
	 * 	Bit pattern to extract byte from
	 * @param n
	 * 	nth short
	 *
	 * @return nth short in given bit pattern
	 *
	 * @throws IllegalArgumentException
	 * 	if n >= 4 since maximum bit pattern size is 64 bits
	 */
	public static int getShort(long value, int n) {
		Preconditions.checkArgument(n < 4, "Largest primitive data type in java is 4 "
			+ "shorts long.");

		int shiftAmt = SHORT_BITS * n;
		return (int) ((value >>> shiftAmt) & SHORT_MASK);
	}

	/**
	 * Get the nth integer (group of 4 bytes) in a long integer value. This can also be
	 * used for floating point numbers using {@link Float#floatToIntBits(float)} and
	 * {@link Double#doubleToLongBits(double)}.
	 *
	 * @param value
	 * 	Bit pattern to extract byte from
	 * @param n
	 * 	nth integer
	 *
	 * @return nth integer in given bit pattern
	 *
	 * @throws IllegalArgumentException
	 * 	if n >= 2 since maximum bit pattern size is 64 bits
	 */
	public static int getInt(long value, int n) {
		Preconditions.checkArgument(n < 2, "Largest primitive data type in java is 2 "
			+ "ints long.");

		int shiftAmt = INT_BITS * n;
		return (int) (value >>> shiftAmt);
	}

	/**
	 * Get the upper 32 bits of a long integer value.
	 *
	 * @param value
	 * 	Bit pattern from which to extract upper bits
	 *
	 * @return Value of the upper bits of the given value
	 */
	public static int getUpperBits(long value) {
		return (int) (value >>> INT_BITS);
	}

	/**
	 * Get the lower 32 bits of a long integer value.
	 *
	 * @param value
	 * 	Bit pattern from which to extract lower bits
	 *
	 * @return Value of the lower bits of the given value
	 */
	public static int getLowerBits(long value) {
		return (int) (value);
	}

	/**
	 * <p> Create a long from two integer values. This is evaluated as: </p> <tt>value =
	 * (upper << 32) | lower</tt>
	 *
	 * @param upper
	 * 	Upper 32 bit value
	 * @param lower
	 * 	Lower 32 bit value
	 *
	 * @return Long integer formed from the two integer values
	 */
	public static long toLong(int upper, int lower) {
		return ((long) upper << INT_BITS) | lower;
	}

}
