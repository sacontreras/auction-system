package auction.user.base.searchitem.util;

import java.util.Date;

import auction.system.base.util.Strings;
import auction.system.base.util.TimestampFormatter;
import auction.user.base.User;

public class FindItemEventIdGenerator {
	public static final String generateNewFindItemEventId(Date timestamp, User user) { 
		return String.format(
			"%s::%s::%s::%s",
			TimestampFormatter.format(timestamp),
			user.id,
			"search",
			Strings.generateRandom("0123456789abcdef", 8)
		);
	}
}
