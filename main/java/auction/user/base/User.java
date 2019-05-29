package auction.user.base;

import java.util.ArrayList;

import auction.system.ItemListing;
import auction.system.base.Event;
import auction.system.base.Subscriber;
import auction.user.base.closeitem.CloseItemEvent;
import auction.user.base.closeitem.CloseItemFailedEvent;
import auction.user.base.searchitem.FindItemEvent;
import auction.user.buyer.biditem.BidItemEvent;
import auction.user.buyer.biditem.BidItemFailedEvent;
import auction.user.buyer.biditem.BuyItemEvent;
import auction.user.seller.listitem.ListItemEvent;
import auction.user.seller.listitem.ListItemExpiredEvent;
import auction.user.seller.listitem.ListItemFailedEvent;

public abstract class User implements Subscriber<Event> {
	public final String id;
	
	public User(String id) {
		this.id = id;
	}
	
	@Override
	public void notify(Event event) {
		if (event instanceof FindItemEvent) {
			FindItemEvent searchItemEvent = (FindItemEvent)event;
			OnSearchItemEvent(searchItemEvent);
		} else if (event instanceof ListItemFailedEvent) {
			ListItemFailedEvent listItemFailedEvent = (ListItemFailedEvent)event;
			OnListItemFailedEvent(listItemFailedEvent);
		} else if (event instanceof ListItemExpiredEvent) {
			ListItemExpiredEvent listItemExpiredEvent = (ListItemExpiredEvent)event;
			OnListItemExpiredEvent(listItemExpiredEvent);
		} else if (event instanceof ListItemEvent) {
			ListItemEvent listItemEvent = (ListItemEvent)event;
			OnListItemEvent(listItemEvent);
		} else if (event instanceof BidItemFailedEvent) {
			BidItemFailedEvent bidItemFailedEvent = (BidItemFailedEvent)event;
			OnBidItemFailedEvent(bidItemFailedEvent);
		} else if (event instanceof BuyItemEvent) {
			BuyItemEvent buyItemEvent = (BuyItemEvent)event;
			OnBuyItemEvent(buyItemEvent);
		} else if (event instanceof BidItemEvent) {
			BidItemEvent bidItemEvent = (BidItemEvent)event;
			OnBidItemEvent(bidItemEvent);
		} else if (event instanceof CloseItemFailedEvent) {
			CloseItemFailedEvent closeItemFailedEvent = (CloseItemFailedEvent)event;
			OnCloseItemFailedEvent(closeItemFailedEvent);
		} else if (event instanceof CloseItemEvent) {
			CloseItemEvent closeItemEvent = (CloseItemEvent)event;
			OnCloseItemEvent(closeItemEvent);
		}
	}
	
	private final ArrayList<ItemListing> lastSearchResults = new ArrayList<>();
	public ArrayList<ItemListing> getLastSearchResults() {
		return (ArrayList<ItemListing>)lastSearchResults.clone();
	}
	
