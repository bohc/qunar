package com.bhc.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CharacterSetToolkit {
	// %25就是%，在转换的时候要先把%25替换为%
	public static void main(String[] args) {
		String str = "飞机";
		str = toUnicode(str, false);
		System.out.println(str);
		str = "%5B%7B%22";
		str = fromUnicode(str);
		System.out.println(str);
		str = "traffics_1=%5B%7B%22traffic_seq%22%3A1%2C%22traffic_type%22%3A%221%22%2C%22flight_no%22%3A%22%E5%BE%85%E5%AE%9A%22%2C%22flight_cabin%22%3A%221%22%7D%2C%7B%22traffic_seq%22%3A2%2C%22traffic_type%22%3A%222%22%2C%22train_no%22%3A%22%E5%BE%85%E5%AE%9A%22%2C%22departure%22%3A%22%E5%BE%85%E5%AE%9A%22%2C%22arrive%22%3A%22%E5%BE%85%E5%AE%9A%22%7D%2C%7B%22traffic_seq%22%3A3%2C%22traffic_type%22%3A%223%22%2C%22car_seat%22%3A0%7D%2C%7B%22traffic_seq%22%3A4%2C%22traffic_type%22%3A%224%22%2C%22cruise_name%22%3A%22%E5%BE%85%E5%AE%9A%22%2C%22departure%22%3A%22%E5%BE%85%E5%AE%9A%22%2C%22arrive%22%3A%22%E5%BE%85%E5%AE%9A%22%7D%2C%7B%22traffic_seq%22%3A5%2C%22traffic_type%22%3A%225%22%2C%22other%22%3A%22%E5%BE%85%E5%AE%9A%22%7D%5D";
		str = "%5B%7B%22traffic_seq%22%3A1%2C%22traffic_type%22%3A%221%22%2C%22flight_no%22%3A%22%E5%BE%85%E5%AE%9A%22%2C%22flight_cabin%22%3A%223%22%7D%5D";
		try {
			str = URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(str);
	}

	public CharacterSetToolkit() {

	}

	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	/*
	 * 
	 * Converts unicodes to encoded &#92;uxxxx and escapes
	 * 
	 * special characters with a preceding slash
	 */

	public static String toUnicode(String theString, boolean escapeSpace) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);
		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}

			switch (aChar) {
				case ' ':
					if (x == 0 || escapeSpace) {
						outBuffer.append('\\');
					}
					outBuffer.append(' ');
					break;
				case '\t':
					outBuffer.append('\\');
					outBuffer.append('t');
					break;
				case '\n':
					outBuffer.append('\\');
					outBuffer.append('n');
					break;
				case '\r':
					outBuffer.append('\\');
					outBuffer.append('r');
					break;
				case '\f':
					outBuffer.append('\\');
					outBuffer.append('f');
					break;
				case '=': // Fall through
				case ':': // Fall through
				case '#': // Fall through
				case '!':
					outBuffer.append('\\');
					outBuffer.append(aChar);
					break;
				default:
					if ((aChar < 0x0020) || (aChar > 0x007e)) {
						outBuffer.append('\\');
						outBuffer.append('u');
						outBuffer.append(toHex((aChar >> 12) & 0xF));
						outBuffer.append(toHex((aChar >> 8) & 0xF));
						outBuffer.append(toHex((aChar >> 4) & 0xF));
						outBuffer.append(toHex(aChar & 0xF));
					} else {
						outBuffer.append(aChar);
					}
			}
		}
		return outBuffer.toString();
	}

	public static String fromUnicode(String str) {
		return fromUnicode(str.toCharArray(), 0, str.length(), new char[1024]);
	}

	/*
	 * 
	 * Converts encoded &#92;uxxxx to unicode chars
	 * 
	 * and changes special saved chars to their original forms
	 */

	public static String fromUnicode(char[] in, int off, int len, char[] convtBuf) {
		if (convtBuf.length < len) {
			int newLen = len * 2;
			if (newLen < 0) {
				newLen = Integer.MAX_VALUE;
			}
			convtBuf = new char[newLen];
		}
		char aChar;
		char[] out = convtBuf;
		int outLen = 0;
		int end = off + len;
		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = in[off++];
						switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char) value;
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = (char) aChar;
			}
		}
		return new String(out, 0, outLen);
	}
}
