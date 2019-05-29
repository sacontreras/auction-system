package auction.system;

import java.util.ArrayList;

import auction.user.buyer.biditem.BidItemEvent;
import auction.user.seller.listitem.ListItemEvent;

public class ItemListing implements Comparable<ItemListing>{
	public final ListItemEvent listItemEvent;
	public ListingTimer timer;
	public final ArrayList<BidItemEvent> itemBids = new ArrayList<>();
	
	public ItemListing(ListItemEvent listItemEvent) {
		this.listItemEvent = listItemEvent;
	}

	@Override
	public int compareTo(ItemListing o) {
		return listItemEvent.timestamp.compareTo(o.listItemEvent.timestamp);
	}
	
	public ItemListing clone() {
		ItemListing clonedItemListing = new ItemListing(listItemEvent.clone());
		return clonedItemListing;
	}
}
