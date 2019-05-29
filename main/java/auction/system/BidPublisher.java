package auction.system;

import java.util.Date;

import auction.system.base.Event;
import auction.system.base.Publisher;
import auction.user.base.SystemUser;
import auction.user.buyer.Buyer;
import auction.user.buyer.biditem.BidItemEvent;
import auction.user.buyer.biditem.BidItemFailedEvent;
import auction.user.buyer.biditem.BuyItemEvent;
import auction.user.buyer.biditem.util.ItemBidIdGenerator;
import auction.user.creditor.Creditor;
import auction.user.seller.Seller;
import auction.user.seller.listitem.ListItemEvent.ListingType;

public final class BidPublisher extends Publisher<Event> {
	private final Controller controller;
	
	public BidPublisher(Controller controller) {
		this.controller = controller;
	}
	
	public void buyItem(Buyer buyer, String itemListingId, Date timestamp, double price, Seller seller, Creditor creditor) {
		BuyItemEvent buyItemEvent = new BuyItemEvent(
			buyer,
			itemListingId, 
			price,
			timestamp, 
			ItemBidIdGenerator.generateNewItemBidId(timestamp, buyer, itemListingId)
		);
		
		//publish purchase to buyer first
		publish(buyer.id, buyItemEvent);
		
		//publish purchase to seller
		publish(seller.id, buyItemEvent);
		
		//publish purchase to creditor
		publish(creditor.id, buyItemEvent);
		
		//close item listing (by system)
		controller.getListedItemsPublisher().closeListing(SystemUser.getInstance(), itemListingId);
	}
	
	public void bidItem(Buyer buyer, String itemListingId, double bid) {
		Date timestamp = new Date(System.currentTimeMillis());
		
		if (!controller.itemListings.containsKey(itemListingId)) {	// listing does not or no longer exists
			BidItemFailedEvent bidItemFailedEvent = new BidItemFailedEvent(
				"that item listing does not exist",
				buyer,
				itemListingId, 
				timestamp, 
				null
			);
			
			//publish failed bid to buyer only
			publish(buyer.id, bidItemFailedEvent);
		} else {
			ItemListing itemListing = controller.itemListings.get(itemListingId);
			boolean acceptableBid = false;
			
			if (itemListing.listItemEvent.listingType == ListingType.BUY_NOW) {
				acceptableBid = bid >= itemListing.listItemEvent.price;
				if (acceptableBid)
					buyItem(buyer, itemListingId, timestamp, itemListing.listItemEvent.price, itemListing.listItemEvent.seller, itemListing.listItemEvent.creditor);
				else {
					BidItemFailedEvent bidItemFailedEvent = new BidItemFailedEvent(
						String.format("your bid (%f) was less than the BUY_NOW price of $%f", bid, itemListing.listItemEvent.price),
						buyer,
						itemListingId, 
						timestamp, 
						null
					);
					
					//publish failed bid to buyer only
					publish(buyer.id, bidItemFailedEvent);
				}
			} else {	//then this is a BID item-listing
				int bidCount = itemListing.itemBids.size();
				acceptableBid = 
					bidCount == 0 
						? bid >= itemListing.listItemEvent.price
						: bid > itemListing.itemBids.get(bidCount-1).bid;
				String unacceptableBidReason = 
					bidCount == 0 
						? String.format("your bid ($%f) must be greater than the listing item price of $%f", bid, itemListing.listItemEvent.price) 
						: String.format("your bid ($%f) must be greater than the last bid of $%f", bid, itemListing.itemBids.get(bidCount-1).bid);
						
				if (acceptableBid) {
					BidItemEvent bidItemEvent = new BidItemEvent(
						buyer,
						itemListingId, 
						bid,
						timestamp, 
						ItemBidIdGenerator.generateNewItemBidId(timestamp, buyer, itemListingId)
					);
					itemListing.itemBids.add(bidItemEvent);
					
					//publish successful bid to buyer first
					publish(buyer.id, bidItemEvent);
					
					//then publish successful bid to seller
					publish(itemListing.listItemEvent.seller.id, bidItemEvent);
				} else {
					BidItemFailedEvent bidItemFailedEvent = new BidItemFailedEvent(
						unacceptableBidReason,
						buyer,
						itemListingId, 
						timestamp, 
						null
					);
					
					//publish failed bid to buyer only
					publish(buyer.id, bidItemFailedEvent);
				}
			}
		}
	}
}