	public void OnSearchItemEvent(FindItemEvent searchItemEvent) {
		//System.out.println(String.format("User %s, OnSearchItemEvent: is no-op", id));
		lastSearchResults.clear();
		for (ItemListing itemListing: searchItemEvent.searchResults)
			lastSearchResults.add(itemListing.clone());
		
		String searchLine = String.format("User %s, your listed-items search is complete - search type: %s (binary)", id, Integer.toBinaryString(searchItemEvent.searchFieldsSpec));
		StringBuilder sbSearchSpec = new StringBuilder();
		if (searchItemEvent.searchFieldsSpec == 0)
			sbSearchSpec.append("ALL LISTED ITEMS");
		else {
			if (searchItemEvent.searchSpecContainsField(FindItemEvent.SearchField.ITEM_DESCRIPTION)) {
				sbSearchSpec.append(String.format("%s contains \"%s\"", FindItemEvent.SearchField.ITEM_DESCRIPTION, searchItemEvent.itemDescription));
			}
			if (searchItemEvent.searchSpecContainsField(FindItemEvent.SearchField.ITEM_DEPARTMENT)) {
				if (sbSearchSpec.length() > 0)
					sbSearchSpec.append(", ");
				sbSearchSpec.append(String.format("%s = %s", FindItemEvent.SearchField.ITEM_DEPARTMENT, searchItemEvent.itemDept));
			}
			if (searchItemEvent.searchSpecContainsField(FindItemEvent.SearchField.ITEM_MODEL)) {
				if (sbSearchSpec.length() > 0)
					sbSearchSpec.append(", ");
				sbSearchSpec.append(String.format("%s contains \"%s\"", FindItemEvent.SearchField.ITEM_MODEL, searchItemEvent.itemModel));
			}
			if (searchItemEvent.searchSpecContainsField(FindItemEvent.SearchField.SELLER_ID)) {
				if (sbSearchSpec.length() > 0)
					sbSearchSpec.append(", ");
				sbSearchSpec.append(String.format("%s = %s", FindItemEvent.SearchField.SELLER_ID, searchItemEvent.sellerId));
			}
			if (searchItemEvent.searchSpecContainsField(FindItemEvent.SearchField.LISTING_TYPE)) {
				if (sbSearchSpec.length() > 0)
					sbSearchSpec.append(", ");
				sbSearchSpec.append(String.format("%s = %s", FindItemEvent.SearchField.LISTING_TYPE, searchItemEvent.listingType));
			}
			if (searchItemEvent.searchSpecContainsField(FindItemEvent.SearchField.LISTING_PRICE)) {
				if (sbSearchSpec.length() > 0)
					sbSearchSpec.append(", ");
				sbSearchSpec.append(String.format("%s <= %f", FindItemEvent.SearchField.LISTING_PRICE, searchItemEvent.maxListingPrice));
			}
			if (searchItemEvent.searchSpecContainsField(FindItemEvent.SearchField.LISTING_DURATION)) {
				if (sbSearchSpec.length() > 0)
					sbSearchSpec.append(", ");
				sbSearchSpec.append(String.format("%s <= %d", FindItemEvent.SearchField.LISTING_DURATION, searchItemEvent.maxListingDurationRemaining));
			}
		}
		searchLine += " (" + sbSearchSpec.toString() + ")";
		System.out.println(searchLine);
		
		if (getLastSearchResults().size() > 0) {
			for (ItemListing itemListing: getLastSearchResults())
				System.out.println(String.format("\tSearch result item: %s", itemListing.listItemEvent));
		} else {
			System.out.println("\tNO ITEMS FOUND");
		}
	}
	
	public void OnListItemFailedEvent(ListItemFailedEvent listItemFailedEvent) {
		System.out.println(String.format("User %s, OnListItemFailedEvent: is no-op", id));
	}
	
	public void OnListItemExpiredEvent(ListItemExpiredEvent listItemExpiredEvent) {
		System.out.println(String.format("User %s, OnListItemExpiredEvent: is no-op", id));
	}
	
	public void OnListItemEvent(ListItemEvent listItemEvent) {
		System.out.println(String.format("User %s, OnListItemEvent: is no-op", id));
	}
	
	public void OnBidItemFailedEvent(BidItemFailedEvent bidItemFailedEvent) {
		System.out.println(String.format("User %s, OnBidItemFailedEvent: is no-op", id));
	}
	
	public void OnBidItemEvent(BidItemEvent bidItemEvent) {
		System.out.println(String.format("User %s, OnBidItemEvent: is no-op", id));
	}
	
	public void OnBuyItemEvent(BuyItemEvent buyItemEvent) {
		System.out.println(String.format("User %s, OnBuyItemEvent: is no-op", id));
	}
	
	public void OnCloseItemFailedEvent(CloseItemFailedEvent closeItemFailedEvent) {
		System.out.println(String.format("User %s, OnCloseItemFailedEvent: is no-op", id));
	}
	
	public void OnCloseItemEvent(CloseItemEvent closeItemEvent) {
		System.out.println(String.format("User %s, OnCloseItemEvent: is no-op", id));
	}
}
