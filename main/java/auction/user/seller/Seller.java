package auction.user.seller;

import auction.system.Controller;
import auction.system.base.Event;
import auction.system.base.Subscriber;
import auction.user.base.User;
import auction.user.base.closeitem.CloseItemEvent;
import auction.user.base.closeitem.CloseItemFailedEvent;
import auction.user.buyer.biditem.BidItemEvent;
import auction.user.buyer.biditem.BuyItemEvent;
import auction.user.seller.listitem.ListItemEvent;
import auction.user.seller.listitem.ListItemExpiredEvent;
import auction.user.seller.listitem.ListItemFailedEvent;

public class Seller extends User implements Subscriber<Event> {
//	public final static int FLD_SRCH_SPEC__ITM_DESCR 	= 0x01;
//	public final static int FLD_SRCH_SPEC__ITM_DEPT 	= 0x02;
//	public final static int FLD_SRCH_SPEC__ITM_MDL 		= 0x04;
	public final static int FLD_SRCH_SPEC__SELLER_ID 	= 0x08;

	public Seller(String id) {
		super(id);
		Controller.getInstance().getListedItemsPublisher().subsribe(id, this);
		Controller.getInstance().getBidPublisher().subsribe(id, this);
	}
	
	@Override
	public void OnListItemFailedEvent(ListItemFailedEvent listItemFailedEvent) {
		System.out.println(String.format("User %s, failed to list your item: %s", id, listItemFailedEvent));
	}

	@Override
	public void OnListItemExpiredEvent(ListItemExpiredEvent listItemExpiredEvent) {
		System.out.println(String.format("User %s, your item listing expired: %s", id, listItemExpiredEvent));
	}

	@Override
	public void OnListItemEvent(ListItemEvent listItemEvent) {
		System.out.println(String.format("User %s, your item was listed: %s", id, listItemEvent));
	}

	@Override
	public void OnBidItemEvent(BidItemEvent bidItemEvent) {
		System.out.println(String.format("User %s, a bid was placed on your listed item: %s", id, bidItemEvent));
	}
	
	@Override
	public void OnBuyItemEvent(BuyItemEvent buyItemEvent) {
		System.out.println(String.format("User %s, your listing was purchased: %s", id, buyItemEvent));
	}

	@Override
	public void OnCloseItemFailedEvent(CloseItemFailedEvent closeItemFailedEvent) {
		System.out.println(String.format("User %s, failed to close your listing: %s", id, closeItemFailedEvent));
	}

	@Override
	public void OnCloseItemEvent(CloseItemEvent closeItemEvent) {
		System.out.println(String.format("User %s, your listing was closed: %s", id, closeItemEvent));
	}
}
