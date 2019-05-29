package auction.user.base.closeitem;

import java.util.Date;

import auction.user.base.User;

public class CloseItemFailedEvent extends CloseItemEvent {
	public final String error;

	public CloseItemFailedEvent(String error, User user, String itemListingId, Date timestamp) {
		super(user, itemListingId, timestamp, null);
		this.error = error;
	}
	
	@Override
	public String toString() {
		return String.format("{%s, %s, %s, %s, \"%s\"}", timestamp, eventId, user.id, itemListingId, error);
	}
}
