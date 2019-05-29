package auction.user.seller.listitem;

import java.util.Date;

import auction.system.base.Event;

public class ListItemExpiredEvent extends Event {
	public ListItemExpiredEvent(Date timestamp, String eventId) {
		super(timestamp, eventId);
	}
	
	@Override
	public String toString() {
		return String.format("{%s, %s}", timestamp, eventId);
	}
}
