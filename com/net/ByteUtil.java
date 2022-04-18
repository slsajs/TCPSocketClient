package com.net;


/**
 * @(#) ByteUtil.java Copyright 1999 by Java Service Network Community, KOREA.
 *      All rights reserved. http://www.javaservice.net
 * 
 *      NOTICE ! You can copy or redistribute this code freely, but you should
 *      not remove the information about the copyright notice and the author.
 * 
 * @author WonYoung Lee, lwy@javaservice.com
 */

public class ByteUtil {

	private ByteUtil() {
	}

	public static final byte[] short2byte(short s) {
		byte dest[] = new byte[2];
		dest[1] = (byte) (s & 0xff);
		dest[0] = (byte) (s >>> 8 & 0xff);
		return dest;
	}

	public static final byte[] int2byte(int i) {
		byte dest[] = new byte[4];
		dest[3] = (byte) (i & 0xff);
		dest[2] = (byte) (i >>> 8 & 0xff);
		dest[1] = (byte) (i >>> 16 & 0xff);
		dest[0] = (byte) (i >>> 24 & 0xff);
		return dest;
	}

	public static final byte[] long2byte(long l) {
		byte dest[] = new byte[8];
		dest[7] = (byte) (int) (l & 255L);
		dest[6] = (byte) (int) (l >>> 8 & 255L);
		dest[5] = (byte) (int) (l >>> 16 & 255L);
		dest[4] = (byte) (int) (l >>> 24 & 255L);
		dest[3] = (byte) (int) (l >>> 32 & 255L);
		dest[2] = (byte) (int) (l >>> 40 & 255L);
		dest[1] = (byte) (int) (l >>> 48 & 255L);
		dest[0] = (byte) (int) (l >>> 56 & 255L);
		return dest;
	}

	public static final byte[] float2byte(float f) {
		byte dest[] = new byte[4];
		return setfloat(dest, 0, f);
	}

	public static final byte[] double2byte(double d) {
		byte dest[] = new byte[8];
		return setdouble(dest, 0, d);
	}

	public static final byte getbyte(byte src[], int offset) {
		return src[offset];
	}

	public static final byte[] getbytes(byte src[], int offset, int length) {
		byte dest[] = new byte[length];
		System.arraycopy(src, offset, dest, 0, length);
		return dest;
	}

	public static final short getshort(byte src[], int offset) {
		return (short) ((src[offset] & 0xff) << 8 | src[offset + 1] & 0xff);
	}

	public static final int getint(byte src[], int offset) {
		return (src[offset] & 0xff) << 24 | (src[offset + 1] & 0xff) << 16
				| (src[offset + 2] & 0xff) << 8 | src[offset + 3] & 0xff;
	}

	public static final long getlong(byte src[], int offset) {
		return (long) getint(src, offset) << 32
				| (long) getint(src, offset + 4) & 0xffffffffL;
	}

	public static final long getlong(byte src[]) {
		return (((long) (src[0] & 0xff) << 24) & 0xffffffff)
				| ((src[1] & 0xff) << 16) | ((src[2] & 0xff) << 8)
				| (src[3] & 0xff);
	}

	public static final float getfloat(byte src[], int offset) {
		return Float.intBitsToFloat(getint(src, offset));
	}

	public static final double getdouble(byte src[], int offset) {
		return Double.longBitsToDouble(getlong(src, offset));
	}

	public static final byte[] setbyte(byte dest[], int offset, byte b) {
		dest[offset] = b;
		return dest;
	}

	public static final byte[] setbytes(byte dest[], int offset, byte src[]) {
		System.arraycopy(src, 0, dest, offset, src.length);
		return dest;
	}

	public static final byte[] setbytes(byte dest[], int offset, byte src[],
			int len) {
		System.arraycopy(src, 0, dest, offset, len);
		return dest;
	}

	public static final byte[] setshort(byte dest[], int offset, short s) {
		dest[offset] = (byte) (s >>> 8 & 0xff);
		dest[offset + 1] = (byte) (s & 0xff);
		return dest;
	}

	public static final byte[] setint(byte dest[], int offset, int i) {
		dest[offset] = (byte) (i >>> 24 & 0xff);
		dest[offset + 1] = (byte) (i >>> 16 & 0xff);
		dest[offset + 2] = (byte) (i >>> 8 & 0xff);
		dest[offset + 3] = (byte) (i & 0xff);
		return dest;
	}

	public static final byte[] setlong(byte dest[], int offset, long l) {
		setint(dest, offset, (int) (l >>> 32));
		setint(dest, offset + 4, (int) (l & 0xffffffffL));
		return dest;
	}

	public static final byte[] setfloat(byte dest[], int offset, float f) {
		return setint(dest, offset, Float.floatToIntBits(f));
	}

	public static final byte[] setdouble(byte dest[], int offset, double d) {
		return setlong(dest, offset, Double.doubleToLongBits(d));
	}

	public static final boolean isEquals(byte b[], String s) {
		if (b == null || s == null)
			return false;
		int slen = s.length();
		if (b.length != slen)
			return false;
		for (int i = slen; i-- > 0;)
			if (b[i] != s.charAt(i))
				return false;

		return true;
	}

	public static final boolean isEquals(byte a[], byte b[]) {
		if (a == null || b == null)
			return false;
		if (a.length != b.length)
			return false;
		for (int i = a.length; i-- > 0;)
			if (a[i] != b[i])
				return false;

		return true;
	}

	public static byte[] fillInBuff(byte[] buf, int start, int offset,
			String msg) {
		if (msg == null)
			msg = "";
		byte[] tmp = msg.trim().getBytes();
		for (int i = start; i < offset + start; i++) {
			buf[i] = 0x20;
		}
		for (int i = start; i < offset + start && i < tmp.length + start; i++) {
			buf[i] = tmp[i - start];
		}
		
		return buf;
	}

	public static byte[] fillInBuff(byte[] buf, int start, int offset, byte[] c) {
		for (int i = start; i < offset + start && i < c.length + start; i++) {
			buf[i] = c[i - start];
		}
		
		return buf;
	}

	public static byte[] fillInBuff(byte[] buf, int start, int offset, byte c) {
		for (int i = start; i < offset + start; i++) {
			buf[i] = 0x00;
		}
		buf[start] = c;
		return buf;
	}
	
	public static void main(String[] args) {
		short sendMsg	= 22;
	}
	
	public static byte[] hexToBytes(char[] hex) {
		int length = hex.length / 2;
		byte[] raw = new byte[length];
		for (int i = 0; i < length; i++) {
			int high = Character.digit(hex[i * 2], 16);
			int low = Character.digit(hex[i * 2 + 1], 16);
			int value = (high << 4) | low;
			if (value > 127) {
				value -= 256;
			}
			raw[i] = (byte) value;
		}

		return raw;
	}

	public static char[] bytesToHex(byte[] raw) {
		char[] kDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		int length = raw.length;
		char[] hex = new char[length * 2];
		for (int i = 0; i < length; i++) {
			int value = (raw[i] + 256) % 256;
			int highIndex = value >> 4;
			int lowIndex = value & 0x0f;
			hex[i * 2 + 0] = kDigits[highIndex];
			hex[i * 2 + 1] = kDigits[lowIndex];
		}
		return hex;
	}
}
