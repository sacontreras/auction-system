package auction.user.base.closeitem.util;

import java.util.Date;

import auction.system.base.util.Strings;
import auction.system.base.util.TimestampFormatter;
import auction.user.base.User;

public class CloseItemEventIdGenerator {
	public static final String generateNewCloseItemEventId(Date timestamp, User user) { 
		return String.format(
			"%s::%s::%s::%s",
			TimestampFormatter.format(timestamp),
			user.id,
			"close",
			Strings.generateRandom("0123456789abcdef", 8)
		);
	}
}
