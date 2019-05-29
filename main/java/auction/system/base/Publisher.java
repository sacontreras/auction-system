package auction.system.base;

import java.util.HashMap;

public abstract class Publisher<TContent> {
	protected final HashMap<String, Subscriber<TContent>> subscribers = new HashMap<>();
	
	public final void subsribe(String subId, Subscriber<TContent> subscriber) {
		subscribers.put(subId, subscriber);
	}
	
	public final void unsubsribe(String subId) {
		subscribers.remove(subId);
	}
	
	public void publish(String subId, TContent content) {
		Subscriber<TContent> subscriber = subscribers.get(subId);
		if (subscriber == null)
			throw new NullPointerException(String.format("subscriber \"%s\" is not subscribed to this publibisher", subId));
		subscriber.notify(content);
	}
	
	public void broadcast(TContent content) {
		for (Subscriber<TContent> subscriber: subscribers.values())
			subscriber.notify(content);
	}
}
