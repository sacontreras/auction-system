package auction.system.base;

public interface Subscriber<TContent> {
	void notify(TContent content);
}
