package auction.system;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import auction.system.base.Event;
import auction.system.base.Item;
import auction.system.base.Publisher;
import auction.user.base.SystemUser;
import auction.user.base.User;
import auction.user.base.closeitem.CloseItemEvent;
import auction.user.base.closeitem.CloseItemFailedEvent;
import auction.user.base.closeitem.util.CloseItemEventIdGenerator;
import auction.user.base.searchitem.FindItemEvent;
import auction.user.base.searchitem.util.FindItemEventIdGenerator;
import auction.user.buyer.biditem.BidItemEvent;
import auction.user.creditor.Creditor;
import auction.user.seller.Seller;
import auction.user.seller.listitem.ListItemEvent;
import auction.user.seller.listitem.ListItemExpiredEvent;
import auction.user.seller.listitem.ListItemFailedEvent;
import auction.user.seller.listitem.util.ItemListingIdGenerator;

public final class ListedItemsPublisher extends Publisher<Event> {
	private final Controller controller;
	
	public ListedItemsPublisher(Controller controller) {
		this.controller = controller;
	}
	
	public void listItem(Seller seller, Item item, ListItemEvent.ListingType listingType, double price, long duration, Creditor creditor) {
		Date timestamp = new Date(System.currentTimeMillis());
		String itemListingId = ItemListingIdGenerator.generateNewItemListingId(timestamp, seller, item);
		
		if (controller.itemListings.containsKey(itemListingId)) {
			ListItemFailedEvent listItemFailedEvent = new ListItemFailedEvent( 
				"that item listing id already exists",
				seller, 
				item, 
				listingType, 
				price, 
				duration,
				timestamp, 
				itemListingId
			);
			
			publish(seller.id, listItemFailedEvent);
		} else {
			ListItemEvent listItemEvent = new ListItemEvent( 
				seller, 
				item, 
				listingType, 
				price,
				creditor,
				duration,
				timestamp, 
				itemListingId
			);
			
			ItemListing itemListing = new ItemListing(listItemEvent);
			itemListing.timer = new ListingTimer(itemListingId, 1000, false);
			itemListing.timer.schedule(
				new TimerTask() {
		            @Override
		            public void run() {
		            	ListItemExpiredEvent listItemExpiredEvent = new ListItemExpiredEvent(new Date(System.currentTimeMillis()), itemListingId);
		                publish(seller.id, listItemExpiredEvent);
		                
		                ItemListing itemListing = controller.itemListings.get(itemListingId);
		                if (itemListing != null && itemListing.itemBids.size() > 0) {
		                	BidItemEvent closingBid = itemListing.itemBids.get(itemListing.itemBids.size()-1);
		                	Date buyTimestamp = new Date(System.currentTimeMillis());
		                	Controller.getInstance().getBidPublisher().buyItem(closingBid.buyer, itemListingId, buyTimestamp, closingBid.bid, seller, itemListing.listItemEvent.creditor);
		                } else {
		                	closeListing(SystemUser.getInstance(), listItemExpiredEvent.eventId);
		                }
		            }
				}, 
				listItemEvent.duration*1000
			);
	        controller.itemListings.put(listItemEvent.eventId, itemListing);
			
			
			publish(seller.id, listItemEvent);
		}
	}
	
	public void closeListing(User user, String itemListingId) {
		Date timestamp = new Date(System.currentTimeMillis());
		
		if (!controller.itemListings.containsKey(itemListingId)) {	//listing does not or no longer exists
			if (!(user instanceof SystemUser)) {
				CloseItemFailedEvent closeItemFailedEvent = new CloseItemFailedEvent(
					"that item listing does not exist", 
					user, 
					itemListingId, 
					timestamp
				);
				
				publish(user.id, closeItemFailedEvent);
			}
		} else {
			ItemListing itemListing = controller.itemListings.get(itemListingId);
			
			if (user instanceof SystemUser || user.id.equals(itemListing.listItemEvent.seller.id)) {
				CloseItemEvent closeItemEvent = new CloseItemEvent(
					user,
					itemListingId,
					timestamp, 
					CloseItemEventIdGenerator.generateNewCloseItemEventId(timestamp, user)
				);
				if (itemListing.timer != null) {
					if (itemListing.timer.monitorTimeoutThread != null && itemListing.timer.monitorTimeoutThread.isAlive())
	            		itemListing.timer.monitorTimeoutThread.interrupt();
					itemListing.timer.cancel();
				}
				controller.itemListings.remove(itemListingId);
				
				publish(itemListing.listItemEvent.seller.id, closeItemEvent);
			} else {
				
			}
		}
	}
	
