package auction.user.base.closeitem;

import java.util.Date;

import auction.system.base.Event;
import auction.user.base.User;

public class CloseItemEvent extends Event {
	public final User user;
	public final String itemListingId;
	
	public CloseItemEvent(User user, String itemListingId, Date timestamp, String closeItemEventId) {
		super(timestamp, closeItemEventId);
		this.user = user;
		this.itemListingId = itemListingId;
	}
	
	@Override
	public String toString() {
		return String.format("{%s, %s, %s, %s}", timestamp, eventId, user.id, itemListingId);
	}
}
