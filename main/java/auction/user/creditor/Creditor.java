package auction.user.creditor;

import auction.system.Controller;
import auction.user.base.User;
import auction.user.buyer.biditem.BuyItemEvent;

public class Creditor extends User {
	public Creditor(String id) {
		super(id);
		Controller.getInstance().getBidPublisher().subsribe(id, this);
	}
	
	@Override
	public void OnBuyItemEvent(BuyItemEvent buyItemEvent) {
		System.out.println(String.format("User %s, your creditee purchased listing: %s", id, buyItemEvent));
	}
}
