package org.wewi.medimg.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

public final class Base64 {
    
	public static String encode(byte[] raw, int offset, int length) {
		StringBuffer encoded = new StringBuffer();
		for (int i = 0; i < length; i += 3)
			encoded.append(encodeBlock(raw, offset + i));
		return encoded.toString();
	}
    
    
    public static String encode(byte[] raw) {
        return encode(raw, 0, raw.length);
    }
    
	private static char[] encodeBlock(byte[] raw, int offset) {
		int block = 0;
		int slack = raw.length - offset - 1;
		int end = (slack >= 2) ? 2 : slack;
		for (int i = 0; i <= end; i++) {
			byte b = raw[offset + i];
			int neuter = (b < 0) ? b + 256 : b;
			block += neuter << (8 * (2 - i));
		}
		char[] base64 = new char[4];
		for (int i = 0; i < 4; i++) {
			int sixbit = (block >>> (6 * (3 - i))) & 0x3f;
			base64[i] = getChar(sixbit);
		}
		if (slack < 1)
			base64[2] = '=';
		if (slack < 2)
			base64[3] = '=';
		return base64;
	}
    
	private static char getChar(int sixBit) {
		if (sixBit >= 0 && sixBit <= 25)
			return (char) ('A' + sixBit);
		if (sixBit >= 26 && sixBit <= 51)
			return (char) ('a' + sixBit - 26);
		if (sixBit >= 52 && sixBit <= 61)
			return (char) ('0' + sixBit - 52);
		if (sixBit == 62)
			return '+';
		if (sixBit == 63)
			return '/';
		return '?';
	}

	public static byte[] decode(String base64)
		throws IllegalArgumentException {
		try {
			int pad = 0;
			for (int i = base64.length() - 1; base64.charAt(i) == '='; i--)
				pad++;
			int length = base64.length() * 6 / 8 - pad;
			byte[] raw = new byte[length];
			int rawIndex = 0;
			for (int i = 0; i < base64.length(); i += 4) {
				int block =
					(getValue(base64.charAt(i)) << 18)
						+ (getValue(base64.charAt(i + 1)) << 12)
						+ (getValue(base64.charAt(i + 2)) << 6)
						+ (getValue(base64.charAt(i + 3)));
				for (int j = 0; j < 3 && rawIndex + j < raw.length; j++)
					raw[rawIndex + j] =
						(byte) ((block >> (8 * (2 - j))) & 0xff);
				rawIndex += 3;
			}
			return raw;
		} catch (StringIndexOutOfBoundsException e) {
		}
		throw new IllegalArgumentException("No base 64 encoded string");
	}
    
	private static int getValue(char c) {
		if (c >= 'A' && c <= 'Z')
			return c - 'A';
		if (c >= 'a' && c <= 'z')
			return c - 'a' + 26;
		if (c >= '0' && c <= '9')
			return c - '0' + 52;
		if (c == '+')
			return 62;
		if (c == '/')
			return 63;
		if (c == '?' || c == '=')
			return 0;
		return -1;
	}
    
    

	// A little APPLICATION to simulate an unix filter
	public static void main(String[] args) throws Exception {
		if (args.length > 0
			&& (args[0].startsWith("e") || args[0].startsWith("-e"))) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int read;
			byte[] buf = new byte[4096];
			while ((read = System.in.read(buf)) > 0)
				bos.write(buf, 0, read);

			System.err.println("encode " + bos.size() + " bytes.");

			String encoded = Base64.encode(bos.toByteArray());
			for (int i = 0; i < encoded.length(); i += 72) {
				int rest = Math.min(72, encoded.length() - i);
				System.out.println(encoded.substring(i, i + rest));
			}
		} else {
			StringBuffer in = new StringBuffer();
			String line;
			BufferedReader reader =
				new BufferedReader(new InputStreamReader(System.in));

			while ((line = reader.readLine()) != null)
				in.append(line);

			System.err.println("decode " + in.length() + " bytes.");

			byte[] decoded = Base64.decode(in.toString());
			System.out.write(decoded);
		}
	}

}
