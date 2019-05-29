package auction.user.buyer.biditem;

import java.util.Date;

import auction.user.buyer.Buyer;

public class BuyItemEvent extends BidItemEvent {
	public BuyItemEvent(Buyer buyer, String listItemEventId, Double price, Date timestamp, String bidListItemEventId) {
		super(buyer, listItemEventId, price, timestamp, bidListItemEventId);
	}
}
