package auction.system.base;

import java.util.Date;

public abstract class Event {
	public final Date timestamp;
	public final String eventId;
	
	public Event(Date timestamp, String eventId) {
		this.timestamp = timestamp;
		this.eventId = eventId;
	}
}
