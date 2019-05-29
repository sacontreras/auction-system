package auction.user.seller.listitem;

import java.util.Date;

import auction.system.base.Item;
import auction.user.seller.Seller;

public class ListItemFailedEvent extends ListItemEvent {
	public final String error;

	public ListItemFailedEvent(String error, Seller seller, Item item, ListingType listingType, double price, long duration,
			Date timestamp, String itemListingId) {
		super(seller, item, listingType, price, null, duration, timestamp, itemListingId);
		this.error = error;
	}
	
	@Override
	public String toString() {
		return String.format("{%s, %s, %s, %s, %s, %f, %d, \"%s\"}", item, timestamp, eventId, listingType, seller.id, price, duration, error);
	}
}
