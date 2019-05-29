package auction.system;

import java.util.LinkedHashMap;

public class Controller {
	public final LinkedHashMap<String, ItemListing> itemListings = new LinkedHashMap<>();
	
	private final ListedItemsPublisher listedItemsPublisher;
	private final BidPublisher bidPublisher;
	
	//Singleton
	private static Controller mThis;
	private Controller() {
		listedItemsPublisher = new ListedItemsPublisher(this);
		bidPublisher = new BidPublisher(this);
	}
	public final static Controller getInstance() {
		if (mThis == null)
			mThis = new Controller();
		return mThis;
	}
	
	public ListedItemsPublisher getListedItemsPublisher() {
		return listedItemsPublisher;
	}
	
	public BidPublisher getBidPublisher() {
		return bidPublisher;
	}
}
