package auction.user.buyer.biditem;

import java.util.Date;

import auction.user.buyer.Buyer;

public class BidItemFailedEvent extends BidItemEvent {
	public final String error;
	
	public BidItemFailedEvent(String error, Buyer buyer, String listItemEventId, Date timestamp, String bidListItemEventId) {
		super(buyer, listItemEventId, null, timestamp, bidListItemEventId);
		this.error = error;
	}
	
	@Override
	public String toString() {
		return String.format("{%s, %s, %s, %s, \"%s\"}", timestamp, eventId, buyer.id, listItemEventId, error);
	}
}
