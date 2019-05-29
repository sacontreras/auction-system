package auction.user.buyer.biditem.util;

import java.util.Date;

import auction.system.base.Item;
import auction.system.base.util.Strings;
import auction.system.base.util.TimestampFormatter;
import auction.user.buyer.Buyer;
import auction.user.seller.Seller;

public class ItemBidIdGenerator {
	public static final String generateNewItemBidId(Date timestamp, Buyer buyer, String itemListingId) { 
		return String.format(
			"%s::%s::%s::%s",
			TimestampFormatter.format(timestamp),
			buyer.id,
			itemListingId,
			Strings.generateRandom("0123456789abcdef", 8)
		);
	}
}
