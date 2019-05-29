package auction.system.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampFormatter {
	private static final SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String format(Date timestamp) {
		return timestampFormatter.format(timestamp);
	}
}
