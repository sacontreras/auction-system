package auction.system.base.util;

public class Strings {
	public final static String generateRandom(String charSet, int length) {
		StringBuilder sb = new StringBuilder();
		char[] srcChars = charSet.toCharArray();
		for (int i = 0; i < length; i++) {
			int idx = (int)(Math.random() * srcChars.length);
			sb.append(srcChars[idx]);
		}
		return sb.toString();
	}
}
