package auction.system.base;

import java.util.Date;

public abstract class ItemEvent extends Event {
	public final Item item;
	
	public ItemEvent(Item item, Date timestamp, String eventId) {
		super(timestamp, eventId);
		this.item = item;
	}
}
