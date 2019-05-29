package auction.user.buyer.biditem;

import java.util.Date;

import auction.system.base.Event;
import auction.system.base.Item;
import auction.system.base.ItemEvent;
import auction.user.buyer.Buyer;
import auction.user.seller.Seller;

public class BidItemEvent extends Event {
	public final Buyer buyer;
	public final String listItemEventId;
	public final Double bid;

	public BidItemEvent(Buyer buyer, String listItemEventId, Double bid, Date timestamp, String bidListItemEventId) {
		super(timestamp, bidListItemEventId);
		this.buyer = buyer;
		this.listItemEventId = listItemEventId;
		this.bid = bid;
	}
	
	@Override
	public String toString() {
		return String.format("{%s, %s, %s, %f, %s}", timestamp, eventId, buyer.id, bid, listItemEventId);
	}
}
