package auction.user.buyer;

import auction.system.Controller;
import auction.system.base.Event;
import auction.system.base.Subscriber;
import auction.user.base.User;
import auction.user.buyer.biditem.BidItemEvent;
import auction.user.buyer.biditem.BidItemFailedEvent;
import auction.user.buyer.biditem.BuyItemEvent;

public class Buyer extends User implements Subscriber<Event> {
	public Buyer(String id) {
		super(id);
		Controller.getInstance().getBidPublisher().subsribe(id, this);
		Controller.getInstance().getListedItemsPublisher().subsribe(id, this);
	}

	@Override
	public void OnBidItemFailedEvent(BidItemFailedEvent bidItemFailedEvent) {
		System.out.println(String.format("User %s, failed to place your bid: %s", id, bidItemFailedEvent));
	}

	@Override
	public void OnBidItemEvent(BidItemEvent bidItemEvent) {
		System.out.println(String.format("User %s, your bid was placed: %s", id, bidItemEvent));
	}
	
	@Override
	public void OnBuyItemEvent(BuyItemEvent buyItemEvent) {
		System.out.println(String.format("User %s, you won the auction for listing: %s", id, buyItemEvent));
	}
}
