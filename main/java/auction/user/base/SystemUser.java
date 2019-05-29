package auction.user.base;

import auction.system.Controller;
import auction.user.seller.listitem.ListItemExpiredEvent;

public class SystemUser extends User {
	//singleton
	private static SystemUser mThis;
	private SystemUser() {
		super("system");
		Controller.getInstance().getListedItemsPublisher().subsribe(id, this);
	}
	public static SystemUser getInstance() {
		if (mThis == null)
			mThis = new SystemUser();
		return mThis;
	}
}