	public void findItem(
			User user, 
			int searchFieldsSpec, 
			String itemDescription, 
			Item.Department itemDept, 
			String itemModel,
			String sellerId, 
			ListItemEvent.ListingType listingType, 
			Double maxListingPrice, 
			Long maxListingDurationRemaining) {
		
		Date timestamp = new Date(System.currentTimeMillis());
		FindItemEvent searchItemEvent = new FindItemEvent(
			searchFieldsSpec, 
			itemDescription, 
			itemDept, 
			itemModel, 
			sellerId, 
			listingType, 
			maxListingPrice, 
			maxListingDurationRemaining, 
			timestamp, 
			FindItemEventIdGenerator.generateNewFindItemEventId(timestamp, user)
		);
		
		//now get listed-items for matching selected search values...
		for (ItemListing itemListing: controller.itemListings.values()) {
			if ((searchFieldsSpec & Item.FLD_SRCH_SPEC__ITM_DESCR) == Item.FLD_SRCH_SPEC__ITM_DESCR) {
				if (!itemListing.listItemEvent.item.description.toLowerCase().contains(itemDescription.toLowerCase()))
					continue;
			}
			if ((searchFieldsSpec & Item.FLD_SRCH_SPEC__ITM_DEPT) == Item.FLD_SRCH_SPEC__ITM_DEPT) {
				if (itemListing.listItemEvent.item.department != itemDept)
					continue;
			}
			if ((searchFieldsSpec & Item.FLD_SRCH_SPEC__ITM_MDL) == Item.FLD_SRCH_SPEC__ITM_MDL) {
				if (!itemListing.listItemEvent.item.modelNumber.toLowerCase().contains(itemModel.toLowerCase()))
					continue;
			}
			if ((searchFieldsSpec & Seller.FLD_SRCH_SPEC__SELLER_ID) == Seller.FLD_SRCH_SPEC__SELLER_ID) {
				if (!itemListing.listItemEvent.seller.id.toLowerCase().equals(sellerId.toLowerCase()))
					continue;
			}
			if ((searchFieldsSpec & ListItemEvent.FLD_SRCH_SPEC__LSTNG_TYPE) == ListItemEvent.FLD_SRCH_SPEC__LSTNG_TYPE) {
				if (itemListing.listItemEvent.listingType != listingType)
					continue;
			}
			if ((searchFieldsSpec & ListItemEvent.FLD_SRCH_SPEC__LSTNG_PRICE) == ListItemEvent.FLD_SRCH_SPEC__LSTNG_PRICE) {
				if (itemListing.listItemEvent.price > maxListingPrice)
					continue;
			}
			if ((searchFieldsSpec & ListItemEvent.FLD_SRCH_SPEC__LSTNG_DUR) == ListItemEvent.FLD_SRCH_SPEC__LSTNG_DUR) {
				long durationRemainingSeconds = itemListing.listItemEvent.getTimeRemaining()/1000;
				if (durationRemainingSeconds > maxListingDurationRemaining)
					continue;
			}
			searchItemEvent.searchResults.add(itemListing.clone());
		}
		
		publish(user.id, searchItemEvent);
	}
	
	public void getAllItems(User user) {
		findItem(user, 0, null, null, null, null, null, null, null);
	}
}
