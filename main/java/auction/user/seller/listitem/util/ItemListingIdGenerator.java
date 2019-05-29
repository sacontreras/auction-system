package auction.user.seller.listitem.util;

import java.util.Date;

import auction.system.base.Item;
import auction.system.base.util.Strings;
import auction.system.base.util.TimestampFormatter;
import auction.user.seller.Seller;

public class ItemListingIdGenerator {
	public static final String generateNewItemListingId(Date timestamp, Seller seller, Item item) { 
		return String.format(
			"%s::%s::%s::%s",
			TimestampFormatter.format(timestamp),
			seller.id,
			item.modelNumber,
			Strings.generateRandom("0123456789abcdef", 8)
		);
	}
}
